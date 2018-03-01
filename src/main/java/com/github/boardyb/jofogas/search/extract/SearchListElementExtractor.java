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

@Component
public class SearchListElementExtractor {

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

    private int getPictureCount(Element element) {
        Elements pictureCountElement = element.select(".picNumC");
        return pictureCountElement.isEmpty() ? 0 : Integer.parseInt(pictureCountElement.first().ownText());
    }

    private int extractPriceFromElement(Element element) {
        String priceBoxText = element.select(".priceBox").first().ownText();
        String priceString = priceBoxText.replaceAll("\\s", "");
        return isNullOrEmpty(priceString) ? 0 : Integer.parseInt(priceString);
    }

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

    protected LocalDate getLocalDateOfToday() {
        return LocalDate.now();
    }

    private LocalDate parseMonthAndDayFromShortForm(String dateString) {
        return parseString(dateString, getLocalDateOfToday());
    }

    private ArrayList<String> extractBadgeFromElement(Element element) {
        ArrayList<String> badges = newArrayList();
        element.select(".badge.jfg-badge").forEach(s -> badges.add(s.ownText()));
        return badges;
    }

    private Boolean extractValidPhoneNumberBooleanFromElement(Element element) {
        boolean validPhoneNumber;
        validPhoneNumber = element.select(".reLiSection validPhone") != null;
        return validPhoneNumber;
    }

}
