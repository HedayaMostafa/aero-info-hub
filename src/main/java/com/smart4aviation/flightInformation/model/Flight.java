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
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "initial_value")
    @Column(name = "flightId", nullable = false)
    private Long flightId;

    @Column(name = "flightNumber")
    private Integer flightNumber;

    @Column(name = "departureAirportIATACode")
    private String departureAirportIATACode;

    @Column(name = "arrivalAirportIATACode")
    private String arrivalAirportIATACode;

    @Column(name = "departureDate")
    private String departureDate;
}
