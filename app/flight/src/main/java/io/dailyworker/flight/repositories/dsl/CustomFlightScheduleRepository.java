package io.dailyworker.flight.repositories.dsl;

import io.dailyworker.flight.domain.Airport;
import io.dailyworker.flight.domain.FlightSchedule;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public interface CustomFlightScheduleRepository {
    List<FlightSchedule> findFlightSchedule(Airport departAirport, Airport arriveAirport, ZonedDateTime departDate, ZonedDateTime arriveDate);
    List<FlightSchedule> findOneWayFlightSchedule(Airport departAirport, Airport arriveAirport, LocalDate departDate);
}
