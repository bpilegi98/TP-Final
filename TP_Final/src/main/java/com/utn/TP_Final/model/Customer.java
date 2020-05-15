package com.utn.TP_Final.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@Entity es una entity?
public class Customer extends Person{

    //@OneToMany(mappedBy = "customer")
   // private List<Line> lines;
}
