package com.warrous.ready2ride.network;


public enum ApiErrorCode {
    NO_NETWORK(100),
    SERVER_ERROR(500),
    SERVER_ERROR_UPDATE(2000),
    SERVER_ERROR_RESPONSE(102),
    INVALID_EMAIL(500),
    UN_REGISTERED_EMAIL(501),
    REGISTERED_EMAIL(502);

    int code;

    ApiErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
