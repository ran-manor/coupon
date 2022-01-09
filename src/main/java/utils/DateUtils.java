package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {
    public static Date javaDateToSqlDate(java.util.Date date){
        if (date instanceof Date){
            return (Date) date;
        }
        return localDateToSqlDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
    public static Date localDateToSqlDate(LocalDate localDate){
        return Date.valueOf(localDate);
    }

    private static LocalDate randomStartDate(){
        return LocalDate.now().minusDays((int)(Math.random()*14)+1);
    }

    private static LocalDate randomEndDate(){
        return LocalDate.now().plusDays((int)(Math.random()*14)+1);
    }

    public static Date getRandomSqlStartDate(){
        return localDateToSqlDate(randomStartDate());
    }
    public static Date getRandomSqlEndDate(){
        return localDateToSqlDate(randomEndDate());
    }



    public static String beautifyLocalDate(LocalDate localDate){
        return String.format("%02d/%02d/%04d",
                localDate.getDayOfMonth(),localDate.getMonthValue(),localDate.getYear());
    }

    public static String beautifyDateTime(LocalDateTime localDate){
        return String.format("%02d/%02d/%04d %02d:%02d:%02d",
                localDate.getDayOfMonth(),localDate.getMonthValue(),localDate.getYear(),
                localDate.getHour(),localDate.getMinute(),localDate.getSecond()
        );
    }

    public static String getLocalDateTime(){
        return "["+beautifyDateTime(LocalDateTime.now())+"]";
    }

    public static void main(String[] args) {
        System.out.println(getLocalDateTime());
    }

}
