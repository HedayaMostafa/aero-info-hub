package com.smart4aviation.flightInformation.service.impl;

import com.smart4aviation.flightInformation.dto.AirportInfoRequestDTO;
import com.smart4aviation.flightInformation.dto.AirportInfoResponseDTO;
import com.smart4aviation.flightInformation.model.Baggage;
import com.smart4aviation.flightInformation.model.Flight;
import com.smart4aviation.flightInformation.repository.BaggageRepository;
import com.smart4aviation.flightInformation.repository.FlightRepository;
import com.smart4aviation.flightInformation.service.AirportInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Service
@Log4j2
public class AirportInfoServiceImpl implements AirportInfoService {

    private FlightRepository flightRepository;
    private BaggageRepository baggageRepository;

   @Autowired
    public AirportInfoServiceImpl(FlightRepository flightRepository, BaggageRepository baggageRepository) {
        this.flightRepository = flightRepository;
        this.baggageRepository = baggageRepository;
    }


    @Override
    public AirportInfoResponseDTO airportInfo(AirportInfoRequestDTO requestDTO) {
       long flightsDeparting = 0 , flightsArriving = 0;
       AtomicLong totalPiecesBaggageDeparting = new AtomicLong(0);
        AtomicLong totalPiecesBaggageArriving = new AtomicLong();

       flightsDeparting= flightRepository.countByDepartureAirportIATACodeAndDepartureDate(requestDTO.getCode(), String.valueOf(requestDTO.getDate()));
       flightsArriving= flightRepository.countByArrivalAirportIATACodeAndDepartureDate(requestDTO.getCode(), String.valueOf(requestDTO.getDate()));

        Optional<List<Flight>> optionalDepartureFlights = flightRepository.findAllByDepartureAirportIATACodeAndDepartureDate(requestDTO.getCode(), String.valueOf(requestDTO.getDate()));
        Optional<List<Flight>> optionalArrivalFlights = flightRepository.findAllByArrivalAirportIATACodeAndDepartureDate(requestDTO.getCode(), String.valueOf(requestDTO.getDate()));


        optionalDepartureFlights.ifPresent(flightList -> {
            totalPiecesBaggageDeparting.set(sumOfPieces(flightList));
        });

        optionalArrivalFlights.ifPresent(flightList -> {
            totalPiecesBaggageArriving.set(sumOfPieces(flightList));
        });

       AirportInfoResponseDTO responseDTO = new AirportInfoResponseDTO();

       responseDTO.setFlightsDeparting(flightsDeparting);
       responseDTO.setFlightsArriving(flightsArriving);
       responseDTO.setTotalPiecesBaggageDeparting(totalPiecesBaggageDeparting.get());
       responseDTO.setTotalPiecesBaggageArriving(totalPiecesBaggageArriving.get());

        return responseDTO;
    }

    private long sumOfPieces(List<Flight> flightList) {

        AtomicLong totalPieces = new AtomicLong(0);

        Optional<List<Baggage>> optionalBaggage = baggageRepository.findAllByFlightIn(flightList);

        optionalBaggage.ifPresent(baggageList -> {
            totalPieces.set(baggageList
                    .stream()
                    .mapToInt(Baggage::getPieces)
                    .sum());

        });

        return totalPieces.get();

    }



}
