package com.ns.hospitalmanagement.exception;

public class ResourceNotFoundException extends  RuntimeException {

    public  ResourceNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
