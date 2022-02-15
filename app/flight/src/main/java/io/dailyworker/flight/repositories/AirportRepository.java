package io.dailyworker.flight.repositories;

import io.dailyworker.flight.domain.Airport;
import io.dailyworker.flight.enumerate.AirportInfo;
import io.dailyworker.flight.enumerate.CountryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Optional<Airport> findByCity(CountryInfo city);
    Optional<Airport> findByAirport(AirportInfo airport);
    List<Airport> findByAirportIn(List<AirportInfo> airportInfos);
}
