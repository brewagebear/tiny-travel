package io.dailyworker.flight.enumerate;

import io.dailyworker.flight.enumerate.converter.EnumCodeDelegator;
import lombok.RequiredArgsConstructor;

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
}
