package com.warrous.ready2ride.network;



import retrofit2.Call;
import retrofit2.Response;


public class ApiResponseHandler {

    public static String NO_INTERNET = "Please check your network connection.";
    public static String SERVER_ERROR = "Something went wrong, please try again later.";

    ApiCallBack<?> apiCallBack;

    public ApiResponseHandler(ApiCallBack<?> apiCallBack) {
        this.apiCallBack = apiCallBack;
    }

    public void noNetwork() {
        APIError apiError = new APIError();
       //apiError.setCode(ApiErrorCode.NO_NETWORK.getCode());
        apiError.setMessage(NO_INTERNET);
        apiCallBack.onError(apiError);

    }

    public void onResponse(Call call, Response response) {

        String method = getMethod(call);
        ApiResponse apiResponse = (ApiResponse) response.body();
        if (!apiResponse.isError()) {
            apiCallBack.onResponse(method,apiResponse.getMessage(),apiResponse.getResult());

        } else {
            APIError apiError = apiResponse.getApiError();
            if (apiError == null) {
                apiError = new APIError();
            }
           // apiError.setCode(apiResponse.getApiError().getCode());
            apiError.setMethod(method);
            apiError.setMessage(apiResponse.getMessage());
//            if(apiError!=null&&apiError.getMessage().isEmpty()&&apiError.getMessage().equalsIgnoreCase("")){
//              //  apiError.setMessage(apiResponse.apiError.getMessage());
//            }

            apiCallBack.onError(apiError);
        }

    }


    public void onError(Call call, Response response) {
        String method = getMethod(call);
        APIError apiError = new APIError();
        apiError.setMethod(method);
        apiError.setMessage(SERVER_ERROR);
      //  apiError.setCode(ApiErrorCode.SERVER_ERROR.getCode());
        apiCallBack.onError(apiError);
    }

    public void onFails(Call call, Throwable t) {
        String method = getMethod(call);
        APIError apiError = new APIError();
        apiError.setMethod(method);
      //  apiError.setCode(ApiErrorCode.SERVER_ERROR.getCode());
        apiError.setMessage(SERVER_ERROR);
        apiCallBack.onError(apiError);

    }

    private String getMethod(Call call) {
        StringBuilder builder = new StringBuilder();
        for (String path : call.request().url().pathSegments()) {
            builder.append(path).append("/");
        }
        builder.deleteCharAt(builder.lastIndexOf("/"));
        return builder.toString();

    }
}
