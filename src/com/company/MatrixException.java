package com.company;

public class MatrixException extends RuntimeException {
    private final String messageException;

    public MatrixException(String messageException) {
        this.messageException = messageException;
    }

    @Override
    public String getMessage() {
        return messageException;
    }


}
