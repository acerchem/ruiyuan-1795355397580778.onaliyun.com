package com.acerchem.facades.facades;

/**
 * Created by Jacob.Ji on 2018/4/2.
 */
public class AcerchemOrderException extends Exception {
    private String code;
    private String message;

    public AcerchemOrderException(String message) {
        this.message = message;
    }

    public AcerchemOrderException(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
