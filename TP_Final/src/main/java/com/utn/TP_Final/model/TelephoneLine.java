package com.utn.TP_Final.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "telephone_lines")
public class TelephoneLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "line_number", unique = true)
    private String lineNumber;

    @Column(name = "line_type")
    @Enumerated(EnumType.STRING)
    private LineType lineType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LineStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="telephone_lines-user")
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(mappedBy = "telephoneLine",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoices;
}
