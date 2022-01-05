package io.dailyworker.flight.service;

import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.domain.dto.FlightScheduleInfo;
import io.dailyworker.flight.repositories.FlightScheduleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FlightScheduleService {
    private FlightScheduleRepository flightScheduleRepository;

    public List<FlightSchedule> searchSchedule(FlightScheduleInfo dto) {
        return flightScheduleRepository.findByFlightScheduleInfo(dto.getDepartAirPort(), dto.getArriveAirPort(), dto.getDepartDate(), dto.getArriveDate());
    }
}
