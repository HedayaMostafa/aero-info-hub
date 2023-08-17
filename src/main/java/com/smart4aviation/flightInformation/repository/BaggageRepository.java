package com.smart4aviation.flightInformation.repository;

import com.smart4aviation.flightInformation.model.Baggage;
import com.smart4aviation.flightInformation.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaggageRepository extends JpaRepository<Baggage, Long> {
    Optional<List<Baggage>> findByFlight(Flight flight);

    Optional<List<Baggage>> findAllByFlightIn(List<Flight> flightList);

}
