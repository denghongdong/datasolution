package com.jdjr.datasolution.exception;

/**
 * Created by wangbo on 16/2/21.
 */
public class ParamNotValidException extends RuntimeException {

    public ParamNotValidException() {
    }

    public ParamNotValidException(String msg) {
        super(msg);
    }
}
