package com.smart4aviation.flightInformation.service.impl;

import com.smart4aviation.flightInformation.dto.FlightInfoWeightRequestDTO;
import com.smart4aviation.flightInformation.dto.FlightInfoWeightResponseDTO;
import com.smart4aviation.flightInformation.exception.FlightNotFoundException;
import com.smart4aviation.flightInformation.model.Baggage;
import com.smart4aviation.flightInformation.model.Cargo;
import com.smart4aviation.flightInformation.model.Flight;
import com.smart4aviation.flightInformation.repository.BaggageRepository;
import com.smart4aviation.flightInformation.repository.CargoRepository;
import com.smart4aviation.flightInformation.repository.FlightRepository;
import com.smart4aviation.flightInformation.service.FlightInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class FlightInfoServiceImpl implements FlightInfoService {

    private FlightRepository flightRepository;
    private BaggageRepository baggageRepository;
    private CargoRepository cargoRepository;


    @Autowired
    public FlightInfoServiceImpl(FlightRepository flightRepository, BaggageRepository baggageRepository, CargoRepository cargoRepository) {
        this.flightRepository = flightRepository;
        this.baggageRepository = baggageRepository;
        this.cargoRepository = cargoRepository;
    }

    @Override
    public FlightInfoWeightResponseDTO getFlightInfoWeight(FlightInfoWeightRequestDTO requestDTO) {
        double baggageWeight = 0, cargoWeight = 0, totalWeight = 0;

        String date = String.valueOf(OffsetDateTime.parse(requestDTO.getDate().toString()));

        Optional<Flight> flight = flightRepository.findByFlightNumberAndDepartureDate(requestDTO.getFlightNumber(), date);

        if (flight.isPresent()) {
            Optional<List<Baggage>> baggageList = baggageRepository.findByFlight(flight.get());
            Optional<List<Cargo>> cargoList = cargoRepository.findByFlight(flight.get());

            if (baggageList.isPresent()) {
                baggageWeight = baggageList.get()
                        .stream()
                        .mapToDouble(Baggage::getWeight)
                        .sum();

            } else {
                log.info("No baggage data found.");
            }

            if (cargoList.isPresent()) {
                cargoWeight = cargoList.get()
                        .stream()
                        .mapToDouble(Cargo::getWeight)
                        .sum();

            } else {
                log.info("No cargo data found.");
            }

            totalWeight = baggageWeight + cargoWeight;
        } else {
            log.error("No flight found.");
            throw new FlightNotFoundException("We cannot find your flight at this time.");
        }

        FlightInfoWeightResponseDTO infoWeightResponseDTO = new FlightInfoWeightResponseDTO();
        infoWeightResponseDTO.setBaggageWeight(baggageWeight);
        infoWeightResponseDTO.setCargoWeight(cargoWeight);
        infoWeightResponseDTO.setTotalWeight(totalWeight);

        return infoWeightResponseDTO;
    }
}
