package com.project.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {
    // Định dạng mẫu cho ngày/tháng/năm
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    // Đối tượng SimpleDateFormat
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    // Phương thức để chuyển đổi từ Date sang String
    public static String formatDate(Date date) {
        return sdf.format(date);
    }

    // Phương thức để chuyển đổi từ String sang Date
    public static Date parseDate(String dateString) throws ParseException {
        return sdf.parse(dateString);
    }


}
