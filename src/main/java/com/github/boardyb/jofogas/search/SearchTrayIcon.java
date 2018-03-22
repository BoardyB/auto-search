package com.github.boardyb.jofogas.search;

import java.awt.*;

/**
 * This object is capable of displaying a TrayIcon on the SystemTray.
 */
public class SearchTrayIcon {

    /**
     * This method is responsible for displaying a TrayIcon on the SystemTray.
     * The image of the TrayIcon will be set from icon.png on resources path.
     *
     * @param caption caption of the displayed TrayIcon
     * @param text    text of the displayed TrayIcon
     * @throws AWTException if the desktop system tray is missing
     */
    public void displayTrayIcon(String caption, String text) throws AWTException {
        SystemTray systemTray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit()
                             .createImage(this.getClass().getResource("icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Search Result");

        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Search result.");

        systemTray.add(trayIcon);
        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
        systemTray.remove(trayIcon);
    }

}