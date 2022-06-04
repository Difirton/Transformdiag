package com.difirton.transformdiag.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtil {
    private static List<SimpleDateFormat> dateFormats = new ArrayList<SimpleDateFormat>(){
        {
            add(new SimpleDateFormat("dd.MM.yyyy"));
            add(new SimpleDateFormat("dd-MM-yyyy"));
            add(new SimpleDateFormat("dd/MM/yyyy"));
            add(new SimpleDateFormat("dd.MM.yy"));
            add(new SimpleDateFormat("dd-MM-yy"));
            add(new SimpleDateFormat("dd/MM/yy"));
            add(new SimpleDateFormat("d.MM.yyyy"));
            add(new SimpleDateFormat("d-MM-yyyy"));
            add(new SimpleDateFormat("d/MM/yyyy"));
            add(new SimpleDateFormat("d.MM.yy"));
            add(new SimpleDateFormat("d-MM-yy"));
            add(new SimpleDateFormat("d/MM/yy"));
            add(new SimpleDateFormat("d.M.yyyy"));
            add(new SimpleDateFormat("d-M-yyyy"));
            add(new SimpleDateFormat("d/M/yyyy"));
            add(new SimpleDateFormat("d.M.yy"));
            add(new SimpleDateFormat("d-M-yy"));
            add(new SimpleDateFormat("d/M/yy"));
            add(new SimpleDateFormat("dd.M.yyyy"));
            add(new SimpleDateFormat("dd-M-yyyy"));
            add(new SimpleDateFormat("dd/M/yyyy"));
            add(new SimpleDateFormat("dd.M.yy"));
            add(new SimpleDateFormat("dd-M-yy"));
            add(new SimpleDateFormat("dd/M/yy"));
        }
    };

    public static Date stringToDate(String input) {
        if(input == null) {
            return null;
        }
        Date date = null;
        for (SimpleDateFormat format : dateFormats) {
            try {
                format.setLenient(false);
                date = format.parse(input);
            } catch (ParseException e) { }
            if (date != null) {
                break;
            }
        }
        return date;
    }
}