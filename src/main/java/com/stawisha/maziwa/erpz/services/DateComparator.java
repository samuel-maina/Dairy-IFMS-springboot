package com.stawisha.maziwa.erpz.services;


import com.stawisha.maziwa.erpz.services.LogDateHolder;
import java.util.Comparator;

 public class DateComparator implements Comparator<LogDateHolder>{

    @Override
    public int compare(LogDateHolder a, LogDateHolder b) {
        return b.getDate().compareTo(a.getDate());
        }
}