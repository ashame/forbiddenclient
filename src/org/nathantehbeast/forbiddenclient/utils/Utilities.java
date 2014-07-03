package org.nathantehbeast.forbiddenclient.utils;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

/**
 * Created by Nathan on 7/2/14.
 * http://www.powerbot.org/community/user/523484-nathan-l/
 * http://www.excobot.org/forum/user/906-nathan/
 */
public class Utilities {

    public static boolean downloadFile(String url, String location) {
        try {
            final URLConnection connection = new URL(url).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0");

            final int contentLength = connection.getContentLength();
            final File destination = new File(location);

            if (destination.exists()) {
                final URLConnection savedFileConnection = destination.toURI().toURL().openConnection();
                if (savedFileConnection.getContentLength() == contentLength) {
                    return true;
                }
            } else {
                final File parent = destination.getParentFile();
                if (!parent.exists()) parent.mkdirs();
            }

            final ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());

            final FileOutputStream fos = new FileOutputStream(destination);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();

        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }

        System.out.println(url + "->" + location);
        return new File(location).exists();
    }

    public static String getContentDirectory() {
        return System.getProperty("user.home") + File.separator + "forbidden-client" + File.separator;
    }

    public static Image getImage(String file) {
        return Toolkit.getDefaultToolkit().createImage(file);
    }

    public static TableModel resultSetToTableModel(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columns = metaData.getColumnCount();
            Vector<String> columnNames = new Vector<>();

            for (int i = 0; i < columns; i++) {
               columnNames.addElement(metaData.getColumnLabel(i + 1));
            }

            Vector<Vector<Object>> rows = new Vector<>();

            while (rs.next()) {
                Vector<Object> newRow = new Vector<>();

                for (int i = 1; i <= columns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.add(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
