package com.github.boardyb.jofogas.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class CustomDateParser {

    private Logger logger = LoggerFactory.getLogger(CustomDateParser.class);

    public static LocalDate parseString(String dateString) {
        String monthShortFormString = dateString.split(" ")[0];
        String dayOfTheMonthString = dateString.split(" ")[1];
        String dayWithoutDot = dayOfTheMonthString.substring(0, dayOfTheMonthString.length() - 1);
        int dayOfTheMonth = Integer.parseInt(dayWithoutDot);
        int numberOfMonth = getConvertibleMonths().indexOf(monthShortFormString) + 1;
        int month = numberOfMonth == -1 ? 1 : numberOfMonth;
        return LocalDate.of(LocalDate.now().getYear(), month, dayOfTheMonth);
    }

    public static LocalDate parseString(String dateString, LocalDate localDateOfToday) {
        String monthShortFormString = dateString.split(" ")[0];
        String dayOfTheMonthString = dateString.split(" ")[1];
        String dayWithoutDot = dayOfTheMonthString.substring(0, dayOfTheMonthString.length() - 1);
        int dayOfTheMonth = Integer.parseInt(dayWithoutDot);
        int numberOfMonth = getConvertibleMonths().indexOf(monthShortFormString) + 1;
        int month = numberOfMonth == -1 ? 1 : numberOfMonth;
        return LocalDate.of(localDateOfToday.getYear(), month, dayOfTheMonth);
    }

    private static List<String> getConvertibleMonths() {
        return newArrayList("jan", "feb", "márc", "ápr", "máj", "jún", "júl", "aug", "szept", "okt", "nov", "dec");
    }

}
