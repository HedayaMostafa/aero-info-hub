package com.smart4aviation.flightInformation.service.impl;

import com.smart4aviation.flightInformation.dto.AirportInfoRequestDTO;
import com.smart4aviation.flightInformation.dto.AirportInfoResponseDTO;
import com.smart4aviation.flightInformation.service.AirportInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AirportInfoServiceImpl implements AirportInfoService {

    @Override
    public AirportInfoResponseDTO airportInfo(AirportInfoRequestDTO requestDTO) {
        return null;
    }
}
