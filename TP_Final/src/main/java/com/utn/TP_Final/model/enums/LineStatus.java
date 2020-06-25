package com.utn.TP_Final.model.enums;


public enum LineStatus {

    SUSPENDED("SUSPENDED"),
    ACTIVE("ACTIVE");

    private final String string;

    private LineStatus(final String string)
    {
        this.string = string;
    }

    public String getString()
    {
        return string;
    }


}
