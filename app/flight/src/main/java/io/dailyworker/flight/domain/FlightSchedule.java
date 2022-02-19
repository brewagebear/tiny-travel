package io.dailyworker.flight.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
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

    private Instant departAt;
    private Instant arriveAt;

    public static FlightSchedule createFlightSchedule(Airplane airPlane, Airport departAirport, Airport arriveAirport, Instant departAt, Instant arriveAt) {
        FlightSchedule flightSchedule = new FlightSchedule();
        flightSchedule.airplane = airPlane;
        flightSchedule.from = departAirport;
        flightSchedule.to = arriveAirport;
        flightSchedule.departAt = departAt;
        flightSchedule.arriveAt = arriveAt;
        return flightSchedule;
    }

    public ZonedDateTime instantToZonedDateTime(Instant instant) {
        return instant.atZone(ZoneId.of("UTC"));
    }

    public Instant zonedDateTimeToInstant(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toInstant();
    }
}
