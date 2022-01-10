package io.dailyworker.flight.repositories.dsl;

import io.dailyworker.flight.domain.AirPort;
import io.dailyworker.flight.domain.FlightSchedule;

import java.time.LocalDate;
import java.util.List;

public interface CustomFlightScheduleRepository {
    List<FlightSchedule> findFlightSchedule(AirPort departAirPort, AirPort arriveAirPort, LocalDate departDate, LocalDate arriveDate);
    List<FlightSchedule> findOneWayFlightSchedule(AirPort departAirPort, AirPort arriveAirPort, LocalDate departDate);
}
