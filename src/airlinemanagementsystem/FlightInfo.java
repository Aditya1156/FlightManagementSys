//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package airlinemanagementsystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;

public class FlightInfo extends JFrame {
    JTable table;

    public FlightInfo() {
        // Gradient background with animation
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(72, 219, 251); // Light blue
                Color color2 = new Color(0, 82, 212);  // Deep blue
                g2d.setPaint(new java.awt.GradientPaint(0, 0, color1, 0, getHeight(), color2));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setLayout(null);
        setContentPane(gradientPanel);

        JLabel heading = new JLabel("✈️ Flight Information ✈️");
        heading.setBounds(250, 20, 300, 30);
        heading.setFont(new Font("Serif", Font.BOLD, 24));
        heading.setForeground(Color.WHITE);
        gradientPanel.add(heading);

        table = new JTable();
        styleTable(); // Apply animation and color effects to the table

        // Fetch data from the database
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from flight");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add sound and animation on row click
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playClickSound(); // Play sound on row click
                animateRowClick(table, e.getPoint()); // Zoom-in animation
            }
        });

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 80, 700, 350);
        gradientPanel.add(jsp);

        setSize(800, 500);
        setLocation(400, 200);
        setVisible(true);
    }

    // 🎨 Style the JTable with hover and header effects
    private void styleTable() {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(Color.LIGHT_GRAY);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 16));
        header.setBackground(new Color(0, 51, 153)); // Header background
        header.setForeground(Color.WHITE);

        // Add hover and color effects
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(72, 219, 251)); // Selected row color
                    c.setForeground(Color.BLACK);
                } else if (row % 2 == 0) {
                    c.setBackground(new Color(230, 230, 250)); // Alternate row color
                } else {
                    c.setBackground(Color.WHITE);
                }
                if (hasFocus) {
                    c.setBackground(Color.ORANGE);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });
    }

    // 🎧 Play Click Sound Effect
    private void playClickSound() {
        try {
            InputStream audioSrc = getClass().getResourceAsStream("/airlinemanagementsystem/sounds/click.wav");
            if (audioSrc == null) {
                System.out.println("❗ Sound file not found.");
                return;
            }
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start(); // Play the sound
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❗ Error playing sound: " + e.getMessage());
        }
    }

    // 🪄 Zoom-in Animation on Row Click
    private void animateRowClick(JTable table, java.awt.Point point) {
        int row = table.rowAtPoint(point);
        if (row != -1) {
            table.setRowHeight(row, 40); // Zoom in
            Timer timer = new Timer(300, e -> table.setRowHeight(row, 30)); // Reset after 300ms
            timer.setRepeats(false);
            timer.start();
        }
    }

    public static void main(String[] args) {
        new FlightInfo();
    }
}
