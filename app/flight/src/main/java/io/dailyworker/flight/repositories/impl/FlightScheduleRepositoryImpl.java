package io.dailyworker.flight.repositories.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dailyworker.flight.domain.AirPort;
import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.domain.QFlightSchedule;
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
    public List<FlightSchedule> findFlightSchedule(AirPort departAirPort, AirPort arriveAirPort, LocalDate departDate, LocalDate arriveDate) {
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
