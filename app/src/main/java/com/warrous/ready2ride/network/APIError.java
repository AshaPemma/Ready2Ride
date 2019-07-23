package com.warrous.ready2ride.network;


public class APIError {
    private String message;
    private String method;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "APIError{" +
                ", message='" + message + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
