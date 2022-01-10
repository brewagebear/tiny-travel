package io.dailyworker.flight.domain.vo;

import io.dailyworker.flight.domain.dto.FlightScheduleRequest;
import io.dailyworker.flight.enumerate.AirportInfo;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlightScheduleSearchKey {
    private static final int DEPART_AIRPORT_ITAT_INDEX = 0;
    private static final int ARRIVE_AIRPORT_ITAT_INDEX = 1;
    private static final int DEPART_DATE_INDEX = 2;
    private static final int KEY_SIZE = 3;

    private final List<String> keys;
    private final String originRequestKey;

    public FlightScheduleSearchKey(String key) throws ParseException {
        this.originRequestKey = key;
        this.keys = List.of(key.split("-"));
        if(keys.size() != KEY_SIZE) {
            throw new ParseException("key size must be 3", 3);
        }
    }

    public FlightScheduleRequest toRequest() {
        return FlightScheduleRequest.builder()
                .requestKey(originRequestKey)
                .departAirPort(departAirport())
                .arriveAirPort(arriveAirport())
                .date(date())
                .build();
    }

    public AirportInfo departAirport() {
        return AirportInfo.find(this.keys.get(DEPART_AIRPORT_ITAT_INDEX));
    }

    public AirportInfo arriveAirport() {
        return AirportInfo.find(this.keys.get(ARRIVE_AIRPORT_ITAT_INDEX));
    }

    public LocalDate date() {
        return LocalDate.parse(this.keys.get(DEPART_DATE_INDEX), DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
