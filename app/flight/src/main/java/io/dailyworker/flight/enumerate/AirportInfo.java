package io.dailyworker.flight.enumerate;

import io.dailyworker.flight.enumerate.converter.EnumCodeDelegator;
import io.dailyworker.flight.exceptions.NoSuchCodeException;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum AirportInfo implements EnumCodeDelegator {
    GMP("GMP", "RKSS", "Gimpo International Airport", "김포국제공항"),
    JEJU("CJU", "RKPC", "Jeju International Airport", "제주국제공항"),
    INCHEON("ICN", "RKSI", "Incheon International Airport", "인천국제공항");

    private final String itat;
    private final String icao;
    private final String engName;
    private final String korName;

    @Override
    public String getCode() {
        return itat;
    }

    public static AirportInfo find(String code) {
        return Stream.of(values())
                .filter(airportInfo -> airportInfo.itat.equals(code))
                .findAny()
                .orElseThrow(NoSuchCodeException::new);
    }
}
