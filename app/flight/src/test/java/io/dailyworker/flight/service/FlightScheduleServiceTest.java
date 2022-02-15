package io.dailyworker.flight.service;

import io.dailyworker.flight.domain.Airplane;
import io.dailyworker.flight.domain.AirplaneSeat;
import io.dailyworker.flight.domain.Airport;
import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.domain.vo.FlightScheduleSearchKey;
import io.dailyworker.flight.enumerate.AirportInfo;
import io.dailyworker.flight.enumerate.CountryInfo;
import io.dailyworker.flight.repositories.AirPlanRepository;
import io.dailyworker.flight.repositories.AirportRepository;
import io.dailyworker.flight.repositories.FlightScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FlightScheduleServiceTest {

    @Autowired
    FlightScheduleRepository flightScheduleRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    AirPlanRepository airPlanRepository;

    @Autowired
    FlightScheduleService flightScheduleService;

    private Airplane airPlane;

    @BeforeEach
    @Transactional
    public void setUp() {
        airPlane = new Airplane("SU-123", "707", 100);
        AirplaneSeat airPlaneSeat = new AirplaneSeat(airPlane);

        Airport gimpoAirport = new Airport(CountryInfo.DOMESTIC, AirportInfo.GMP);
        Airport jejuAirport = new Airport(CountryInfo.DOMESTIC, AirportInfo.JEJU);
        Airport incheonAirport = new Airport(CountryInfo.DOMESTIC, AirportInfo.INCHEON);

        airPlanRepository.save(airPlane);

        airportRepository.save(jejuAirport);
        airportRepository.save(gimpoAirport);
        airportRepository.save(incheonAirport);

        ZonedDateTime departDate = LocalDate.of(2022, 1, 5);
        ZonedDateTime arriveDate = LocalDate.of(2022, 1, 10);

        FlightSchedule flightSchedule = FlightSchedule.createFlightSchedule(airPlane, gimpoAirport, jejuAirport, departDate, arriveDate);
        flightScheduleRepository.save(flightSchedule);
    }

    @Test
    @Transactional
    @DisplayName("편도 항공권을 조회할 수 있다.")
    public void searchOneWay() throws Exception {
        //given
        FlightScheduleSearchKey flightScheduleSearchKey = new FlightScheduleSearchKey("GMP-CJU-20220105");
        //when
        List<FlightSchedule> flightSchedules = flightScheduleService.searchSchedule(flightScheduleSearchKey.toRequest());
        //then
        FlightSchedule flightSchedule = flightSchedules.get(0);

        assertAll(
                () -> assertEquals(flightSchedule.getFrom().itatCode(), AirportInfo.GMP.getCode()),
                () -> assertEquals(flightSchedule.getTo().itatCode(), AirportInfo.JEJU.getCode()),
                () -> assertEquals(flightSchedule.getDepartDate(), LocalDate.of(2022, 01, 05))
        );
    }

}