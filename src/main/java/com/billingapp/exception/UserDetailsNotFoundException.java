package com.billingapp.exception;

public class UserDetailsNotFoundException extends RuntimeException{
    public UserDetailsNotFoundException(String msg){
        super(msg);
    }
}
