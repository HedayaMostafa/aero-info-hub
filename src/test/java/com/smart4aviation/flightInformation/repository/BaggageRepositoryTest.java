package com.smart4aviation.flightInformation.repository;

import com.smart4aviation.flightInformation.model.Baggage;
import com.smart4aviation.flightInformation.model.Flight;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
class BaggageRepositoryTest {

    @Autowired
    private BaggageRepository baggageRepository;

    @Autowired
    private FlightRepository flightRepository;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        baggageRepository.deleteAll();
        flightRepository.deleteAll();
    }

    @Test
    void findByFlight_BaggageListExists_ListOfBaggage() {
        //given
        Flight flight = new Flight(
                null,
                5222,
                "ANC",
                "MIT",
                "2014-01-20T04:51:25-01:00"
        );
        flightRepository.save(flight);

        List<Baggage> expectedBaggageList = new ArrayList<>();
        expectedBaggageList.add(new Baggage(
                null,
                151,
                "kg",
                664,
                flight
        ));
        expectedBaggageList.add(new Baggage(
                null,
                152,
                "kg",
                666,
                flight
        ));

        baggageRepository.saveAll(expectedBaggageList);

        //when
        Optional<List<Baggage>> actualBaggageList =  baggageRepository.findByFlight(flight);

        //then
        assertTrue(actualBaggageList.isPresent());
        assertEquals(expectedBaggageList, actualBaggageList.get());
    }

    @Test
    void findAllByFlightIn_FlightsExist_ReturnsListOfCargo() {
        //given
        List<Flight> flightList = new ArrayList<>();
        flightList.add(new Flight(
                null,
                5222,
                "ANC",
                "MIT",
                "2014-01-20T04:51:25-01:00"
        ));
        flightList.add(new Flight(
                null,
                5223,
                "YYZ",
                "MIT",
                "2014-01-20T04:51:25-01:00"
        ));

        flightRepository.saveAll(flightList);

        List<Baggage> baggageList = new ArrayList<>();
        baggageList.add(new Baggage(
                null,
                151,
                "kg",
                664,
                flightList.get(0)
        ));
        baggageList.add(new Baggage(
                null,
                152,
                "kg",
                666,
                flightList.get(1)
        ));

        baggageRepository.saveAll(baggageList);


        //when

        Optional<List<Baggage>> actualOptionalBaggageList = baggageRepository.findAllByFlightIn(flightList);
        List<Baggage> actualBaggageList = new ArrayList<>();
        actualOptionalBaggageList.ifPresent(flights -> actualBaggageList.addAll(flights));

        //then
        assertTrue(actualOptionalBaggageList.isPresent());
        assertEquals(baggageList, actualBaggageList);
    }
}