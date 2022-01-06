package io.dailyworker.flight.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AirPlaneSeat {
    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_id")
    private AirPlane airplane;

    public AirPlaneSeat(AirPlane airplane) {
        this.airplane = airplane;
    }

    public void updateAirPlane(AirPlane airPlane) {
        this.airplane = airPlane;
        airPlane.getSeats().add(this);
    }
}
