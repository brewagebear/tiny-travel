package io.dailyworker.flight.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AirplaneSeat {
    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    public AirplaneSeat(Airplane airplane) {
        this.airplane = airplane;
    }

    public void updateAirPlane(Airplane airPlane) {
        this.airplane = airPlane;
        airPlane.getSeats().add(this);
    }
}
