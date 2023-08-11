package com.smart4aviation.flightInformation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flightId", nullable = false)
    private Long flightId;

    @Column(name = "flightNumber")
    private Integer flightNumber;

    @Column(name = "departureAirportIATACode")
    private String departureAirportIATACode;

    @Column(name = "arrivalAirportIATACode")
    private String arrivalAirportIATACode;

    @Column(name = "departureDate")
    private LocalDateTime departureDate;

}
