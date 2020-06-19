package com.utn.TP_Final.projections;

public interface CallsBetweenDates {

    String getCalledNumber();
    Integer getDurationSecs();
    float getTotalPrice();

    void setCalledNumber(String calledNumber);
    void setDurationSecs(Integer durationSecs);
    void setTotalPrice(float totalPrice);
}
