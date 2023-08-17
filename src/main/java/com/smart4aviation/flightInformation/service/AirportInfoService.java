package com.smart4aviation.flightInformation.service;

import com.smart4aviation.flightInformation.dto.AirportInfoRequestDTO;
import com.smart4aviation.flightInformation.dto.AirportInfoResponseDTO;

public interface AirportInfoService {

    AirportInfoResponseDTO airportInfo (AirportInfoRequestDTO requestDTO);

}
