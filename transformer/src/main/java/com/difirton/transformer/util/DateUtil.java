package com.difirton.transformer.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public final class DateUtil {
    private static final List<DateTimeFormatter> dateFormats = new ArrayList<>(){
        {
            add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            add(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            add(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            add(DateTimeFormatter.ofPattern("yyyy-MM-d"));
            add(DateTimeFormatter.ofPattern("yyyy.MM.d"));
            add(DateTimeFormatter.ofPattern("yyyy/MM/d"));
            add(DateTimeFormatter.ofPattern("yyyy-M-dd"));
            add(DateTimeFormatter.ofPattern("yyyy.M.dd"));
            add(DateTimeFormatter.ofPattern("yyyy/M/dd"));
            add(DateTimeFormatter.ofPattern("yyyy-M-d"));
            add(DateTimeFormatter.ofPattern("yyyy/M/d"));
            add(DateTimeFormatter.ofPattern("yyyy.M.d"));
            add(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            add(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            add(DateTimeFormatter.ofPattern("dd.MM.yy"));
            add(DateTimeFormatter.ofPattern("dd-MM-yy"));
            add(DateTimeFormatter.ofPattern("dd/MM/yy"));
            add(DateTimeFormatter.ofPattern("d.MM.yyyy"));
            add(DateTimeFormatter.ofPattern("d-MM-yyyy"));
            add(DateTimeFormatter.ofPattern("d/MM/yyyy"));
            add(DateTimeFormatter.ofPattern("d.MM.yy"));
            add(DateTimeFormatter.ofPattern("d-MM-yy"));
            add(DateTimeFormatter.ofPattern("d/MM/yy"));
            add(DateTimeFormatter.ofPattern("d.M.yyyy"));
            add(DateTimeFormatter.ofPattern("d-M-yyyy"));
            add(DateTimeFormatter.ofPattern("d/M/yyyy"));
            add(DateTimeFormatter.ofPattern("d.M.yy"));
            add(DateTimeFormatter.ofPattern("d-M-yy"));
            add(DateTimeFormatter.ofPattern("d/M/yy"));
            add(DateTimeFormatter.ofPattern("dd.M.yyyy"));
            add(DateTimeFormatter.ofPattern("dd-M-yyyy"));
            add(DateTimeFormatter.ofPattern("dd/M/yyyy"));
            add(DateTimeFormatter.ofPattern("dd.M.yy"));
            add(DateTimeFormatter.ofPattern("dd-M-yy"));
            add(DateTimeFormatter.ofPattern("dd/M/yy"));
        }
    };

    public static LocalDate stringToDate(String input) {
        if(input == null) {
            return null;
        }
        LocalDate date = null;
        for (DateTimeFormatter format : dateFormats) {
            try {
                date = LocalDate.parse(input, format);
            } catch (DateTimeParseException e) { }
            if (date != null) {
                break;
            }
        }
        return date;
    }
}