package io.dailyworker.flight.service;

import io.dailyworker.flight.repositories.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AirportInfoService {
    private final AirportRepository airportRepository;
}
