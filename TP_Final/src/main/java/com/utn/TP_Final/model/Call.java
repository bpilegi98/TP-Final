package com.utn.TP_Final.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "calls")
public class Call {
    //TODO Agregarle el id de los numeros de telefono

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "price_per_minute", nullable = false)
    private float pricePerMinute;

    @Column(name = "duration_secs")
    private Integer durationSecs;

    @Column(name = "total_cost", nullable = false)
    private float totalCost;

    @Column(name = "total_price", nullable = false)
    private float totalPrice;

    @Column(name = "date_call")
    private LocalDateTime dateCall;

    @Column(name = "source_number", nullable = false)
    private String sourceNumber;

    @Column(name = "destination_number", nullable = false)
    private String destinationNumber;
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="city-province")
    @JoinColumn(name = "id_province")
    private Province province;
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "call-source_number")
    @JoinColumn(name = "id_source_number", referencedColumnName = "id")
    private TelephoneLine idSourceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "call-destination_number")
    @JoinColumn(name = "id_destination_number",referencedColumnName = "id")
    private TelephoneLine idDestinationNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="call-source_city")
    @JoinColumn(name = "id_source_city")
    private City sourceCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="call-destination_city")
    @JoinColumn(name = "id_destination_city")
    private City destinationCity;

    public Call(String sourceNumber, String destinationNumber,Integer durationSecs, LocalDateTime dateCall ) {
        this.durationSecs = durationSecs;
        this.dateCall = dateCall;
        this.sourceNumber = sourceNumber;
        this.destinationNumber = destinationNumber;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="call-invoice")
    @JoinColumn(name = "id_invoice")
    private Invoice invoice;


}
