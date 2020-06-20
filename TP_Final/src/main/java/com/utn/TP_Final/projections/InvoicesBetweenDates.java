package com.utn.TP_Final.projections;

import java.sql.Date;

public interface InvoicesBetweenDates {

    Date getPeriod_from();
    Date getPeriod_to();
    String getLine_number();
    float getTotal_price();
    boolean getPaid();

    void setPeriod_from(Date period_from);
    void setPeriod_to(Date period_to);
    void setLine_number(String line_number);
    void setPaid(boolean paid);
}
