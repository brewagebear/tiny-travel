package io.dailyworker.flight.repositories;

import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.repositories.dsl.CustomFlightScheduleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long>, CustomFlightScheduleRepository {
}
