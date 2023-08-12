package com.smart4aviation.flightInformation.mapper;

import com.smart4aviation.flightInformation.dto.FlightDTO;
import com.smart4aviation.flightInformation.model.Flight;
import com.smart4aviation.flightInformation.util.DateTimeUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightMapper {

    public Flight flightDTOToFlight (FlightDTO flightDTO) {
        Flight flight = new Flight();
        flight.setFlightId(flightDTO.getFlightId());
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setDepartureAirportIATACode(flightDTO.getDepartureAirportIATACode());
        flight.setArrivalAirportIATACode(flightDTO.getArrivalAirportIATACode());
        if(flightDTO.getDepartureDate()!=null) {
            flight.setDepartureDate(DateTimeUtils.parseLocalDateTime(flightDTO.getDepartureDate()));
        }
        return flight;
    }

    public List<Flight> flightDTOListToFlightList(List<FlightDTO> flightDTOList) {
        return flightDTOList.stream().map(this::flightDTOToFlight).collect(Collectors.toList());
    }
}
