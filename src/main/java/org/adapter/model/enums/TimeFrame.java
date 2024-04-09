package org.adapter.model.enums;

public enum TimeFrame {
    OneMin("1m"),
    FiveMin("5m"),
    QuoterHour("15m"),
    HalfHour("30m"),
    OneHour("1h"),
    FourHour("4h");

    private String timeframe;

    TimeFrame(String timeframe) {
        this.timeframe = timeframe;
    }

    @Override
    public String toString() {
        return timeframe;
    }
}
