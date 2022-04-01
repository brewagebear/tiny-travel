package io.dailyworker.flight.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AirplaneTest {

    @Test
    @DisplayName("좌석(Seat)을 통해서 비행기(Airplane)를 예약을 하면 이용가능한 좌석 수가 줄어든다.")
    public void airplane() {
        LocalDateTime boardingAt = LocalDateTime.of(LocalDate.of(2022, 4, 1), LocalTime.of(7, 0));
        LocalDateTime landingAt = LocalDateTime.of(LocalDate.of(2022, 4, 1), LocalTime.of(8, 0));

        Airplane airplane = new Airplane("TEST", "Boeing-707", 100, boardingAt, landingAt);

        Seat seat = new Seat(airplane);

        airplane.reservation(seat);

        assertEquals(airplane.remainSeat(), 99);
    }
}