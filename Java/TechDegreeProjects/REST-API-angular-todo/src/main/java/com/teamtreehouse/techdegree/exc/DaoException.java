package com.teamtreehouse.techdegree.exc;

public class DaoException extends Exception {

    private final Exception orginalException;

    public DaoException(Exception originalException, String message) {
        super(message);
        this.orginalException = originalException;
    }
}
