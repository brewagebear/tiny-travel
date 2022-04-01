package io.dailyworker.flight.repositories;

import static org.junit.jupiter.api.Assertions.*;

import io.dailyworker.flight.domain.Airplane;
import io.dailyworker.flight.domain.Seat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class SeatRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private SeatRepository seatRepository;

    @Test
    public void save() {
        LocalDateTime boardingAt = LocalDateTime.of(LocalDate.of(2022, 4, 1), LocalTime.of(7, 0));
        LocalDateTime landingAt = LocalDateTime.of(LocalDate.of(2022, 4, 1), LocalTime.of(8, 0));

        Airplane airplane = new Airplane("TEST", "Boeing-707", 100, boardingAt, landingAt);

        Seat seat = new Seat(airplane);

        Seat save = seatRepository.save(seat);

        System.out.println(save.uuid());

        airplane.reservation(seat);
    }

}