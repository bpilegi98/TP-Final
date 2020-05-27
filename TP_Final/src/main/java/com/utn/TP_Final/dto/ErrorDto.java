package com.utn.TP_Final.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorDto {

    @JsonProperty
    private int code;
    @JsonProperty
    private String description;
}
