package com.ding.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

    public String dateFormat(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  dateFormat.format(date);
    }
}
