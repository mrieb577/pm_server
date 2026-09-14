package com.mrieb577.responses;

public class RequestResponse {
    public static final int SUCCESS_CODE = 200;
    public static final int BAD_REQUEST_CODE = 400;
    public static final int ACCESS_DENIED_CODE = 404;
    public static final int SERVER_ERROR_CODE = 500;
    public static final int NOT_IMPLEMENTED_CODE = 501;
    
    public int code;

    public RequestResponse(int code){
        this.code = code;
    }
}
