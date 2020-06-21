package com.utn.TP_Final.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "calls")
public class Call {
    //TODO Agregarle el id de los numeros de telefono

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "price_per_minute")
    private float pricePerMinute;

    @Column(name = "duration_secs")
    private Integer durationSecs;

    @Column(name = "total_cost")
    private float totalCost;

    @Column(name = "total_price")
    private float totalPrice;

    @Column(name = "date_call")
    private Date dateCall;


    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="city-province")
    @JoinColumn(name = "id_province")
    private Province province;
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "call-source_number")
    @JoinColumn(name = "source_number", referencedColumnName = "line_number")
    private TelephoneLine sourceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "call-destination_number")
    @JoinColumn(name = "destination_number",referencedColumnName = "line_number")
    private TelephoneLine destinationNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="call-source_city")
    @JoinColumn(name = "id_source_city")
    private City sourceCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="user-destination_city")
    @JoinColumn(name = "id_destination_city")
    private City destinationCity;

    public Call(TelephoneLine sourceNumber, TelephoneLine destinationNumber,Integer durationSecs, Date date_call ) {
        this.durationSecs = durationSecs;
        this.dateCall = dateCall;
        this.sourceNumber = sourceNumber;
        this.destinationNumber = destinationNumber;
    }

}
