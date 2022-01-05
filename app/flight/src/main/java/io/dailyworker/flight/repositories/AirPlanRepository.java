package io.dailyworker.flight.repositories;

import io.dailyworker.flight.domain.AirPlane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirPlanRepository extends JpaRepository<AirPlane, Long> {
}
