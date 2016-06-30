package com.jdjr.datasolution.exception;

/**
 * Created by wangbo on 16/2/21.
 */
public class StatusNotValidException extends RuntimeException {

    public StatusNotValidException() {
    }

    public StatusNotValidException(String msg) {
        super(msg);
    }
}
