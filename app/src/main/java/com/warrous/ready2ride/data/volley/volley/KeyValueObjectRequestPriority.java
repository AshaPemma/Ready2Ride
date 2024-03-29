package com.warrous.ready2ride.data.volley.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Gokul Kalagara on 1/26/2018.
 */

public class KeyValueObjectRequestPriority extends StringRequest {
    private Priority mPriority = Priority.LOW;
    private Map<String, String> mParams;
    private Map<String, String> mHeaders;

    public KeyValueObjectRequestPriority(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public KeyValueObjectRequestPriority(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }


    @Override
    public Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Priority priority) {
        mPriority = priority;
    }

    public void setParams(Map<String, String> mParams) {
        this.mParams = mParams;
    }

//    public void setHeaders(Map<String, String> mHeaders) {
//        this.mHeaders = mHeaders;
//    }


    @Override
    protected Map<String, String> getParams() {
        return mParams;
    }

    @Override
    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        return super.setRetryPolicy(retryPolicy);
    }


//    @Override
//    public Map<String, String> getHeaders() {
//        return mHeaders;
//    }
}
