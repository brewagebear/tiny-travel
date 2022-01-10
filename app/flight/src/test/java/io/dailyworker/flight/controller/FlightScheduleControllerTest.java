package io.dailyworker.flight.controller;

import io.dailyworker.flight.domain.AirPlane;
import io.dailyworker.flight.domain.AirPlaneSeat;
import io.dailyworker.flight.domain.AirPort;
import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.enumerate.AirportInfo;
import io.dailyworker.flight.enumerate.CountryInfo;
import io.dailyworker.flight.repositories.AirPlanRepository;
import io.dailyworker.flight.repositories.AirportRepository;
import io.dailyworker.flight.repositories.FlightScheduleRepository;
import io.dailyworker.flight.service.FlightScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FlightScheduleControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    FlightScheduleRepository flightScheduleRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    AirPlanRepository airPlanRepository;

    @Autowired
    FlightScheduleService flightScheduleService;

    @BeforeEach
    @Transactional
    public void createFlightSchedule() {
        AirPlane airPlane = new AirPlane("SU-123", "707", 100);
        AirPlaneSeat airPlaneSeat = new AirPlaneSeat(airPlane);

        AirPort gimpoAirPort = new AirPort(CountryInfo.DOMESTIC, AirportInfo.GMP);
        AirPort jejuAirPort = new AirPort(CountryInfo.DOMESTIC, AirportInfo.JEJU);

        airPlanRepository.save(airPlane);
        airportRepository.save(gimpoAirPort);
        airportRepository.save(jejuAirPort);

        LocalDate departDate = LocalDate.of(2022, 1, 5);
        LocalDate arriveDate = LocalDate.of(2022, 1, 10);

        FlightSchedule flightSchedule = FlightSchedule.createFlightSchedule(airPlane, gimpoAirPort, jejuAirPort, departDate, arriveDate);
        flightScheduleRepository.save(flightSchedule);
    }

    @Test
    @DisplayName("Http Request로 편도 항공권 조회가 가능하다")
    public void atdd() throws Exception {
        List<FlightSchedule> flightSchedules = webTestClient.get()
                .uri("/api/v1/schedule/search/" + "GMP-CJU-20220105")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FlightSchedule.class).hasSize(1)
                .returnResult()
                .getResponseBody();


    }

}