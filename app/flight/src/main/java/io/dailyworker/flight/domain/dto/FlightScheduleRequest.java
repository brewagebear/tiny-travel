package io.dailyworker.flight.domain.dto;

import io.dailyworker.flight.enumerate.AirportInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightScheduleRequest {
    private String requestKey;
    private final List<AirportInfo> airportInfos = new LinkedList<>();
    private LocalDate date;

    @Builder
    public FlightScheduleRequest(String requestKey, AirportInfo departAirPort, AirportInfo arriveAirPort, LocalDate date) {
        this.requestKey = requestKey;
        this.airportInfos.add(departAirPort);
        this.airportInfos.add(arriveAirPort);
        this.date = date;
    }
}
