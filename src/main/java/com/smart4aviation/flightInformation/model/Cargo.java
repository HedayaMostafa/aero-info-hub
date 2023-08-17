package com.smart4aviation.flightInformation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "initial_value", sequenceName = "my_sequence", initialValue = 0)
@Table(name = "cargo")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "initial_value")
    @Column(name = "id")
    private Long id;

    @Column(name = "weight")
    private double weight;

    @Column(name = "weightUnit")
    private String weightUnit;

    @Column(name = "pieces")
    private Integer pieces;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

}
