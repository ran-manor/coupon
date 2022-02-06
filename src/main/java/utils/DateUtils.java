package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {
    /**
     * gets a Java Date and converts it to SqlDate, returns if the provided date is SqlDate.
     * @param date java date.
     * @return Sql date to work with sql.
     */
    public static Date javaDateToSqlDate(java.util.Date date){
        if (date instanceof Date){
            return (Date) date;
        }
        return localDateToSqlDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    /**
     * gets a SqlDate from a LocalDate.
     * @param localDate LocalDate to work with.
     * @return a sql date from the LocalDate.
     */
    public static Date localDateToSqlDate(LocalDate localDate){
        return Date.valueOf(localDate);
    }

    /**
     * generates a random LocalDate with the date in the last 2 weeks.
     * @return the start date
     */
    private static LocalDate randomStartDate(){
        return LocalDate.now().minusDays((int)(Math.random()*14)+1);
    }

    /**
     * generates a random end date 2 weeks from now.
     * @return LocalDate the end date.
     */
    private static LocalDate randomEndDate(){
        return LocalDate.now().plusDays((int)(Math.random()*14)+1);
    }

    /**
     * uses getRandomStartDate to get a random start date in SqlDate.
     * @return SqlDate the start date.
     */
    public static Date getRandomSqlStartDate(){
        return localDateToSqlDate(randomStartDate());
    }

    /**
     * uses getRandomEndDate to get a random end date in SqlDate.
     * @return SqlDate the end date.
     */
    public static Date getRandomSqlEndDate(){
        return localDateToSqlDate(randomEndDate());
    }



    public static String beautifyLocalDate(LocalDate localDate){
        return String.format("%02d/%02d/%04d",
                localDate.getDayOfMonth(),localDate.getMonthValue(),localDate.getYear());
    }

    /**
     * generates a formatted string from a LocalDate.
     */
    public static String beautifyDateTime(LocalDateTime localDate){
        return String.format("%02d/%02d/%04d %02d:%02d:%02d",
                localDate.getDayOfMonth(),localDate.getMonthValue(),localDate.getYear(),
                localDate.getHour(),localDate.getMinute(),localDate.getSecond()
        );
    }

    /**
     * gets a formatted string of current date and time.
     * @return the string.
     */
    public static String getLocalDateTime(){
        return "["+beautifyDateTime(LocalDateTime.now())+"]";
    }
}
