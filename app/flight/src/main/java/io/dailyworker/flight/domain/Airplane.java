package io.dailyworker.flight.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Airplane {

    @Id
    @Column(name = "airplane_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightModelName;

    private String flightNumber;

    private Instant landingAt;

    private Instant boardingAt;

    @Embedded
    private Seats seats;

    public Airplane(String flightNumber, String flightModelName, int available,
        LocalDateTime boardingAt, LocalDateTime landingAt) {
        this.flightNumber = flightNumber;
        this.flightModelName = flightModelName;
        this.landingAt = ZonedDateTime.of(landingAt, ZoneId.of("Asia/Seoul")).toInstant();
        this.boardingAt = ZonedDateTime.of(boardingAt, ZoneId.of("Asia/Seoul")).toInstant();
        this.seats = new Seats(available);
    }

    public void reservation(Seat seat) {
        this.seats.addPassenger(seat);
    }

    public void cancelReservation(String uuid) {
        this.seats.removePassenger(uuid);
    }

    public int remainSeat() {
        return this.seats.remain();
    }
}
