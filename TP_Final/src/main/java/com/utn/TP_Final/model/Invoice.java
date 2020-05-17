package com.utn.TP_Final.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Date dateExpiration;

    @Column(name = "paid")
    private boolean paid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "idTelephoneLine") 
    private TelephoneLine telephoneLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "idPerson")
    private Person person;
}
