//package io.dailyworker.flight.controller;
//
//import io.dailyworker.flight.domain.Airplane;
//import io.dailyworker.flight.domain.Airport;
//import io.dailyworker.flight.domain.FlightSchedule;
//import io.dailyworker.flight.enumerate.AirportInfo;
//import io.dailyworker.flight.enumerate.CountryInfo;
//import io.dailyworker.flight.repositories.AirPlanRepository;
//import io.dailyworker.flight.repositories.AirportRepository;
//import io.dailyworker.flight.repositories.FlightScheduleRepository;
//import io.dailyworker.flight.service.FlightScheduleService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@AutoConfigureWebTestClient(timeout = "36000")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class FlightScheduleControllerTest {
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @Autowired
//    FlightScheduleRepository flightScheduleRepository;
//
//    @Autowired
//    AirportRepository airportRepository;
//
//    @Autowired
//    AirPlanRepository airplaneRepository;
//
//    @Autowired
//    FlightScheduleService flightScheduleService;
//
//    @BeforeEach
//    @Transactional
//    public void createFlightSchedule() {
//        Airplane airplane = new Airplane("SU-123", "707", 100);
//        Airport gimpoAirport = new Airport(CountryInfo.DOMESTIC, AirportInfo.GMP);
//        Airport jejuAirport = new Airport(CountryInfo.DOMESTIC, AirportInfo.JEJU);
//
//        airplaneRepository.save(airplane);
//        airportRepository.save(gimpoAirport);
//        airportRepository.save(jejuAirport);
//
//        ZonedDateTime departAt = LocalDate.parse("2022-01-05").atStartOfDay(ZoneId.of("UTC"));
//        ZonedDateTime arriveAt = LocalDate.parse("2022-01-10").atStartOfDay(ZoneId.of("UTC"));
//
//        FlightSchedule flightSchedule = FlightSchedule.createFlightSchedule(airplane, gimpoAirport, jejuAirport, departAt.toInstant(), arriveAt.toInstant());
//        flightScheduleRepository.save(flightSchedule);
//    }
//
//    @Test
//    @DisplayName("Http Request로 편도 항공권 조회가 가능하다")
//    public void atdd() throws Exception {
//        List<FlightSchedule> flightSchedules = webTestClient.get()
//                .uri("/api/v1/schedule/" + "GMP-CJU-20220104")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(FlightSchedule.class).hasSize(1)
//                .returnResult()
//                .getResponseBody();
//    }
//}