package com.utn.TP_Final.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Fees")
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Integer id;

    @Column(name = "price_per_minute")
    private float pricePerMinute;

    @Column(name = "cost_per_minute")
    private float costPerMinute;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_source_city",  insertable = false, updatable = false)
    private City sourceCity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_destination_city", insertable = false, updatable = false)
    private City destinationCity;
}
