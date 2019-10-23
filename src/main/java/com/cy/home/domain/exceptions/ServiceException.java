package com.cy.home.domain.exceptions;

public class ServiceException extends RuntimeException {

    public ServiceException(Exception e) {
        super(e);
    }


    public ServiceException(String msg) {
        super(msg);
    }

}
