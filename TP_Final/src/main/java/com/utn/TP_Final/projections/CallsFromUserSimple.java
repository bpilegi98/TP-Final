package com.utn.TP_Final.projections;

public interface CallsFromUserSimple {

    String getFirstname();
    String getLastname();
    String getDni();
    Integer getCallsMade();

    void setFirstname(String firstname);
    void setLastname(String lastname);
    void setDni(String dni);
    void setCallsMade(Integer callsMade);

}
