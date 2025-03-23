package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class PassengerHistory extends JFrame {

    public PassengerHistory() {
        // 🎨 Set Up Frame with Gradient Background
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(33, 147, 176); // Teal Blue
                Color color2 = new Color(109, 213, 237); // Sky Blue
                g2d.setPaint(new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setLayout(new BorderLayout());
        setContentPane(gradientPanel);

        // 🎬 Frame Settings
        setTitle("🛫 Passenger Booking History");
        setBounds(200, 100, 1000, 600);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // 🧾 Column names for the table
        String[] columnNames = {
                "PNR", "Ticket", "Aadhar", "Name", "Nationality",
                "Flight Code", "Source", "Destination", "Date of Departure"
        };

        // 🟡 Create Table Model
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        styleTable(table); // Add Custom Styling to Table

        // 📜 Add Table to Scroll Pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        scrollPane.setBounds(20, 20, 940, 500);
        gradientPanel.add(scrollPane, BorderLayout.CENTER);

        // 🪄 Fetch Data and Populate Table
        fetchPassengerData(model);

        // 🎉 Make Frame Visible
        setVisible(true);
    }

    // 🎨 Style the JTable with Custom Effects
    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setGridColor(new Color(200, 200, 200));
        table.setShowGrid(true);

        // ✨ Custom Table Header
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 16));
        header.setBackground(new Color(0, 77, 153)); // Dark Blue for Header
        header.setForeground(Color.WHITE);
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        header.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.CYAN));

        // 🎭 Custom Row Renderer for Hover and Alternate Row Effect
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                            boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // 🟦 Highlight the Selected Row
                if (isSelected) {
                    c.setBackground(new Color(0, 120, 215)); // Bright Blue Selection
                    c.setForeground(Color.WHITE);
                    c.setFont(new Font("Arial", Font.BOLD, 14));
                }
                // 🎨 Alternate Row Colors for Better Contrast
                else if (row % 2 == 0) {
                    c.setBackground(new Color(224, 247, 250)); // Light Cyan for Even Rows
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(Color.WHITE); // White for Odd Rows
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });

        // 🔥 Mouse Hover Effect for Rows
        table.addMouseMotionListener(new MouseAdapter() {
            int prevRow = -1;

            @Override
            public void mouseMoved(MouseEvent e) {
                int hoverRow = table.rowAtPoint(e.getPoint());
                if (hoverRow != prevRow) {
                    table.repaint(); // Repaint to apply hover effect
                    prevRow = hoverRow;
                }
            }
        });

        // 🌟 Add Row Hover Effect
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                table.repaint();
            }
        });
    }

    // 🔄 Fetch Data from Database and Add to Table
    private void fetchPassengerData(DefaultTableModel model) {
        try {
            Conn conn = new Conn();
            String query = "SELECT pnr, ticket, aadhar, name, nationality, flightcode, src, des, ddate FROM reservation";
            ResultSet rs = conn.s.executeQuery(query);

            // 📊 Populate the Table with Data
            while (rs.next()) {
                String pnr = rs.getString("pnr");
                String ticket = rs.getString("ticket");
                String aadhar = rs.getString("aadhar");
                String name = rs.getString("name");
                String nationality = rs.getString("nationality");
                String flightCode = rs.getString("flightcode");
                String source = rs.getString("src");
                String destination = rs.getString("des");
                String departureDate = rs.getString("ddate");

                model.addRow(new Object[]{
                        pnr, ticket, aadhar, name, nationality, flightCode, source, destination, departureDate
                });
            }

            conn.s.close();
            conn.c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❗ Error fetching data from the database!");
        }
    }

    // 🚀 Main Method to Launch Frame
    public static void main(String[] args) {
        new PassengerHistory();
    }
}
