package io.dailyworker.flight.web;

import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.domain.vo.FlightScheduleSearchKey;
import io.dailyworker.flight.service.FlightScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
public class FlightScheduleController {
    private final FlightScheduleService flightScheduleService;

    @GetMapping("/{searchKey}")
    public ResponseEntity<?> searchOneWayItinerary(@PathVariable FlightScheduleSearchKey searchKey) {
        List<FlightSchedule> flightSchedules = flightScheduleService.searchSchedule(searchKey.toRequest());
        return ResponseEntity.ok(flightSchedules);
    }
}
