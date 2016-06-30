package com.jdjr.datasolution.exception;

/**
 * Created by wangbo on 16/2/21.
 */
public class RecordNotExistException extends RuntimeException {

    public RecordNotExistException() {
    }

    public RecordNotExistException(String msg) {
        super(msg);
    }
}
