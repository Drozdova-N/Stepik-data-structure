package com.company.stepik.tree_search;

public class NullExp extends RuntimeException {
    private final String messageException;

    public NullExp(String s) {
        this.messageException = s;
    }

    public String getMessage() {
        return messageException;
    }
}
