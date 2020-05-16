package com.utn.TP_Final.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInvoice")
    private Integer idInvoice;

    @Column(name = "calls")
    private Integer calls;

    @Column(name = "cost")
    private float cost;

    @Column(name = "totalPrice")
    private float totalPrice;

    @Column(name = "dateCreation")
    private Date dateCreation;

    @Column(name = "dateExpiration")
    private Date dateExpation;

    @Column(name = "paid")
    private boolean paid;

    @JoinColumn(name = "idTelephoneLine") //hacer el onetomany y manytoone?
    private TelephoneLine telephoneLine;

    @JoinColumn(name = "idPerson") //hacer el onetomany y manytoone?
    private Person person;
}
