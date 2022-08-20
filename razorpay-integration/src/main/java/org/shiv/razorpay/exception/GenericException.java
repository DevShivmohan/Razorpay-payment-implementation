package org.shiv.razorpay.exception;

import lombok.Data;

@Data
public class GenericException extends Exception{
    private int statusCode;
    private String exceptionDesc;

    public GenericException(int statusCode,String exceptionDesc){
        super(exceptionDesc);
        this.statusCode=statusCode;
        this.exceptionDesc=exceptionDesc;
    }
}
