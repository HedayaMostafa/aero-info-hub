package com.smart4aviation.flightInformation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportInfoResponseDTO {
    private long flightsDeparting;
    private long flightsArriving;
    private long totalPiecesBaggageDeparting;
    private long totalPiecesBaggageArriving;
}
