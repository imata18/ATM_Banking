import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Util {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

    public static String currentDate() {
        return LocalDate.now().format(formatter);
    }

    public static long daysBetween(String dateString1, String dateString2) {
        LocalDate date1 = LocalDate.parse(dateString1, formatter);
        LocalDate date2 = LocalDate.parse(dateString2, formatter);

        return ChronoUnit.DAYS.between(date1, date2);
    }

    public static long daysBetween(String dateString) {
        LocalDate date1 = LocalDate.parse(dateString, formatter);
        LocalDate date2 = LocalDate.now();

        return ChronoUnit.DAYS.between(date1, date2);
    }
}
