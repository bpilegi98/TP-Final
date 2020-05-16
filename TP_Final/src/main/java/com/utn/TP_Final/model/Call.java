package com.utn.TP_Final.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Calls")
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCall")
    private Integer idCall;

    @JoinColumn(name = "idTelephoneLine")
    private TelephoneLine sourceNumber;

    @JoinColumn(name = "idTelehoneLine")
    private TelephoneLine destinationNumber;

    @Column(name = "pricePerMinute")
    private float pricePerMinute;

    @Column(name = "durationSecs")
    private Integer durationSecs;

    @Column(name = "totalCost")
    private float totalCost;

    @Column(name = "totalPrice")
    private float totalPrice;

    @JoinColumn(name = "idInvoice")
    private Invoice invoice;

    @JoinColumn(name = "idCity")
    private City sourceCity;

    @JoinColumn(name = "idCity")
    private City destinationCity;
}
