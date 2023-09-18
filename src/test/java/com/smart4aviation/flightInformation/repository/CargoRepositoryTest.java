package com.smart4aviation.flightInformation.repository;

import com.smart4aviation.flightInformation.model.Cargo;
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
class CargoRepositoryTest {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private FlightRepository flightRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        cargoRepository.deleteAll();
        flightRepository.deleteAll();
    }

    @Test
    void findByFlight_CargoListExists_ListOfCargo() {
        //given
        Flight flight = new Flight(
                null,
                5222,
                "ANC",
                "MIT",
                "2014-01-20T04:51:25-01:00"
        );
        flightRepository.save(flight);

        List<Cargo> expectedCargoList = new ArrayList<>();
        expectedCargoList.add(new Cargo(
                null,
                151,
                "kg",
                664,
                flight
        ));
        expectedCargoList.add(new Cargo(
                null,
                152,
                "kg",
                666,
                flight
        ));

        cargoRepository.saveAll(expectedCargoList);

        //when
        Optional<List<Cargo>> actualCargoList =  cargoRepository.findByFlight(flight);

        //then
        assertTrue(actualCargoList.isPresent());
        assertEquals(expectedCargoList, actualCargoList.get());
    }
}