package io.dailyworker.flight.repositories.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dailyworker.flight.domain.Airport;
import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.domain.QAirplane;
import io.dailyworker.flight.domain.QFlightSchedule;
import io.dailyworker.flight.repositories.dsl.CustomFlightScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FlightScheduleRepositoryImpl implements CustomFlightScheduleRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FlightSchedule> findFlightSchedule(Airport departAirport, Airport arriveAirport, Instant departAt, Instant arriveAt) {
        QFlightSchedule flightSchedule = QFlightSchedule.flightSchedule;

        return queryFactory.selectFrom(flightSchedule)
                .where(
                        flightSchedule.from.eq(departAirport)
                                .and(flightSchedule.to.eq(arriveAirport))
                                .and(flightSchedule.arriveAt.between(departAt, arriveAt))
                                .and(flightSchedule.departAt.between(departAt, arriveAt))
                ).fetch();
    }

    @Override
    public List<FlightSchedule> findOneWayFlightSchedule(@NotNull Airport departAirport,
                                                         @NotNull Airport arriveAirport,
                                                         @NotNull Instant departAt) {
        QFlightSchedule flightSchedule = QFlightSchedule.flightSchedule;
        QAirplane airplane = QAirplane.airplane;

        return queryFactory.selectFrom(flightSchedule)
                .where(
                        flightSchedule.from.eq(departAirport)
                                .and(flightSchedule.to.eq(arriveAirport))
                                .and(flightSchedule.departAt.goe(departAt))
                )
                .leftJoin(flightSchedule.airplane, airplane)
                .fetchJoin()
                .fetch();
    }
}
