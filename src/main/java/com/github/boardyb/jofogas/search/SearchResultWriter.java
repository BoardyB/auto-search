package com.github.boardyb.jofogas.search;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

/**
 * This component is responsible for the displaying of the search results.
 * It uses SystemTray icons to display notifications on screen
 */
@Component
public class SearchResultWriter {

    /**
     * This method displays a system notification with the given SearchListElement's price and subject properties.
     * The notification is displayed via SystemTray TrayIcons.
     *
     * @param element search result which contains information that the method will display in the notification.
     * @throws AWTException if the desktop system tray is missing
     */
    public void displaySearchResult(SearchListElement element) throws AWTException {
        SearchTrayIcon notification = new SearchTrayIcon();
        notification.displayTrayIcon(String.valueOf(element.getPrice()) + " Ft", element.getSubject());
    }

    /**
     * This method is responsible for writing the given search results to a search-results.txt file.
     * It will be written in UTF-8 format and will be put in the project working directory.
     *
     * @param searchResults search results to write to file.
     * @throws IOException if failed to write to the file.
     */
    public void writeResultsToFile(Set<SearchListElement> searchResults) throws IOException {
        List<String> resultStrings =
            searchResults.stream().map((SearchListElement::getResultString)).collect(Collectors.toList());
        List<String> resultsToWrite = newArrayList(resultStrings);
        Path file = Paths.get("search-results.txt");
        Files.write(file, resultsToWrite, Charset.forName("UTF-8"));
    }

}
