package com.utn.TP_Final.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "prefix_number")
    private String prefixNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="city-province")
    @JoinColumn(name = "id_province")
    private Province province;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    public City(String name, String prefixNumber) {
        this.name = name;
        this.prefixNumber = prefixNumber;
    }
}
