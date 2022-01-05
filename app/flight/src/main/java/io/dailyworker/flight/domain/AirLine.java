package io.dailyworker.flight.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AirLine {
    @Id
    private Long id;
    private String name;
}
