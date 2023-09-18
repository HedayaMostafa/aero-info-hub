package com.smart4aviation.flightInformation.service.impl;

import com.smart4aviation.flightInformation.dto.FlightInfoWeightRequestDTO;
import com.smart4aviation.flightInformation.dto.FlightInfoWeightResponseDTO;
import com.smart4aviation.flightInformation.model.Baggage;
import com.smart4aviation.flightInformation.model.Cargo;
import com.smart4aviation.flightInformation.model.Flight;
import com.smart4aviation.flightInformation.repository.BaggageRepository;
import com.smart4aviation.flightInformation.repository.CargoRepository;
import com.smart4aviation.flightInformation.repository.FlightRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightInfoServiceImplTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private BaggageRepository baggageRepository;

    @Mock
    private CargoRepository cargoRepository;

    @Captor
    private ArgumentCaptor<Integer> flightNumberCaptor;

    @Captor
    private ArgumentCaptor<String> departureDateCaptor;

    @InjectMocks
    private FlightInfoServiceImpl flightInfoService;

    @AfterEach
    void tearDown() {
    }

    @Test
    void getFlightInfoWeight() {
        Flight flight = new Flight(
                null,
                5222,
                "ANC",
                "MIT",
                "2014-01-20T04:51:25-01:00"
        );
        flightRepository.save(flight);


        List<Baggage> baggageList = new ArrayList<>();
        baggageList.add(new Baggage(null, 100, "kg", 664, flight));
        baggageList.add(new Baggage(null, 150, "kg", 666, flight));
        baggageRepository.saveAll(baggageList);

        List<Cargo> cargoList = new ArrayList<>();
        cargoList.add(new Cargo(null, 200, "kg", 664, flight));
        cargoList.add(new Cargo(null, 200, "kg", 666, flight));
        cargoRepository.saveAll(cargoList);

        FlightInfoWeightRequestDTO requestDTO = new FlightInfoWeightRequestDTO(
                5222,
                OffsetDateTime.parse("2014-01-20T04:51:25-01:00")
        );


        when(flightRepository.
                findByFlightNumberAndDepartureDate(requestDTO.getFlightNumber(), String.valueOf(requestDTO.getDate())))
                .thenReturn(Optional.of(flight));

        when(baggageRepository.findByFlight(flight))
                .thenReturn(Optional.of(baggageList));

        when(cargoRepository.findByFlight(flight))
                .thenReturn(Optional.of(cargoList));

        //when
        FlightInfoWeightResponseDTO actualResult = flightInfoService.getFlightInfoWeight(requestDTO);

        assertNotNull(actualResult);
        assertEquals(250, actualResult.getBaggageWeight());
        assertEquals(400, actualResult.getCargoWeight());

        verify(flightRepository).findByFlightNumberAndDepartureDate(flightNumberCaptor.capture(), departureDateCaptor.capture());
        assertEquals(5222, flightNumberCaptor.getValue().intValue());
        assertEquals("2014-01-20T04:51:25-01:00", departureDateCaptor.getValue());

        verify(baggageRepository).findByFlight(flight);
        verify(cargoRepository).findByFlight(flight);
    }
}