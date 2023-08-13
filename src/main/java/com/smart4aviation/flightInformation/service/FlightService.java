package com.smart4aviation.flightInformation.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart4aviation.flightInformation.dto.FlightDTO;
import com.smart4aviation.flightInformation.mapper.FlightMapper;
import com.smart4aviation.flightInformation.model.Flight;
import com.smart4aviation.flightInformation.repository.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Order(value=1)
@Slf4j
@Component
public class FlightService implements CommandLineRunner {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    private final String FLIGHTS_JSON = "/flight.json";

    @Autowired
    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        TypeReference<List<FlightDTO>> typeReference = new TypeReference<>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream(FLIGHTS_JSON);
        List<FlightDTO> flightDTOS =  new ObjectMapper().readValue(inputStream, typeReference);
        List<Flight> flights = flightMapper.flightDTOListToFlightList(flightDTOS);
        flightRepository.saveAll(flights);
        log.info("The flights have been added.");
   }
}
