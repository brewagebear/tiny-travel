package io.dailyworker.flight.repositories;

import io.dailyworker.flight.domain.Seat;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, UUID> {

}
