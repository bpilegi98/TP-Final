package com.utn.TP_Final.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TelephoneLines")
public class TelephoneLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTelephoneLine")
    private Integer idTelephoneLine;

    @Column(name = "lineNumber", unique = true)
    private String lineNumber;

    @Column(name = "userType")
    private Integer userType;

    @Column(name = "suspended")
    private boolean suspended;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "idPerson")
    private Person person;
}
