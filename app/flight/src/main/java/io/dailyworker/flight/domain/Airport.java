package io.dailyworker.flight.domain;

import io.dailyworker.flight.enumerate.AirportInfo;
import io.dailyworker.flight.enumerate.CountryInfo;
import io.dailyworker.flight.enumerate.converter.CountryInfoConverter;
import io.dailyworker.flight.enumerate.converter.AirportInfoConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CountryInfoConverter.class)
    private CountryInfo city;

    @Convert(converter = AirportInfoConverter.class)
    private AirportInfo airport;

    public Airport(CountryInfo city, AirportInfo airport) {
        this.city = city;
        this.airport = airport;
    }

    public String itatCode() {
        return this.airport.getCode();
    }

    public String cityName(String language) {
        if(language.equals("KOR")) {
            return this.city.getKorName();
        }
        return this.city.getEngName();
    }
}
