package com.smart4aviation.flightInformation.repository;

import com.smart4aviation.flightInformation.model.Baggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaggageRepository extends JpaRepository<Baggage, Long> {
}
