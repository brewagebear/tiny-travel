package io.dailyworker.flight.service;

import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.domain.dto.FlightScheduleInfo;
import io.dailyworker.flight.repositories.FlightScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FlightScheduleService {
    private FlightScheduleRepository flightScheduleRepository;

    @Transactional
    public List<FlightSchedule> searchSchedule(FlightScheduleInfo dto) {
        return flightScheduleRepository.findFlightSchedule(dto.getDepartAirPort(), dto.getArriveAirPort(), dto.getDepartDate(), dto.getArriveDate());
    }
}
