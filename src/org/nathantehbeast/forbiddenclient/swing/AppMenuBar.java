package org.nathantehbeast.forbiddenclient.swing;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Created by Nathan on 7/3/14.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */
public class AppMenuBar extends JMenuBar {

    public AppMenuBar(Application application) {

        JMenu menu;
        JMenuItem menuItem;

        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        add(menu);

        menuItem = new JMenuItem("Random Title");
        menuItem.addActionListener(l -> {
            application.randomizeTitle();
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Refresh Tables");
        menuItem.addActionListener(l -> {
            application.getOverviewPanel().refreshData();
        });
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(l -> {
            System.exit(0);
        });
        menu.add(menuItem);

        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        add(menu);

        menuItem = new JMenuItem("About");
        menuItem.addActionListener(l -> {
            JOptionPane.showMessageDialog(null, "<html> <b>Forbidden Client © 2014 Nathan Liu</b><br><br>Github: <a href='http://github.com/nathantehbeast/forbiddenclient'>http://github" +
                    ".com/forbiddenclient</a><br>E-mail: <a href='mailto:maplechaos@gmail.com'>maplechaos@gmail.com</a></html>", "About", JOptionPane.INFORMATION_MESSAGE);
        });
        menu.add(menuItem);

    }
}
