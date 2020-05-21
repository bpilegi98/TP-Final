package com.utn.TP_Final.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.utn.TP_Final.model.enums.LineStatus;
import com.utn.TP_Final.model.enums.LineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "lineType")
    @Enumerated(EnumType.STRING)
    private LineType lineType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LineStatus lineStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="telephoneline-person")
    @JoinColumn(name = "idPerson")
    private Person person;

    @OneToMany(mappedBy = "telephoneLine",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoices;
}
