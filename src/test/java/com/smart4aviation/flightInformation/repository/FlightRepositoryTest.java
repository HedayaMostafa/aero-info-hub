package com.smart4aviation.flightInformation.repository;

import com.smart4aviation.flightInformation.dto.AirportInfoRequestDTO;
import com.smart4aviation.flightInformation.dto.FlightInfoWeightRequestDTO;
import com.smart4aviation.flightInformation.model.Flight;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        flightRepository.deleteAll();
    }

    //MethodName_StateUnderTest_ExpectedBehavior
    @Test
    void findByFlightNumberAndDepartureDate_ValidInput_ReturnsFlight() {
        //given
        Flight flight = new Flight(
                null,
                 5222,
                "ANC",
                "MIT",
                "2014-01-20T04:51:25-01:00"
        );
        flightRepository.save(flight);

        //when
        FlightInfoWeightRequestDTO requestDTO = new FlightInfoWeightRequestDTO(
                5222,
                OffsetDateTime.parse("2014-01-20T04:51:25-01:00")
        );

        Optional<Flight> actualFlight = flightRepository.findByFlightNumberAndDepartureDate(
                requestDTO.getFlightNumber(),
                String.valueOf(requestDTO.getDate()));


        //then
        assertTrue(actualFlight.isPresent());
        assertEquals(flight, actualFlight.get());

    }

    @Test
    void countByDepartureAirportIATACodeAndDepartureDate_ValidInput_ReturnsCount() {
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
                "ANC",
                "MIT",
                "2014-01-20T04:51:25-01:00"
        ));

        flightRepository.saveAll(flightList);

        OffsetDateTime expectedDateTime = OffsetDateTime.parse("2014-01-20T04:51:25-01:00");
        String expectedCode = "ANC";

        long count = flightList.stream()
                .filter(flight ->
                        flight.getDepartureDate().equals(expectedDateTime.toString()) &&
                                flight.getDepartureAirportIATACode().equals(expectedCode)
                )
                .count();

        //when
        AirportInfoRequestDTO requestDTO = new AirportInfoRequestDTO(
                "ANC",
                OffsetDateTime.parse("2014-01-20T04:51:25-01:00")
        );

        long actualCount = flightRepository.countByDepartureAirportIATACodeAndDepartureDate(
                requestDTO.getCode(),
                String.valueOf(requestDTO.getDate()));

        //then
        assertEquals(count, actualCount);
    }

    @Test
    void findAllByDepartureAirportIATACodeAndDepartureDate_ValidInput_ReturnsFlights() {
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
                "ANC",
                "MIT",
                "2014-01-20T04:51:25-01:00"
        ));
        flightList.add(new Flight(
                null,
                5224,
                "YYZ",
                "MIT",
                "2014-01-20T04:51:25-01:00"
        ));

        flightRepository.saveAll(flightList);

        OffsetDateTime expectedDateTime = OffsetDateTime.parse("2014-01-20T04:51:25-01:00");
        String expectedCode = "ANC";

        long count = flightList.stream()
                .filter(flight ->
                        flight.getDepartureDate().equals(expectedDateTime.toString()) &&
                                flight.getDepartureAirportIATACode().equals(expectedCode)
                )
                .count();


        //when
        AirportInfoRequestDTO requestDTO = new AirportInfoRequestDTO(
                "ANC",
                OffsetDateTime.parse("2014-01-20T04:51:25-01:00"));

        Optional<List<Flight>> actualOptionalFlightsByDepartureAirport = flightRepository.findAllByDepartureAirportIATACodeAndDepartureDate(
                requestDTO.getCode(),
                String.valueOf(requestDTO.getDate()));

        List<Flight> actualRetrievedFlights = new ArrayList<>();
        actualOptionalFlightsByDepartureAirport.ifPresent(flights -> actualRetrievedFlights.addAll(flights));

        //then
        assertTrue(actualOptionalFlightsByDepartureAirport.isPresent());
        assertEquals(count, actualOptionalFlightsByDepartureAirport.get().size());

        for (Flight retrievedFlight : actualOptionalFlightsByDepartureAirport.get()) {
            assertEquals(requestDTO.getCode(), retrievedFlight.getDepartureAirportIATACode());
        }
    }

    @Test
    void countByArrivalAirportIATACodeAndDepartureDate_ValidInput_ReturnsCountOfFlights() {
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
                "ANC",
                "MIT",
                "2014-01-20T04:51:25-01:00"
        ));
        flightList.add(new Flight(
                null,
                5224,
                "ANC",
                "XYZ",
                "2014-01-20T04:51:25-01:00"
        ));


        flightRepository.saveAll(flightList);


        OffsetDateTime dateTime = OffsetDateTime.parse("2014-01-20T04:51:25-01:00");
        String code = "MIT";

        long count = flightList.stream()
                .filter(flight ->
                        flight.getDepartureDate().equals(dateTime.toString()) &&
                                flight.getArrivalAirportIATACode().equals(code)
                )
                .count();

        //when
        AirportInfoRequestDTO requestDTO = new AirportInfoRequestDTO(
                "MIT",
                OffsetDateTime.parse("2014-01-20T04:51:25-01:00")
        );

        long actualCount = flightRepository.countByArrivalAirportIATACodeAndDepartureDate(
                requestDTO.getCode(),
                String.valueOf(requestDTO.getDate()));

        //then
        assertEquals(actualCount, count);
    }

    @Test
    void findAllByArrivalAirportIATACodeAndDepartureDate_ValidInput_ReturnsFlights() {
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
                "ANC",
                "MIT",
                "2014-01-20T04:51:25-01:00"
        ));
        flightList.add(new Flight(
                null,
                5224,
                "YYZ",
                "MIT",
                "2014-01-20T04:51:25-01:00"
        ));

        flightRepository.saveAll(flightList);

        OffsetDateTime dateTime = OffsetDateTime.parse("2014-01-20T04:51:25-01:00");
        String code = "MIT";

        long count = flightList.stream()
                .filter(flight ->
                        flight.getDepartureDate().equals(dateTime.toString()) &&
                                flight.getArrivalAirportIATACode().equals(code)
                )
                .count();


        //when
        AirportInfoRequestDTO requestDTO = new AirportInfoRequestDTO(
                "MIT",
                OffsetDateTime.parse("2014-01-20T04:51:25-01:00"));

        Optional<List<Flight>> actualOptionalFlightsByArrivalAirport = flightRepository.findAllByArrivalAirportIATACodeAndDepartureDate(
                requestDTO.getCode(),
                String.valueOf(requestDTO.getDate()));

        List<Flight> actualRetrievedFlights = new ArrayList<>();
        actualOptionalFlightsByArrivalAirport.ifPresent(flights -> actualRetrievedFlights.addAll(flights));

        //then
        assertTrue(actualOptionalFlightsByArrivalAirport.isPresent());
        assertEquals(actualOptionalFlightsByArrivalAirport.get().size(), count);
        for (Flight retrievedFlight : actualOptionalFlightsByArrivalAirport.get()) {
            assertEquals(requestDTO.getCode(), retrievedFlight.getArrivalAirportIATACode());
        }

    }
}