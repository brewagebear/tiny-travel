package io.dailyworker.flight.repositories;

import io.dailyworker.flight.domain.*;
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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        ZonedDateTime departDate = LocalDate.of(2022, 1, 5);
        ZonedDateTime arriveDate = LocalDate.of(2022, 1, 10);

        Airport jejuAirport = airportRepository.findByAirport(AirportInfo.JEJU)
                .orElseThrow(NoSuchCodeException::new);

        Airport gimpoAirport = airportRepository.findByAirport(AirportInfo.GMP)
                .orElseThrow(NoSuchCodeException::new);


        FlightSchedule flightSchedule = FlightSchedule.createFlightSchedule(airPlane, gimpoAirport, jejuAirport, departDate, arriveDate);
        flightScheduleRepository.save(flightSchedule);

        //when
        List<FlightSchedule> flightSchedules = flightScheduleRepository.findFlightSchedule(gimpoAirport, jejuAirport, departDate, arriveDate);
        FlightSchedule expectedFlightSchedule = flightSchedules.get(0);

        //then
        assertAll(
                () -> assertEquals(expectedFlightSchedule.getAirplane(), airPlane),
                () -> assertEquals(expectedFlightSchedule.getTo().itatCode(), jejuAirport.itatCode()),
                () -> assertEquals(expectedFlightSchedule.getFrom().itatCode(), gimpoAirport.itatCode()),
                () -> assertEquals(expectedFlightSchedule.getArriveDate(), arriveDate),
                () -> assertEquals(expectedFlightSchedule.getDepartDate(), departDate)
        );
    }

    @Test
    @Transactional
    @DisplayName("여러개의 비행 일정 중 검색 조건에 맞는 비행일정을 찾는다.")
    public void  searchMultipleFlightScheduleTest() throws Exception {
        //given
        ZonedDateTime containSearchConditionDepartDate = LocalDate.of(2022, 1, 5);
        ZonedDateTime containSearchConditionArriveDate = LocalDate.of(2022, 1, 10);
        ZonedDateTime containSearchConditionDepartDate2 = LocalDate.of(2022, 1, 6);
        ZonedDateTime containSearchConditionArriveDate2 = LocalDate.of(2022, 1, 9);

        ZonedDateTime notContainSearchConditionDepartDate = LocalDate.of(2021, 12, 5);
        ZonedDateTime notContainSearchConditionArriveDate = LocalDate.of(2021, 12, 10);

        Airport jejuAirport = airportRepository.findByAirport(AirportInfo.JEJU)
                .orElseThrow(NoSuchCodeException::new);

        Airport gimpoAirport = airportRepository.findByAirport(AirportInfo.GMP)
                .orElseThrow(NoSuchCodeException::new);

        List<FlightSchedule> schedules = new ArrayList<>();

        schedules.add(FlightSchedule.createFlightSchedule(airPlane, jejuAirport, gimpoAirport, containSearchConditionDepartDate, containSearchConditionArriveDate));
        schedules.add(FlightSchedule.createFlightSchedule(airPlane, gimpoAirport, jejuAirport, containSearchConditionDepartDate2, containSearchConditionArriveDate2));
        schedules.add(FlightSchedule.createFlightSchedule(airPlane, jejuAirport, gimpoAirport, notContainSearchConditionDepartDate, notContainSearchConditionArriveDate));

        flightScheduleRepository.saveAll(schedules);

        //when
        List<FlightSchedule> flightSchedules = flightScheduleRepository.findFlightSchedule(gimpoAirport, jejuAirport, LocalDate.of(2022, 1, 5), LocalDate.of(2022, 1, 10));


        System.out.println("사이즈 " + flightSchedules.size());
    }

}