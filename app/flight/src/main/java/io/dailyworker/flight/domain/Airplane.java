package io.dailyworker.flight.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Airplane {
    @Id
    @Column(name = "airplane_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "airplane")
    private final List<AirplaneSeat> seats = new ArrayList<>();

    private String flightModelName;
    private String flightNumber;
    private int available;

    public Airplane(String flightNumber, String flightModelName, int available) {
        this.flightNumber = flightNumber;
        this.flightModelName = flightModelName;
        this.available = available;
    }

    public void addSeat(AirplaneSeat seat) {
        seats.add(seat);
        this.available = available;
    }
}
