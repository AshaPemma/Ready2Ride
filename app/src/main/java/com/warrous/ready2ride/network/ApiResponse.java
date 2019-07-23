package com.warrous.ready2ride.network;


public class ApiResponse<T> {
    boolean error;
    T result;

    String message;
    APIError apiError;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public APIError getApiError() {
        return apiError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setApiError(APIError apiError) {
        this.apiError = apiError;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "error=" + error +
                ", result=" + result +
                ", apiError=" + apiError +
                '}';
    }
}
