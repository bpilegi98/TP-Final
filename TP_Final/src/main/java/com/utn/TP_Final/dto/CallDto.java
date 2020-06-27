package com.utn.TP_Final.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CallDto {

    String sourceNumber;
    String destinationNumber;
    Integer duration;
    LocalDateTime date;
}
