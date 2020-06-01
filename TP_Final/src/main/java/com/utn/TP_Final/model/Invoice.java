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
    @Column(name = "id")
    private Integer id;

    @Column(name = "total_price")
    private float totalPrice;

    @Column(name = "total_cost")
    private float totalCost;

    @Column(name = "date_creation")
    private Date dateCreation;

    @Column(name = "date_expiration")
    private Date dateExpiration;

    @Column(name = "paid")
    private boolean paid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="fee-telephone_lines")
    @JoinColumn(name = "id_telephone_line")
    private TelephoneLine telephoneLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="fee-user")
    @JoinColumn(name = "id_user")
    private User user;
}
