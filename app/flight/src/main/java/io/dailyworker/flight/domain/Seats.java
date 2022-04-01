package io.dailyworker.flight.domain;

import io.dailyworker.flight.enumerate.AirplaneReservationStatus;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seats {

    @MapKeyColumn
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "airplane_seats", joinColumns = @JoinColumn(name = "airplane_id"))
    private final Map<UUID, Seat> seats = new ConcurrentHashMap<>();

    private int available;

    public Seats(int available) {
        this.available = available;
    }

    public void addPassenger(Seat seat) {
        seats.put(seat.uuid(), seat);
        available--;
    }

    public Map<UUID, Seat> list() {
        return new ConcurrentHashMap<>(seats);
    }

    public Seat findByUUID(String uuid) {
        return Optional.ofNullable(seats.get(UUID.fromString(uuid)))
            .orElseThrow(RuntimeException::new);
    }

    public int remain() {
        return available;
    }

    public void removePassenger(String uuid) {
        Seat seat = findByUUID(uuid);
        seat.updateStatus(AirplaneReservationStatus.CANCEL);
        seats.remove(UUID.fromString(uuid));
        available++;
    }
}
