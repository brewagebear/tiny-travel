package io.dailyworker.flight.repositories;

import io.dailyworker.flight.domain.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirPlanRepository extends JpaRepository<Airplane, Long> {
}
