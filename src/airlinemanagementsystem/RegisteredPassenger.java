package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisteredPassenger extends JFrame {
    DefaultTableModel model;
    JTable table;

    public RegisteredPassenger() {
        // 🎨 Gradient Background
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, new Color(255, 153, 102), getWidth(), getHeight(), new Color(255, 204, 153)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setLayout(new BorderLayout());
        setContentPane(gradientPanel);

        // 🧾 Table Columns
        String[] columnNames = { "Name", "Nationality", "Phone", "Address", "Aadhar", "Gender" };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        styleTable(table);
        gradientPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔄 Load Data
        fetchRegisteredPassengers();

        // ⚙️ Buttons
        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("✏️ Edit");
        JButton deleteButton = new JButton("🗑️ Delete");
        JButton refreshButton = new JButton("🔁 Refresh");

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        gradientPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 🎯 Button Actions
        editButton.addActionListener(e -> editPassenger());
        deleteButton.addActionListener(e -> deletePassenger());
        refreshButton.addActionListener(e -> fetchRegisteredPassengers());

        // 🖼 Window Settings
        setTitle("👥 Registered Passengers");
        setBounds(200, 100, 1000, 600);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 16));
        header.setBackground(new Color(255, 102, 0));
        header.setForeground(Color.WHITE);
    }

    private void fetchRegisteredPassengers() {
        model.setRowCount(0); // Clear table
        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("SELECT name, nationality, phone, address, aadhar, gender FROM passenger");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("name"),
                        rs.getString("nationality"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("aadhar"),
                        rs.getString("gender")
                });
            }
            conn.s.close();
            conn.c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Failed to fetch data.");
        }
    }

    private void editPassenger() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a passenger to edit.");
            return;
        }

        String originalAadhar = model.getValueAt(row, 4).toString();

        JTextField nameField = new JTextField(model.getValueAt(row, 0).toString());
        JTextField nationalityField = new JTextField(model.getValueAt(row, 1).toString());
        JTextField phoneField = new JTextField(model.getValueAt(row, 2).toString());
        JTextField addressField = new JTextField(model.getValueAt(row, 3).toString());
        JTextField aadharField = new JTextField(originalAadhar);
        JTextField genderField = new JTextField(model.getValueAt(row, 5).toString());

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Nationality:")); panel.add(nationalityField);
        panel.add(new JLabel("Phone:")); panel.add(phoneField);
        panel.add(new JLabel("Address:")); panel.add(addressField);
        panel.add(new JLabel("Aadhar:")); panel.add(aadharField);
        panel.add(new JLabel("Gender:")); panel.add(genderField);

        int result = JOptionPane.showConfirmDialog(this, panel, "✏️ Edit Passenger", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Conn conn = new Conn();
                String query = "UPDATE passenger SET name=?, nationality=?, phone=?, address=?, aadhar=?, gender=? WHERE aadhar=?";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setString(1, nameField.getText());
                pst.setString(2, nationalityField.getText());
                pst.setString(3, phoneField.getText());
                pst.setString(4, addressField.getText());
                pst.setString(5, aadharField.getText());
                pst.setString(6, genderField.getText());
                pst.setString(7, originalAadhar);
                pst.executeUpdate();
                conn.c.close();
                fetchRegisteredPassengers();
                JOptionPane.showMessageDialog(this, "✅ Passenger updated!");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Error updating passenger.");
            }
        }
    }

    private void deletePassenger() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a passenger to delete.");
            return;
        }

        String aadhar = model.getValueAt(row, 4).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this passenger?", "⚠️ Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Conn conn = new Conn();
                String query = "DELETE FROM passenger WHERE aadhar=?";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setString(1, aadhar);
                pst.executeUpdate();
                conn.c.close();
                fetchRegisteredPassengers();
                JOptionPane.showMessageDialog(this, "🗑️ Passenger deleted.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Error deleting passenger.");
            }
        }
    }

    public static void main(String[] args) {
        new RegisteredPassenger();
    }
}
