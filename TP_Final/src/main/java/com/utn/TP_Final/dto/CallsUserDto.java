package com.utn.TP_Final.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CallsUserDto {

    String sourceNumber;
    String sourceCity;
    String destinationNumber;
    String destinationCity;
    float totalPrice;
    LocalDateTime dateCall;

}
