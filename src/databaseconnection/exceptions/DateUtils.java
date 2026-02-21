package databaseconnection.exceptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date treatmentDate(String dateString) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date birthDate = simpleDateFormat.parse(dateString);
            return birthDate;
        } catch (ParseException e) {
            throw new RuntimeException("Enter the date in this format (dd/MM/yyyy)." + "\n" + e);
        }
    }
}
