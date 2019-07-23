package com.warrous.ready2ride.network;

import android.content.Context;

import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestClient {

    private static Retrofit retrofit;
    private Context context;
    private String baseUrl = "";
    private OkHttpClient client;
//    auth/signup

    private RestClient(Context context, String hostUrl) {
        this.context = context;
        this.baseUrl = hostUrl;
    }

    private static <T> T createClient(Class<T> apiInterface) {
        return retrofit.create(apiInterface);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    void build() {
        if (baseUrl == null) {
            return;
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(logging)
                //.addInterceptor(new TokenInterceptor(context))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        .create()))
                .client(client)
                .build();

    }

    public void executeInBackGround(Call<?> request, ApiCallBack<?> apiCallBack) {
        if (!NetworkUtil.isNetworkAvailable(context)) {
            return;
        }
        logRequest(request);
        request.enqueue(new CallbackHandler(retrofit, apiCallBack));
    }

    public void execute(Call<?> request, ApiCallBack<?> apiCallBack) {
        if (!NetworkUtil.isNetworkAvailable(context)) {
            new ApiResponseHandler(apiCallBack).noNetwork();
            return;
        }
        logRequest(request);
        request.enqueue(new CallbackHandler(retrofit, apiCallBack));
    }

    private void logRequest(Call<?> request) {
        try {
//           ContactPointLog.getInstance().info("Request : " + request.request().toString());
//            ContactPointLog.getInstance().info("Request : " + request.request().body().toString());
        } catch (Exception e) {

        }
    }

    public void cancelAll() {
        client.dispatcher().cancelAll();
    }

    public static final class Builder {
        RestClient restClient;

        public Builder(Context context, String hostUrl) {
            restClient = new RestClient(context, hostUrl);
            restClient.build();
        }

        public <T> T create(Class<T> apiInterface) {
            return createClient(apiInterface);
        }

        public RestClient build() {
            return restClient;
        }
    }
}
