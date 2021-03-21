package demetra.domain;

public class Frequency {

    private static final int DefaultDaysPerWeek = 7;

    private Frequency() {}

    public static double ofDaily(double amount) {
        return ofDaily(amount, DefaultDaysPerWeek);
    }

    public static double ofDaily(double amount, int daysPerWeek) {
        return amount * daysPerWeek;
    }

    public static double ofWeekly(double amount) {
        return ofWeekly(amount, DefaultDaysPerWeek);
    }

    public static double ofWeekly(double amount, int daysPerWeek) {
        return amount * daysPerWeek / DefaultDaysPerWeek;
    }

}
