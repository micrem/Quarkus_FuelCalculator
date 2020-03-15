package org.dhbw.mosbach.ai.spritCalc18A;

public class ApiResponseWrapper<T> {
    private int status;
    private String message;
    private T responseData;

    public ApiResponseWrapper(int status, String message, T responseData) {
        this.status = status;
        this.message = message;
        this.responseData = responseData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }
}
