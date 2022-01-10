package io.dailyworker.flight.service;

import io.dailyworker.flight.domain.AirPort;
import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.domain.dto.FlightScheduleRequest;
import io.dailyworker.flight.exceptions.NoSuchCodeException;
import io.dailyworker.flight.repositories.AirportRepository;
import io.dailyworker.flight.repositories.FlightScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FlightScheduleService {
    private final FlightScheduleRepository flightScheduleRepository;
    private final AirportRepository airportRepository;

    public List<FlightSchedule> searchSchedule(FlightScheduleRequest dto) {
        // TODO : 일급 콜렉션으로 변경 예정
        List<AirPort> airPorts = airportRepository.findByAirportIn(dto.getAirportInfos())
                .stream()
                .sorted(Comparator.comparing(entity -> dto.getAirportInfos().indexOf(entity.getAirport())))
                .collect(Collectors.toList());

        return flightScheduleRepository.findOneWayFlightSchedule(airPorts.get(0), airPorts.get(1), dto.getDate());
    }
}
