package com.utn.TP_Final.projections;

import java.util.Date;

public interface CallsFromUser {

    String getFirstname();
    String getLastname();
    String getDni();
    Integer getIdCall();
    float getTotalCost();
    float getTotalPrice();
    Date getDateCall();
    String getSourceNumber();
    String getDestinationNumber();

    void setFirstname(String firstname);
    void setLastname(String lastname);
    void setDni(String dni);
    void setIdCall(Integer idCall);
    void setTotalCost(float totalCost);
    void setTotalPrice(float totalPrice);
    void setDateCall(Date dateCall);
    void setSourceNumber(String sourceNumber);
    void setDestinationNumber(String destinationNumber);
}
