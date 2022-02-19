package io.dailyworker.flight.repositories.dsl;

import io.dailyworker.flight.domain.Airport;
import io.dailyworker.flight.domain.FlightSchedule;

import java.time.Instant;
import java.util.List;

public interface CustomFlightScheduleRepository {
    List<FlightSchedule> findFlightSchedule(Airport departAirport, Airport arriveAirport, Instant departDate, Instant arriveDate);
    List<FlightSchedule> findOneWayFlightSchedule(Airport departAirport, Airport arriveAirport, Instant departDate);
}
