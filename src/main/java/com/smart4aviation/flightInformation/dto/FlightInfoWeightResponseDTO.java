package com.smart4aviation.flightInformation.dto;

import lombok.Data;

@Data
public class FlightInfoWeightResponseDTO {
    private double baggageWeight;
    private double cargoWeight;
    private double totalWeight;
}
