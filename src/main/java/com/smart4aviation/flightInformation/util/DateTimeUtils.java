package com.smart4aviation.flightInformation.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class DateTimeUtils {

    public static LocalDateTime parseLocalDateTime(String dateTimeString) {
        OffsetDateTime odt = OffsetDateTime.parse (dateTimeString);
        return odt.toLocalDateTime();
    }
}
