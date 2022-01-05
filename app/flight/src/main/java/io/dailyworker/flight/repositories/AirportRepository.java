package io.dailyworker.flight.repositories;

import io.dailyworker.flight.domain.AirPort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<AirPort, Long> {
    Optional<AirPort> findByName(String name);
}
