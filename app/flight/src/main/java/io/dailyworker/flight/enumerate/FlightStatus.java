package io.dailyworker.flight.enumerate;

public enum FlightStatus {
    NORMAL("normal"),
    DELAY("delay"),
    CANCEL("cancel");

    private final String status;

    FlightStatus(String status) {
        this.status = status;
    }

    public static FlightStatus toDelay() {
        return DELAY;
    }

    public static FlightStatus toCancel() {
        return CANCEL;
    }
}
