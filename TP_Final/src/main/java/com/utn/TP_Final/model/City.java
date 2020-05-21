package com.utn.TP_Final.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCity")
    private int idCity;

    @Column(name = "cityName")
    private String cityName;

    @Column(name = "prefixNumber")
    private Integer prefixNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="city-province")
    @JoinColumn(name = "idProvince")
    private Province province;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Person> persons;
}
