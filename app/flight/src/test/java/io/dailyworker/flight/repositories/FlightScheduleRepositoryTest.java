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

    private AirPlane airPlane;

    @Transactional
    @BeforeEach
    public void setUp() {
        airPlane = new AirPlane("SU-123", "707", 100);
        AirPlaneSeat airPlaneSeat = new AirPlaneSeat(airPlane);

        AirPort gimpoAirPort = new AirPort(CountryInfo.DOMESTIC, AirportInfo.GMP);
        AirPort jejuAirPort = new AirPort(CountryInfo.DOMESTIC, AirportInfo.JEJU);
        AirPort incheonAirPort = new AirPort(CountryInfo.DOMESTIC, AirportInfo.INCHEON);

        airPlanRepository.save(airPlane);

        airportRepository.save(jejuAirPort);
        airportRepository.save(gimpoAirPort);
        airportRepository.save(incheonAirPort);
    }

    @Test
    @Transactional
    @DisplayName("성공적으로 비행 일정 리스트를 가져올 수 있다.")
    public void searchFlightScheduleTest() throws Exception {
        //given
        LocalDate departDate = LocalDate.of(2022, 1, 5);
        LocalDate arriveDate = LocalDate.of(2022, 1, 10);

        AirPort jejuAirPort = airportRepository.findByAirport(AirportInfo.JEJU)
                .orElseThrow(NoSuchCodeException::new);

        AirPort gimpoAirPort = airportRepository.findByAirport(AirportInfo.GMP)
                .orElseThrow(NoSuchCodeException::new);


        FlightSchedule flightSchedule = FlightSchedule.createFlightSchedule(airPlane, gimpoAirPort, jejuAirPort, departDate, arriveDate);
        flightScheduleRepository.save(flightSchedule);

        //when
        List<FlightSchedule> flightSchedules = flightScheduleRepository.findFlightSchedule(gimpoAirPort, jejuAirPort, departDate, arriveDate);
        FlightSchedule expectedFlightSchedule = flightSchedules.get(0);

        //then
        assertAll(
                () -> assertEquals(expectedFlightSchedule.getAirPlane(), airPlane),
                () -> assertEquals(expectedFlightSchedule.getArriveAirPort().itatCode(), jejuAirPort.itatCode()),
                () -> assertEquals(expectedFlightSchedule.getDepartAirPort().itatCode(), gimpoAirPort.itatCode()),
                () -> assertEquals(expectedFlightSchedule.getArriveDate(), arriveDate),
                () -> assertEquals(expectedFlightSchedule.getDepartDate(), departDate)
        );
    }

    @Test
    @Transactional
    @DisplayName("여러개의 비행 일정 중 검색 조건에 맞는 비행일정을 찾는다.")
    public void  searchMultipleFlightScheduleTest() throws Exception {
        //given
        LocalDate containSearchConditionDepartDate = LocalDate.of(2022, 1, 5);
        LocalDate containSearchConditionArriveDate = LocalDate.of(2022, 1, 10);
        LocalDate containSearchConditionDepartDate2 = LocalDate.of(2022, 1, 6);
        LocalDate containSearchConditionArriveDate2 = LocalDate.of(2022, 1, 9);

        LocalDate notContainSearchConditionDepartDate = LocalDate.of(2021, 12, 5);
        LocalDate notContainSearchConditionArriveDate = LocalDate.of(2021, 12, 10);

        AirPort jejuAirPort = airportRepository.findByAirport(AirportInfo.JEJU)
                .orElseThrow(NoSuchCodeException::new);

        AirPort gimpoAirPort = airportRepository.findByAirport(AirportInfo.GMP)
                .orElseThrow(NoSuchCodeException::new);

        List<FlightSchedule> schedules = new ArrayList<>();

        schedules.add(FlightSchedule.createFlightSchedule(airPlane, jejuAirPort, gimpoAirPort, containSearchConditionDepartDate, containSearchConditionArriveDate));
        schedules.add(FlightSchedule.createFlightSchedule(airPlane, gimpoAirPort, jejuAirPort, containSearchConditionDepartDate2, containSearchConditionArriveDate2));
        schedules.add(FlightSchedule.createFlightSchedule(airPlane, jejuAirPort, gimpoAirPort, notContainSearchConditionDepartDate, notContainSearchConditionArriveDate));

        flightScheduleRepository.saveAll(schedules);

        //when
        List<FlightSchedule> flightSchedules = flightScheduleRepository.findFlightSchedule(gimpoAirPort, jejuAirPort, LocalDate.of(2022, 1, 5), LocalDate.of(2022, 1, 10));


        System.out.println("사이즈 " + flightSchedules.size());
    }

}