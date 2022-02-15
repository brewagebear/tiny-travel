package io.dailyworker.flight.domain.dto;

import io.dailyworker.flight.enumerate.AirportInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightScheduleRequest {
    private String requestKey;
    private final List<AirportInfo> airportInfos = new LinkedList<>();
    private ZonedDateTime date;

    @Builder
    public FlightScheduleRequest(String requestKey, AirportInfo departAirPort, AirportInfo arriveAirPort, ZonedDateTime date) {
        this.requestKey = requestKey;
        this.airportInfos.add(departAirPort);
        this.airportInfos.add(arriveAirPort);
        this.date = date;
    }
}
