package org.nathantehbeast.forbiddenclient.swing;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.security.MessageDigest;
import java.sql.*;

/**
 * Created by Nathan on 7/2/14.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */

public class LoginPanel extends JPanel {

    private final Application application;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private String passwordHash, salt, names;
    private int memberId, groupId, points;
    private boolean loggedIn = false;

    public LoginPanel(Application application) {
        this.application = application;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(135, 150, 190, 150));

        JPanel nested0 = new JPanel(new GridLayout(3, 1, 50, 5));
        JPanel nested_username = new JPanel(new GridLayout());
        JPanel nested_pw = new JPanel(new GridLayout());

        JLabel username = new JLabel("Username");
        username.setFont(username.getFont().deriveFont(Font.BOLD));

        JLabel password = new JLabel("Password");
        password.setFont(password.getFont().deriveFont(Font.BOLD));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login!");
        loginButton.setFont(loginButton.getFont().deriveFont(Font.BOLD));

        loginButton.addActionListener(e -> processLogin());
        passwordField.addActionListener(e -> processLogin());

        nested_username.add(username);
        nested_username.add(usernameField);

        nested_pw.add(password);
        nested_pw.add(passwordField);

        TitledBorder titledBorder = new TitledBorder("Login");
        titledBorder.setTitleFont(titledBorder.getTitleFont().deriveFont(Font.BOLD));

        nested0.setBorder(titledBorder);
        nested0.add(nested_username);
        nested0.add(nested_pw);
        nested0.add(loginButton);

        add(nested0, BorderLayout.CENTER);
    }

    public void disableAll() {
        usernameField.setEnabled(false);
        passwordField.setEnabled(false);
        loginButton.setEnabled(false);
    }

    public void enableAll() {
        usernameField.setEnabled(true);
        passwordField.setEnabled(true);
        loginButton.setEnabled(true);
    }

    private void processLogin() {
        disableAll();
        Thread t = new Thread() {
            public void run() {
                if (!loggedIn) {
                    updateLoginComponents(login(usernameField.getText(), new String(passwordField.getPassword())));
                } else {
                    logout();
                }
            }
        };
        t.start();
    }

    private void updateLoginComponents(boolean success) {
        SwingUtilities.invokeLater(() -> {
            if (success) {
                fetchData();
                application.setUser(names);
                application.setStatus("Welcome " + application.getUser() + "!");
                application.setPoints(points);
                if (groupId == 4 || groupId == 6 || groupId == 8) {
                    application.getTabbedPane().addTab("Management Tools", application.getManagementPanel());
                }
                loginButton.setText("Logout!");
                loginButton.setEnabled(true);
                loggedIn = true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid login. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
                enableAll();
                passwordField.requestFocus();
            }
        });
    }

    private boolean login(String user, String pwd) {
        Connection con;
        Statement statement;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(application.DB_URL, application.DB_USR, application.DB_PWD);
            statement = con.createStatement();

            PreparedStatement ps;
            ps = con.prepareStatement("SELECT member_id, member_group_id, members_pass_hash, members_pass_salt FROM ipb_members WHERE name = ? OR email = ?");

            ps.setString(1, user);
            ps.setString(2, user);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                memberId = rs.getInt("member_id");
                groupId = rs.getInt("member_group_id");
                passwordHash = rs.getString("members_pass_hash");
                salt = rs.getString("members_pass_salt");
            }

            rs.close();
            ps.close();
            statement.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return salt != null && passwordHash != null && pwd != null && checkHash(salt, passwordHash, pwd);
    }

    private void logout() {
        SwingUtilities.invokeLater(() -> {
            enableAll();
            for (Component c : application.getTabbedPane().getComponents()) {
                if (c.equals(application.getManagementPanel()))
                    application.getTabbedPane().remove(c);
            }

            usernameField.setText("");
            passwordField.setText("");

            application.setStatus("Not logged in.");
            application.setPoints(0);
            loginButton.setText("Login!");
            loggedIn = false;
        });
    }

    private void fetchData() {
        Connection con;
        Statement statement;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(application.DB_URL, application.DB_USR, application.DB_PWD);
            statement = con.createStatement();

            PreparedStatement ps;
            ps = con.prepareStatement("SELECT ipb_members.member_id, ipb_members.name, ipb_pfields_content.field_11, ipb_pfields_content.field_12 FROM ipb_members, ipb_pfields_content WHERE ipb_members.member_id = ipb_pfields_content.member_id AND ipb_members.member_id = ?");

            ps.setInt(1, memberId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                names = rs.getString("field_11");
                points = rs.getInt("field_12");
            }

            rs.close();
            ps.close();
            statement.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkHash(String salt, String hash, String pwd) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(salt.getBytes("UTF-8"), 0, salt.length());
            String saltHash = DatatypeConverter.printHexBinary(messageDigest.digest()).toLowerCase();

            messageDigest.update(pwd.getBytes("UTF-8"), 0, pwd.length());

            String mergedHash = saltHash + DatatypeConverter.printHexBinary(messageDigest.digest()).toLowerCase();
            messageDigest.update(mergedHash.getBytes("UTF-8"), 0, mergedHash.length());

            String finalHash = DatatypeConverter.printHexBinary(messageDigest.digest()).toLowerCase();
            return finalHash.equals(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
