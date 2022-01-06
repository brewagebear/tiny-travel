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
                () -> assertEquals(expectedFlightSchedule.getArriveAirPort().getItatCode(), jejuAirPort.getItatCode()),
                () -> assertEquals(expectedFlightSchedule.getDepartAirPort().getItatCode(), gimpoAirPort.getItatCode()),
                () -> assertEquals(expectedFlightSchedule.getArriveDate(), arriveDate),
                () -> assertEquals(expectedFlightSchedule.getDepartDate(), departDate)
        );
    }
}