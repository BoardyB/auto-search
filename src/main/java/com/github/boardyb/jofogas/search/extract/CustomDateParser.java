package com.github.boardyb.jofogas.search.extract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * This class is responsible for parsing a LocalDate from the date elements formatted by jofogas.hu
 */
public class CustomDateParser {

    private Logger logger = LoggerFactory.getLogger(CustomDateParser.class);

    /**
     * This method converts a date String used by jofogas.hu to a LocalDate.
     *
     * @param dateString       date String to parse LocalDate from.
     * @param localDateOfToday LocalDate of today's date.
     * @return LocalDate parsed from the months and days of the date String and the year from today's LocalDate.
     */
    public static LocalDate parseString(String dateString, LocalDate localDateOfToday) {
        String monthShortFormString = dateString.split(" ")[0];
        String dayOfTheMonthString = dateString.split(" ")[1];
        String dayWithoutDot = dayOfTheMonthString.substring(0, dayOfTheMonthString.length() - 1);
        int dayOfTheMonth = Integer.parseInt(dayWithoutDot);
        int numberOfMonth = getConvertibleMonths().indexOf(monthShortFormString) + 1;
        int month = numberOfMonth == 0 ? 1 : numberOfMonth;
        return LocalDate.of(localDateOfToday.getYear(), month, dayOfTheMonth);
    }

    /**
     * Returns a shortened version of month names used by jofogas.hu.
     *
     * @return a List of Strings of month names in shortened format.
     */
    private static List<String> getConvertibleMonths() {
        return newArrayList("jan", "feb", "márc", "ápr", "máj", "jún", "júl", "aug", "szept", "okt", "nov", "dec");
    }

}
