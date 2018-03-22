package com.github.boardyb.jofogas.search;

import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * This component is responsible for the displaying of the search results.
 * It uses SystemTray icons to display notifications on screen
 */
@Component
public class SearchResultWriter {

    /**
     * This method displays a system notification with the given SearchListElement's price and subject properties.
     * The notification is displayed via SystemTray TrayIcons.
     * @param element search result which contains information that the method will display in the notification.
     * @throws AWTException if the desktop system tray is missing
     */
    public void displaySearchResult(SearchListElement element) throws AWTException {
        SearchTrayIcon notification = new SearchTrayIcon();
        notification.displayTrayIcon(String.valueOf(element.getPrice()) + " Ft", element.getSubject());
    }
    
}
