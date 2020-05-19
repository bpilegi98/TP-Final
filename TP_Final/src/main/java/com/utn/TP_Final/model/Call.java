package com.utn.TP_Final.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Calls")
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCall")
    private Integer idCall;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idTelephoneLine", referencedColumnName = "idTelephoneLine")
    private TelephoneLine sourceNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idTelehoneLine", referencedColumnName = "idTelephoneLine")
    private TelephoneLine destinationNumber;

    @Column(name = "pricePerMinute")
    private float pricePerMinute;

    @Column(name = "durationSecs")
    private Integer durationSecs;

    @Column(name = "totalCost")
    private float totalCost;

    @Column(name = "totalPrice")
    private float totalPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idInvoice", referencedColumnName = "idInvoice")
    private Invoice invoice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCity", referencedColumnName = "idCity", insertable = false, updatable = false)
    private City sourceCity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCity", referencedColumnName = "idCity", insertable = false, updatable = false)
    private City destinationCity;
}
