package com.smart4aviation.flightInformation.service;

import com.smart4aviation.flightInformation.dto.FlightInfoWeightRequestDTO;
import com.smart4aviation.flightInformation.dto.FlightInfoWeightResponseDTO;

public interface FlightInfoService {

    FlightInfoWeightResponseDTO getFlightInfoWeight (FlightInfoWeightRequestDTO flightInfoWeightRequestDTO);
}
