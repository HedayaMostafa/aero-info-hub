package com.smart4aviation.flightInformation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightCargoInfo {

    private Long flightId;

    private List<Baggage> baggage;

    private List<Cargo> cargo;
}
