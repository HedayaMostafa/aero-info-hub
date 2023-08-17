package com.smart4aviation.flightInformation.repository;

import com.smart4aviation.flightInformation.model.Cargo;
import com.smart4aviation.flightInformation.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    Optional<List<Cargo>> findByFlight(Flight flight);
}
