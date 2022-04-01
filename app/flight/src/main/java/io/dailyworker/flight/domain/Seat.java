package io.dailyworker.flight.domain;

import io.dailyworker.flight.enumerate.AirplaneReservationStatus;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    @Id
    @Column(columnDefinition = "BINARY(16)", name = "seat_id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @Enumerated(value = EnumType.STRING)
    private AirplaneReservationStatus status;

    public UUID uuid() {
        return this.id;
    }

    public Seat(Airplane airplane) {
        this.airplane = airplane;
        this.status = AirplaneReservationStatus.APPLY;
    }

    public void updateStatus(AirplaneReservationStatus status) {
        this.status = status;
    }
}
