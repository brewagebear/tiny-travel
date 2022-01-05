package io.dailyworker.flight.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlightSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_id")
    private AirPlane airPlane;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private AirPort departAirPort;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private AirPort arriveAirPort;

    private LocalDate departDate;
    private LocalDate arriveDate;

    public static FlightSchedule createFlightSchedule(AirPlane airPlane, AirPort departAirPort, AirPort arriveAirPort, LocalDate departDate, LocalDate arriveDate) {
        FlightSchedule flightSchedule = new FlightSchedule();
        flightSchedule.airPlane = airPlane;
        flightSchedule.departAirPort = departAirPort;
        flightSchedule.arriveAirPort = arriveAirPort;
        flightSchedule.departDate = departDate;
        flightSchedule.arriveDate = arriveDate;
        return flightSchedule;
    }
}
