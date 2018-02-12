package com.github.boardyb.jofogas.search;

import java.awt.*;

public class SearchTrayIcon {

    public void displayTrayIcon(String caption, String text) throws AWTException {
        SystemTray systemTray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");

        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("System tray icon demo");
        
        systemTray.add(trayIcon);
        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
        systemTray.remove(trayIcon);
    }

}
