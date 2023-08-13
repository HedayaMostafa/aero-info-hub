package com.smart4aviation.flightInformation.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart4aviation.flightInformation.model.Baggage;
import com.smart4aviation.flightInformation.model.Cargo;
import com.smart4aviation.flightInformation.model.Flight;
import com.smart4aviation.flightInformation.model.FlightCargoInfo;
import com.smart4aviation.flightInformation.repository.BaggageRepository;
import com.smart4aviation.flightInformation.repository.CargoRepository;
import com.smart4aviation.flightInformation.repository.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Order(value=2)
@Slf4j
@Component
public class CargoService  implements CommandLineRunner {

    private final String CARGO_JSON = "/cargo.json";

    private final BaggageRepository baggageRepository;
    private final CargoRepository cargoRepository;

    private final FlightRepository flightRepository;

    @Autowired
    public CargoService(BaggageRepository baggageRepository, CargoRepository cargoRepository, FlightRepository flightRepository) {
        this.baggageRepository = baggageRepository;
        this.cargoRepository = cargoRepository;
        this.flightRepository = flightRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        try {
            TypeReference<List<FlightCargoInfo>> typeReference = new TypeReference<>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream(CARGO_JSON);
            List<FlightCargoInfo> flightCargoInfos =  new ObjectMapper().readValue(inputStream, typeReference);

            for (FlightCargoInfo flightCargoInfo : flightCargoInfos) {
                Optional<Flight> optionalFlight = flightRepository.findById(flightCargoInfo.getFlightId());

                optionalFlight.ifPresent(flight -> {
                    flightCargoInfo.getBaggage().forEach(baggage -> baggage.setFlight(flight));
                    flightCargoInfo.getCargo().forEach(cargo -> cargo.setFlight(flight));

                    List<Baggage> batchedBaggage = new ArrayList<>(flightCargoInfo.getBaggage());
                    List<Cargo> batchedCargo = new ArrayList<>(flightCargoInfo.getCargo());

                    baggageRepository.saveAll(batchedBaggage);
                    cargoRepository.saveAll(batchedCargo);
                });
            }
            log.info("The cargo has been added.");

        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
