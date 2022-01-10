package io.dailyworker.flight.repositories;

import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.repositories.dsl.CustomFlightScheduleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long>, CustomFlightScheduleRepository {
    @Override
    Optional<FlightSchedule> findById(Long id);
}
