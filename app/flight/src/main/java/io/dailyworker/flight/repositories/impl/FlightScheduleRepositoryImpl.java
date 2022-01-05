package io.dailyworker.flight.repositories.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dailyworker.flight.domain.*;
import io.dailyworker.flight.domain.dto.FlightScheduleInfo;
import io.dailyworker.flight.repositories.FlightScheduleRepository;
import io.dailyworker.flight.repositories.dsl.CustomFlightScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FlightScheduleRepositoryImpl implements CustomFlightScheduleRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FlightSchedule> findByFlightScheduleInfo(AirPort departAirPort, AirPort arriveAirPort, LocalDate departDate, LocalDate arriveDate) {
        QFlightSchedule flightSchedule = QFlightSchedule.flightSchedule;

        return queryFactory.selectFrom(flightSchedule)
                .where(
                        flightSchedule.departAirPort.eq(departAirPort)
                                .and(flightSchedule.arriveAirPort.eq(arriveAirPort))
                                .and(flightSchedule.departDate.between(departDate, arriveDate))
                                .and(flightSchedule.arriveDate.between(departDate, arriveDate))
                ).fetch();
    }
}
