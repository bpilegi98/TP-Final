package com.utn.TP_Final.model.enums;

public enum LineType {
    MOBILE("MOBILE"),
    RESIDENTIAL("RESIDENTIAL");

    private final String string;

    private LineType(final String string)
    {
        this.string = string;
    }

    public String getString()
    {
        return string;
    }
}
