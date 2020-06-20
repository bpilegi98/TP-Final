package com.utn.TP_Final.projections;

public interface TopMostCalledDestinations {

    String getNumber_called();
    Integer getTimes_called();

    void setNumber_called(String number_called);
    void setTimes_called(Integer times_called);
}
