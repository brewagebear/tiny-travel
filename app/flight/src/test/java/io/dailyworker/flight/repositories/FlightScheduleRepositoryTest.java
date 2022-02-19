package io.dailyworker.flight.repositories;

import io.dailyworker.flight.domain.Airplane;
import io.dailyworker.flight.domain.AirplaneSeat;
import io.dailyworker.flight.domain.Airport;
import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.enumerate.AirportInfo;
import io.dailyworker.flight.enumerate.CountryInfo;
import io.dailyworker.flight.exceptions.NoSuchCodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FlightScheduleRepositoryTest {

    @Autowired
    FlightScheduleRepository flightScheduleRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    AirPlanRepository airPlanRepository;

    private Airplane airPlane;

    @Transactional
    @BeforeEach
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
    }

    @Test
    @Transactional
    @DisplayName("성공적으로 비행 일정 리스트를 가져올 수 있다.")
    public void searchFlightScheduleTest() throws Exception {
        //given
        ZonedDateTime departAt = ZonedDateTime.parse("2022-01-05T00:00:00+09:00[Asia/Seoul]");
        ZonedDateTime arriveAt = ZonedDateTime.parse("2022-01-10T00:00:00+09:00[Asia/Seoul]");

        Airport jejuAirport = airportRepository.findByAirport(AirportInfo.JEJU)
                .orElseThrow(NoSuchCodeException::new);

        Airport gimpoAirport = airportRepository.findByAirport(AirportInfo.GMP)
                .orElseThrow(NoSuchCodeException::new);


        FlightSchedule flightSchedule = FlightSchedule.createFlightSchedule(airPlane, gimpoAirport, jejuAirport, departAt.toInstant(), arriveAt.toInstant());
        flightScheduleRepository.save(flightSchedule);

        //when
        List<FlightSchedule> flightSchedules = flightScheduleRepository.findFlightSchedule(gimpoAirport, jejuAirport, departAt.toInstant(), arriveAt.toInstant());
        FlightSchedule expectedFlightSchedule = flightSchedules.get(0);

        //then
        assertAll(
                () -> assertEquals(expectedFlightSchedule.getAirplane(), airPlane),
                () -> assertEquals(expectedFlightSchedule.getTo().itatCode(), jejuAirport.itatCode()),
                () -> assertEquals(expectedFlightSchedule.getFrom().itatCode(), gimpoAirport.itatCode()),
                () -> assertEquals(expectedFlightSchedule.getArriveAt(), arriveAt.toInstant()),
                () -> assertEquals(expectedFlightSchedule.getDepartAt(), departAt.toInstant())
        );
    }

    @Test
    @Transactional
    @DisplayName("여러개의 비행 일정 중 검색 조건에 맞는 비행일정을 찾는다.")
    public void  searchMultipleFlightScheduleTest() throws Exception {
        //given
        ZonedDateTime containSearchConditionDepartAt = LocalDate.parse("2022-01-05").atStartOfDay(ZoneId.of("UTC"));
        ZonedDateTime containSearchConditionArriveAt = LocalDate.parse("2022-01-10").atStartOfDay(ZoneId.of("UTC"));;
        ZonedDateTime containSearchConditionDepartAt2 = LocalDate.parse("2022-01-06").atStartOfDay(ZoneId.of("UTC"));;
        ZonedDateTime containSearchConditionArriveAt2 = LocalDate.parse("2022-01-09").atStartOfDay(ZoneId.of("UTC"));

        ZonedDateTime notContainSearchConditionDepartAt = LocalDate.parse("2021-12-05").atStartOfDay(ZoneId.of("UTC"));
        ZonedDateTime notContainSearchConditionArriveAt = LocalDate.parse("2022-12-10").atStartOfDay(ZoneId.of("UTC"));

        Airport jejuAirport = airportRepository.findByAirport(AirportInfo.JEJU)
                .orElseThrow(NoSuchCodeException::new);

        Airport gimpoAirport = airportRepository.findByAirport(AirportInfo.GMP)
                .orElseThrow(NoSuchCodeException::new);

        List<FlightSchedule> schedules = new ArrayList<>();

        schedules.add(FlightSchedule.createFlightSchedule(airPlane, jejuAirport, gimpoAirport, containSearchConditionDepartAt.toInstant(), containSearchConditionArriveAt.toInstant()));
        schedules.add(FlightSchedule.createFlightSchedule(airPlane, gimpoAirport, jejuAirport, containSearchConditionDepartAt2.toInstant(), containSearchConditionArriveAt2.toInstant()));
        schedules.add(FlightSchedule.createFlightSchedule(airPlane, jejuAirport, gimpoAirport, notContainSearchConditionDepartAt.toInstant(), notContainSearchConditionArriveAt.toInstant()));

        flightScheduleRepository.saveAll(schedules);

        //when
        List<FlightSchedule> flightSchedules = flightScheduleRepository.findFlightSchedule(gimpoAirport, jejuAirport, containSearchConditionDepartAt.toInstant(), containSearchConditionDepartAt.toInstant());


        System.out.println("사이즈 " + flightSchedules.size());
    }
}