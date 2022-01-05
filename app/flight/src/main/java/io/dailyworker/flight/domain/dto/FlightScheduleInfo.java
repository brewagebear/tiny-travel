package io.dailyworker.flight.domain.dto;

import io.dailyworker.flight.domain.AirPort;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightScheduleInfo {
    private AirPort departAirPort;
    private AirPort arriveAirPort;
    private LocalDate departDate;
    private LocalDate arriveDate;
    private int passengerCnt;

    @Builder
    public FlightScheduleInfo(AirPort departAirPort, AirPort arriveAirPort, LocalDate departDate, LocalDate arriveDate, int passengerCnt) {
        this.departAirPort = departAirPort;
        this.arriveAirPort = arriveAirPort;
        this.departDate = departDate;
        this.arriveDate = arriveDate;
        this.passengerCnt = passengerCnt;
    }
}
