package com.utn.TP_Final.projections;

public interface TopMostCalledDestinations {

    String getCity();
    Integer getTimes_called();

    void setNumber_called(String city);
    void setTimes_called(Integer times_called);
}
