package io.dailyworker.flight.repositories.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dailyworker.flight.domain.Airport;
import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.domain.QFlightSchedule;
import io.dailyworker.flight.repositories.dsl.CustomFlightScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FlightScheduleRepositoryImpl implements CustomFlightScheduleRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FlightSchedule> findFlightSchedule(Airport departAirport, Airport arriveAirport, ZonedDateTime departDate, ZonedDateTime arriveDate) {
        QFlightSchedule flightSchedule = QFlightSchedule.flightSchedule;

        return queryFactory.selectFrom(flightSchedule)
                .where(
                        flightSchedule.from.eq(departAirport)
                                .and(flightSchedule.to.eq(arriveAirport))
                                .and(flightSchedule.departDate.between(departDate, arriveDate))
                                .and(flightSchedule.arriveDate.between(departDate, arriveDate))
                ).fetch();
    }

    @Override
    public List<FlightSchedule> findOneWayFlightSchedule(@NotNull Airport departAirport,
                                                         @NotNull Airport arriveAirport,
                                                         @NotNull ZonedDateTime departDate) {
        QFlightSchedule flightSchedule = QFlightSchedule.flightSchedule;

        return queryFactory.selectFrom(flightSchedule)
                .where(
                        flightSchedule.from.eq(departAirport)
                                .and(flightSchedule.to.eq(arriveAirport))
                                .and(flightSchedule.departDate.goe(departDate))
                ).fetch();
    }
}
