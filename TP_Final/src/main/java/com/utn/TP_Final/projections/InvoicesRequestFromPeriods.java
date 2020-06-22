package com.utn.TP_Final.projections;

import java.sql.Date;

public interface InvoicesRequestFromPeriods {

    String getLine_number();
    Date getDate_creation();
    Date getDate_expiration();
    float getTotal_cost();
    float getTotal_price();
    boolean getPaid();

    void setLine_number(String line_number);
    void setDate_creation(Date date_creation);
    void setDate_expiration(Date date_expiration);
    void setTotal_cost(float total_cost);
    void setTotal_price(float total_price);
    void setPaid(boolean paid);

}
