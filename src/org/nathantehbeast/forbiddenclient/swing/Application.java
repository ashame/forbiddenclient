package org.nathantehbeast.forbiddenclient.swing;

import org.nathantehbeast.forbiddenclient.utils.Utilities;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Random;

/**
 * Created by Nathan on 7/1/14.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */
public class Application extends JFrame {

    protected Image icon;
    protected JTabbedPane tabbedPane;
    protected JPanel statusPanel;
    protected JLabel statusLabel;

    private LoginPanel loginPanel;
    private ManagementPanel managementPanel;
    private OverviewPanel overviewPanel;

    private String user = "";
    private final String[] titleMessages = new String[] {"Used condoms for sale",
            "I honestly don't know what to put here", "Dirty stories about corn"};

    public Application() {
        super();
        int msg = new Random().nextInt(titleMessages.length);
        setTitle("Forbidden - " + titleMessages[msg]);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        icon = Utilities.getImage(Utilities.getContentDirectory() + "images/icon.png");
        setIconImage(icon);

        tabbedPane = new JTabbedPane();
        loginPanel = new LoginPanel(this);
        managementPanel = new ManagementPanel(this);
        overviewPanel = new OverviewPanel(this);

        tabbedPane.addTab("Login", loginPanel);

        statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(getWidth(), 20));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

        statusLabel = new JLabel("Not logged in.");
        statusPanel.add(statusLabel);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(getStatusPanel(), BorderLayout.SOUTH);

        getContentPane().setPreferredSize(new Dimension(640, 500));
        getContentPane().revalidate();
        setResizable(false);
        pack();
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
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

}
