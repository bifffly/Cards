package leitner;

public enum LeitnerEnum {
    NOTINTRODUCED(0), DAILY(1), BIWEEKLY(3),
    WEEKLY(7), TWOWEEKS(14), THREEWEEKS(21),
    MONTHLY(30), SIXWEEKS(45), TWOMONTHS(60);

    private long interval;

    LeitnerEnum(long interval) {
        this.interval = interval;
    }

    public long getInterval() {
        return interval;
    }
}
