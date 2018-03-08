package com.github.boardyb.jofogas.search.extract;

import com.github.boardyb.jofogas.search.SearchListElement;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static com.github.boardyb.jofogas.search.extract.CustomDateParser.parseString;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;

/**
 * This class is responsible for parsing a SearchListElement from an HTML element.
 */
@Component
public class SearchListElementExtractor {

    /**
     * This method parses SearchListElement properties from an Element object.
     *
     * @param element to create SearchListElement from.
     * @return SearchListElement instance with the properties parsed from the element.
     */
    public SearchListElement extractFromElement(Element element) {
        SearchListElement searchListElement = new SearchListElement();
        searchListElement.setId(element.select("div[id]").first().id());
        searchListElement.setSubject(element.select(".subject").text());
        searchListElement.setCategory(element.select(".category").text());
        searchListElement.setPictureCount(getPictureCount(element));
        searchListElement.setPrice(extractPriceFromElement(element));
        searchListElement.setUploadDate(extractDateFromElement(element));
        searchListElement.setBadge(extractBadgeFromElement(element));
        searchListElement.setValidPhoneNumber(extractValidPhoneNumberBooleanFromElement(element));
        return searchListElement;
    }

    /**
     * This method parses picture count from an HTML element.
     *
     * @param element to parse picture count from.
     * @return picture count as int
     */
    private int getPictureCount(Element element) {
        Elements pictureCountElement = element.select(".picNumC");
        return pictureCountElement.isEmpty() ? 0 : Integer.parseInt(pictureCountElement.first().ownText());
    }

    /**
     * This method parses price from an HTML element.
     *
     * @param element to parse price from.
     * @return price as int or 0 if there is no price in element.
     */
    private int extractPriceFromElement(Element element) {
        String priceBoxText = element.select(".priceBox").first().ownText();
        String priceString = priceBoxText.replaceAll("\\s", "");
        return isNullOrEmpty(priceString) ? 0 : Integer.parseInt(priceString);
    }

    /**
     * This method parses LocalDateTime from an HTML element.
     *
     * @param element to parse date from.
     * @return LocalDateTime.MIN if the date is more than 1 month from today's day,
     * LocalDateTime of yesterday with parsed time if element contains yesterday's time,
     * LocalDateTime of today with parsed time if element contains today's time
     * LocalDateTime of the month,day and time extracted from the element.
     */
    protected LocalDateTime extractDateFromElement(Element element) {
        String timeElementString = element.select(".time").text();
        String dateString = timeElementString.split(",")[0];
        String timeString = timeElementString.split(",")[1].trim();
        boolean dateIsMoreThanPastMonth = dateString.equalsIgnoreCase("több");
        boolean dateIsYesterday = dateString.equalsIgnoreCase("tegnap");
        boolean dateIsToday = dateString.equalsIgnoreCase("ma");

        if (dateIsMoreThanPastMonth) {
            return LocalDateTime.MIN;
        } else if (dateIsYesterday) {
            return LocalDateTime.of(getLocalDateOfToday().minusDays(1), LocalTime.parse(timeString));
        } else if (dateIsToday) {
            return LocalDateTime.of(getLocalDateOfToday(), LocalTime.parse(timeString));
        } else {
            return LocalDateTime.of(parseMonthAndDayFromShortForm(dateString), LocalTime.parse(timeString));
        }

    }

    /**
     * This method returns today's LocalDate.
     *
     * @return today's LocalDate.
     */
    protected LocalDate getLocalDateOfToday() {
        return LocalDate.now();
    }

    /**
     * This method parses a LocalDate from a date string formatted by www.jofogas.hu.
     *
     * @param dateString to parse date from.
     * @return LocalDate parsed from dateString.
     */
    private LocalDate parseMonthAndDayFromShortForm(String dateString) {
        return parseString(dateString, getLocalDateOfToday());
    }

    /**
     * This method parses badges as a List of Strings from an HTML element.
     *
     * @param element to parse badges from.
     * @return List of String objects which contain the parsed badges.
     */
    private ArrayList<String> extractBadgeFromElement(Element element) {
        ArrayList<String> badges = newArrayList();
        element.select(".badge.jfg-badge").forEach(s -> badges.add(s.ownText()));
        return badges;
    }

    /**
     * This method parses valid phone number flag from an HTML element.
     *
     * @param element to parse valid phone number from.
     * @return boolean which contains whether the element has a valid phone number or not.
     */
    private Boolean extractValidPhoneNumberBooleanFromElement(Element element) {
        boolean validPhoneNumber;
        validPhoneNumber = element.select(".reLiSection validPhone") != null;
        return validPhoneNumber;
    }

}
