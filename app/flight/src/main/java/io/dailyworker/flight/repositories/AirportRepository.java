package io.dailyworker.flight.repositories;

import io.dailyworker.flight.domain.AirPort;
import io.dailyworker.flight.enumerate.AirportInfo;
import io.dailyworker.flight.enumerate.CountryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<AirPort, Long> {
    Optional<AirPort> findByCity(CountryInfo city);
    Optional<AirPort> findByAirport(AirportInfo airport);
}
