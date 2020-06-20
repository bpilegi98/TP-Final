package com.utn.TP_Final.projections;

public interface CallsBetweenDates {

    String getCalledNumber();
    Integer getCallDuration();
    float getTotalPrice();

    void setCalledNumber(String calledNumber);
    void setCallDuration(Integer durationSecs);
    void setTotalPrice(float totalPrice);
}
