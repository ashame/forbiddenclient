package org.nathantehbeast.forbiddenclient.swing;

import org.nathantehbeast.forbiddenclient.utils.Utilities;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * Created by Nathan on 7/1/14.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */
public class Application extends JFrame {

    protected final String[] titleMessages = new String[] {"Used condoms for sale", "Buttering the corn",
            "I honestly don't know what to put here", "Dirty stories about corn", "Wibble wobble", "Might as well put cornflakes in there",
            "Corn bread as well", "#RuwenBrokeAV2014", "One day, I shall finish my guide", "Cardboard boxes for sale", "Home of Drillar noob",
            "Loli VR FPS", "Invite Ori to PvE things and he will GvG you", "I cut you", "Bite my shiny metal ass", "Parting the Red Sea",
            "insert title here", "Cyndy is fat"};

    protected final String DB_URL = "";
    protected final String DB_USR = "";
    protected final String DB_PWD = "";

    protected final String VERSION = "1.0.4a";

    private JTabbedPane tabbedPane;
    private JPanel statusPanel;
    private JLabel statusLabel, pointsLabel;
    private int points = 0;

    private ManagementPanel managementPanel;
    private OverviewPanel overviewPanel;

    private String user = "";

    public Application() {
        super();

        AppMenuBar appMenuBar = new AppMenuBar(this);
        LoginPanel loginPanel = new LoginPanel(this);
        managementPanel = new ManagementPanel();
        overviewPanel = new OverviewPanel(this);

        int msg = new Random().nextInt(titleMessages.length);

        setTitle("Forbidden - " + titleMessages[msg]);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Image icon = Utilities.getImage(Utilities.getContentDirectory() + "images/icon.png");
        setIconImage(icon);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Login", loginPanel);
        tabbedPane.addTab("Overview", overviewPanel);

        tabbedPane.setSelectedIndex(1);

        statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(getWidth(), 20));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.LINE_AXIS));

        statusLabel = new JLabel("Not logged in.");
        pointsLabel = new JLabel("Points: " + points);

        statusPanel.add(statusLabel);
        statusPanel.add(Box.createHorizontalGlue());
        statusPanel.add(pointsLabel);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F5) {
                    overviewPanel.refreshData();
                }
                if (e.getKeyCode() == KeyEvent.VK_F4) {
                    randomizeTitle();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        setJMenuBar(appMenuBar);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(getStatusPanel(), BorderLayout.SOUTH);

        getContentPane().setPreferredSize(new Dimension(640, 500));
        getContentPane().revalidate();
        setResizable(false);
        pack();
    }

    public ManagementPanel getManagementPanel() {
        return managementPanel;
    }

    public JPanel getStatusPanel() {
        return statusPanel;
    }

    public OverviewPanel getOverviewPanel() {
        return overviewPanel;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    public void setPoints(int i) {
        this.points = i;
        pointsLabel.setText("Points: " + points);
    }

    public void randomizeTitle() {
        int msg = new Random().nextInt(titleMessages.length);
        setTitle("Forbidden - " + titleMessages[msg]);
    }

    public void setAppTitle(String title) {
        setTitle("Forbidden - " + title);
    }

}
