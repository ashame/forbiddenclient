package org.nathantehbeast.forbiddenclient.swing;

import org.nathantehbeast.forbiddenclient.utils.Utilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Nathan on 7/2/14.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */
public class OverviewPanel extends JPanel {

    private final Application application;

    protected JPanel leftPanel, rightPanel, rightTopPanel, rightBottomPannel;
    protected JScrollPane scrollPane;
    protected JTable table1;

    public OverviewPanel(Application application) {
        this.application = application;

        setLayout(new GridLayout(1, 2, 5, 5));

        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new TitledBorder("Points Overview"));

        rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.setBorder(new TitledBorder("Others"));

        rightTopPanel = new JPanel();
        rightTopPanel.setBorder(new TitledBorder(""));

        rightBottomPannel = new JPanel();
        rightBottomPannel.setBorder(new TitledBorder(""));

        rightPanel.add(rightTopPanel);
        rightPanel.add(rightBottomPannel);

        table1 = new JTable(){
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };

        scrollPane = new JScrollPane();

        refreshTable();

        table1.setAutoCreateRowSorter(true);

        scrollPane.setViewportView(table1);

        leftPanel.add(scrollPane, BorderLayout.CENTER);

        add(leftPanel);
        add(rightPanel);
    }

    private void refreshTable() {
        Connection con;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(application.DB_URL, application.DB_USR, application.DB_PWD);
            stmt = con.createStatement();

            String query = "SELECT ipb_pfields_content.field_11, ipb_pfields_content.field_12 FROM ipb_members, ipb_pfields_content WHERE ipb_members.member_group_id >= 3 AND ipb_members.member_id = ipb_pfields_content.member_id AND ipb_pfields_content.field_11 IS NOT NULL";

            ResultSet rs = stmt.executeQuery(query);

            table1.setModel(Utilities.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        table1.getColumnModel().getColumn(0).setHeaderValue("Name");
        table1.getColumnModel().getColumn(0).setResizable(false);

        table1.getColumnModel().getColumn(1).setHeaderValue("Points");
        table1.getColumnModel().getColumn(1).setResizable(false);
        table1.getColumnModel().getColumn(1).setMaxWidth(53);
        table1.getColumnModel().getColumn(1).setMinWidth(53);
    }

}
