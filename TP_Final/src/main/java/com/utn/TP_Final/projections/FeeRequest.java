package com.utn.TP_Final.projections;

public interface FeeRequest {

    String getCityFrom();
    String getCityTo();
    float getFee();

    void setCityFrom(String cityFrom);
    void setCityTo(String cityTo);
    void setFee(float fee);

}
