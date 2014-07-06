package org.nathantehbeast.forbiddenclient.swing;


import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URI;

/**
 * Created by Nathan on 7/3/14.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */
public class AppMenuBar extends JMenuBar {

    public AppMenuBar(Application application) {

        JMenu menu, subMenu;
        JMenuItem menuItem;

        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        add(menu);


        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(l -> {
            System.exit(0);
        });
        menu.add(menuItem);

        menu = new JMenu("Tools");
        add(menu);
        menu.setMnemonic(KeyEvent.VK_T);

        subMenu = new JMenu("Set Title");


        menu.add(subMenu);

        menuItem = new JMenuItem("Set custom title");
        menuItem.addActionListener(l -> {
            application.setAppTitle((JOptionPane.showInputDialog(application, "Enter Title:", "Set Title", JOptionPane.PLAIN_MESSAGE)));

        });
        subMenu.add(menuItem);

        subMenu.addSeparator();

        for (String s : application.titleMessages) {
            if (s != null) {
                menuItem = new JMenuItem(s);
                menuItem.addActionListener(l -> {
                    application.setAppTitle(s);
                });
                subMenu.add(menuItem);
            }
        }

        menuItem = new JMenuItem("Random Title (F4)");
        menuItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F4"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                application.randomizeTitle();
            }
        });
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Refresh Tables (F5)");
        menuItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F5"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                application.getOverviewPanel().refreshData();
            }
        });
        menu.add(menuItem);

        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        add(menu);

        menuItem = new JMenuItem("About");
        JEditorPane ep = new JEditorPane
                ("text/html", "<html><b>Forbidden Client © 2014 Nathan Liu</b>" +
                        "<br>" +
                        "<br>" +
                        "Github: <a href='http://github.com/nathantehbeast/forbiddenclient'>http://github.com/forbiddenclient</a>" +
                        "<br>E-mail: <a href='mailto:maplechaos@gmail.com'>maplechaos@gmail.com</a>" +
                        "<br>" +
                        "<br><small><b>Version " + application.VERSION + "</b></small></html>");
        ep.addHyperlinkListener(e -> {
            if ((e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))) {
                URI uri = URI.create(e.getURL().toString());
                try {
                    Desktop.getDesktop().browse(uri);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        ep.setEditable(false);
        ep.setBackground(new Color(0, 0, 0, 0));

        menuItem.addActionListener(l -> {
            JOptionPane.showMessageDialog(application, ep, "About", JOptionPane.INFORMATION_MESSAGE);
        });

        menu.add(menuItem);

    }
}
