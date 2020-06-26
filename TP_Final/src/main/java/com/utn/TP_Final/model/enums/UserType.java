package com.utn.TP_Final.model.enums;

public enum  UserType {
    AERIAL("AERIAL"),
    CUSTOMER("CUSTOMER"),
    EMPLOYEE("EMPLOYEE");

    private final String string;

    private UserType(final String string)
    {
        this.string = string;
    }

    public String getString()
    {
        return string;
    }
}
