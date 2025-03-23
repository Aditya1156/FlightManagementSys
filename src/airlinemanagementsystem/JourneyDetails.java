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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;

public class JourneyDetails extends JFrame implements ActionListener {
    JTable table;
    JTextField pnr;
    JButton show;

    public JourneyDetails() {
        // 🌈 Gradient background with color transition
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

        JLabel lblpnr = new JLabel("🔍 PNR ");
        lblpnr.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblpnr.setForeground(Color.WHITE);
        lblpnr.setBounds(50, 50, 150, 25);
        gradientPanel.add(lblpnr);

        pnr = new JTextField();
        pnr.setBounds(160, 50, 120, 30);
        pnr.setFont(new Font("Tahoma", Font.PLAIN, 14));
        gradientPanel.add(pnr);

        // 🚀 Button with hover effect and glow
        show = new JButton("Show Details");
        show.setBackground(new Color(0, 51, 153));
        show.setForeground(Color.WHITE);
        show.setFont(new Font("Tahoma", Font.BOLD, 14));
        show.setBounds(290, 50, 150, 30);
        show.setFocusPainted(false);
        addHoverEffect(show); // Add hover and glow effects
        show.addActionListener(this);
        gradientPanel.add(show);

        // 🎨 Table styling and animation
        table = new JTable();
        styleTable(); // Style the table

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(50, 100, 700, 300);
        gradientPanel.add(jsp);

        setSize(800, 500);
        setLocation(400, 150);
        setVisible(true);
    }

    // 🎨 Style the JTable with hover and animation effects
    private void styleTable() {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(Color.LIGHT_GRAY);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 16));
        header.setBackground(new Color(0, 51, 153)); // Header background
        header.setForeground(Color.WHITE);

        // Row hover effect and alternate row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(72, 219, 251)); // Row clicked
                    c.setForeground(Color.BLACK);
                } else if (row % 2 == 0) {
                    c.setBackground(new Color(230, 230, 250)); // Alternate row
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });

        // Add row click sound and zoom effect
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playClickSound(); // Play sound when row is clicked
                animateRowClick(table, e.getPoint()); // Row animation
            }
        });
    }

    // 🚀 Glow effect and hover animation for button
    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 120, 255));
                button.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 51, 153));
                button.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
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

    public void actionPerformed(ActionEvent ae) {
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT * FROM reservation WHERE PNR = '" + pnr.getText() + "'");
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "❗ No Information Found");
                return;
            }
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
