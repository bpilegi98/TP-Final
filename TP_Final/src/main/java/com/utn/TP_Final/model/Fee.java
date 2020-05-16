package com.utn.TP_Final.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Fees")
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFee")
    private Integer idFee;

    @Column(name = "pricePerMinute")
    private float pricePerMinute;

    @Column(name = "costPerMinute")
    private float costPerMinute;

    @JoinColumn(name = "idCity")
    private City sourceCity;

    @JoinColumn(name = "idCity")
    private City destinationCity;
}
