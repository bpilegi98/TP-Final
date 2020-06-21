package com.utn.TP_Final.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@AllArgsConstructor
@Data
public class CallDto {

    String sourceNumber;
    String destinationNumber;
    Integer duration;
    Date date;
}
