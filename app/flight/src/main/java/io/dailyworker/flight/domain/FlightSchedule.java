package io.dailyworker.flight.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlightSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Airport from;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Airport to;

    private ZonedDateTime departDate;
    private ZonedDateTime arriveDate;

    public static FlightSchedule createFlightSchedule(Airplane airPlane, Airport departAirport, Airport arriveAirport, ZonedDateTime departDate, ZonedDateTime arriveDate) {
        FlightSchedule flightSchedule = new FlightSchedule();
        flightSchedule.airplane = airPlane;
        flightSchedule.from = departAirport;
        flightSchedule.to = arriveAirport;
        flightSchedule.departDate = departDate;
        flightSchedule.arriveDate = arriveDate;
        return flightSchedule;
    }
}
