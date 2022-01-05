package io.dailyworker.flight.repositories;

import io.dailyworker.flight.domain.AirPlane;
import io.dailyworker.flight.domain.AirPort;
import io.dailyworker.flight.domain.FlightSchedule;
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

    @Test
    @Transactional
    @DisplayName("성공적으로 비행 일정 리스트를 가져올 수 있다.")
    public void searchFlightScheduleTest() throws Exception {
        //given
        AirPlane airPlane = new AirPlane("AIR-1234");
        AirPort departAirPort = new AirPort("CJU");
        AirPort arriveAirPort = new AirPort("GMP");
        LocalDate departDate = LocalDate.of(2022, 1, 5);
        LocalDate arriveDate = LocalDate.of(2022, 1, 10);

        airPlanRepository.save(airPlane);
        airportRepository.save(departAirPort);
        airportRepository.save(arriveAirPort);

        FlightSchedule flightSchedule = FlightSchedule.createFlightSchedule(airPlane, departAirPort, arriveAirPort, departDate, arriveDate);
        flightScheduleRepository.save(flightSchedule);

        //when
        List<FlightSchedule> flightSchedules = flightScheduleRepository.findByFlightScheduleInfo(departAirPort, arriveAirPort, departDate, arriveDate);
        FlightSchedule expectedFlightSchedule = flightSchedules.get(0);

        //then
        assertAll(
                () -> assertEquals(expectedFlightSchedule.getAirPlane(), airPlane),
                () -> assertEquals(expectedFlightSchedule.getArriveAirPort().getName(), arriveAirPort.getName()),
                () -> assertEquals(expectedFlightSchedule.getDepartAirPort().getName(), departAirPort.getName()),
                () -> assertEquals(expectedFlightSchedule.getArriveDate(), arriveDate),
                () -> assertEquals(expectedFlightSchedule.getDepartDate(), departDate)
        );
    }
}