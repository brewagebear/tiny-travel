package io.dailyworker.flight.enumerate;

import io.dailyworker.flight.enumerate.converter.EnumCodeDelegator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CountryInfo implements EnumCodeDelegator {
    DOMESTIC("DO", "DOMESTIC", "국내"),
    ASIA("AS", "ASIA","아시아"),
    EUROPE("EU", "EUROPE", "유럽"),
    AFRICA("AF", "AFRICA", "아프리카"),
    OCEANIA("OC", "OCEANIA", "오세아니아"),
    NORTH_AMERICA("NA", "NORTH AMERICA", "미주"),
    SOUTH_AMERICA("SA", "SOUTH AMERICA", "중남미"),
    CHINA("CN", "CHINA", "중국"),
    JAPAN("JP", "JAPAN", "일본");

    private final String code;
    private final String engName;
    private final String korName;
}
