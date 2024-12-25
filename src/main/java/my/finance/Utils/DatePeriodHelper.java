package my.finance.Utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class DatePeriodHelper {
    public static LocalDate[] getCurrentWeekPeriod() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return new LocalDate[]{startOfWeek, endOfWeek};
    }

    public static LocalDate[] getCurrentMonthPeriod() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        return new LocalDate[]{startOfMonth, endOfMonth};
    }

    public static LocalDate[] getCurrentYearPeriod() {
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.with(TemporalAdjusters.firstDayOfYear());
        LocalDate endOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        return new LocalDate[]{startOfYear, endOfYear};
    }

    public static LocalDate[] getCustomPeriod(LocalDate startDate, LocalDate endDate){
        return new LocalDate[]{startDate,endDate};
    }
}

