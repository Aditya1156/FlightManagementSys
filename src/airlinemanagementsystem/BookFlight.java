package airlinemanagementsystem;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.Random;
import javax.swing.*;

public class BookFlight extends JFrame implements ActionListener {

    JTextField tfaadhar;
    JLabel tfname, tfnationality, tfaddress, labelgender, labelfname, labelfcode;
    JButton bookflight, fetchButton, flight;
    Choice source, destination;
    JDateChooser dcdate;
    JLabel lblimage;

    public BookFlight() {
        // Gradient Panel as Background
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(25, 118, 210); // Blue
                Color color2 = new Color(58, 175, 169); // Teal
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        setContentPane(gradientPanel);
        gradientPanel.setLayout(null);

        // Heading with gradient animation
        JLabel heading = new JLabel("✈️ Book Your Flight ✈️");
        heading.setBounds(400, 20, 500, 40);
        heading.setFont(new Font("Serif", Font.BOLD, 32));
        heading.setForeground(Color.WHITE);
        add(heading);

        // Aadhar Input
        createLabel("Aadhar", 60, 80);
        tfaadhar = createTextField(220, 80);

        // Fetch Button
        fetchButton = createAnimatedButton("Fetch User", 380, 80, 120, 30);
        fetchButton.addActionListener(this);
        add(fetchButton);

        // User Info Labels
        createLabel("Name", 60, 130);
        tfname = createValueLabel(220, 130);

        createLabel("Nationality", 60, 180);
        tfnationality = createValueLabel(220, 180);

        createLabel("Address", 60, 230);
        tfaddress = createValueLabel(220, 230);

        createLabel("Gender", 60, 280);
        labelgender = createValueLabel(220, 280);

        // Source and Destination Dropdowns
        createLabel("Source", 60, 330);
        source = new Choice();
        source.setBounds(220, 330, 150, 25);
        add(source);

        createLabel("Destination", 60, 380);
        destination = new Choice();
        destination.setBounds(220, 380, 150, 25);
        add(destination);

        loadFlightData(); // Load source and destination from DB

        // Fetch Flights Button
        flight = createAnimatedButton("Fetch Flights", 380, 380, 120, 30);
        flight.addActionListener(this);
        add(flight);

        // Flight Info Labels
        createLabel("Flight Name", 60, 430);
        labelfname = createValueLabel(220, 430);

        createLabel("Flight Code", 60, 480);
        labelfcode = createValueLabel(220, 480);

        // Date Picker
        createLabel("Date of Travel", 60, 530);
        dcdate = new JDateChooser();
        dcdate.setBounds(220, 530, 150, 25);
        add(dcdate);

        // Airline Image with Zoom Animation
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/details.jpg"));
        Image i2 = i1.getImage().getScaledInstance(450, 320, Image.SCALE_SMOOTH);
        lblimage = new JLabel(new ImageIcon(i2));
        lblimage.setBounds(550, 80, 500, 410);
        add(lblimage);

        addImageZoomEffect(lblimage); // Zoom animation on image

        // Book Flight Button
        bookflight = createAnimatedButton("Book Flight", 220, 580, 150, 35);
        bookflight.addActionListener(this);
        add(bookflight);

        // Frame Settings
        setSize(1100, 700);
        setLocation(200, 50);
        setVisible(true);
    }

    // Load Flight Data from DB
    private void loadFlightData() {
        try {
            Conn c = new Conn();
            String query = "SELECT DISTINCT source, destination FROM flight";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                source.add(rs.getString("source"));
                destination.add(rs.getString("destination"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create Labels
    private void createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 25);
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        add(label);
    }

    // Create Value Labels
    private JLabel createValueLabel(int x, int y) {
        JLabel label = new JLabel();
        label.setBounds(x, y, 150, 25);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        add(label);
        return label;
    }

    // Create Animated TextField
    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 150, 25);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 14));

        // Focus Animation for TextField
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(new Color(58, 175, 169), 2));
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        });

        add(textField);
        return textField;
    }

    // Create Animated Buttons
    private JButton createAnimatedButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setBackground(new Color(58, 175, 169)); // Initial Color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        // Button Hover Effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(25, 118, 210)); // Change color on hover
                button.setBounds(x - 2, y - 2, width + 4, height + 4); // Zoom-in effect
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(58, 175, 169));
                button.setBounds(x, y, width, height); // Zoom-out effect
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(new Color(58, 175, 169));
            }
        });
        return button;
    }

    // Add Zoom Effect to Image
    private void addImageZoomEffect(JLabel lblimage) {
        lblimage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblimage.setBounds(545, 75, 510, 420); // Zoom-in effect
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblimage.setBounds(550, 80, 500, 410); // Zoom-out effect
            }
        });
    }

    // Action Listener Logic
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            fetchUserDetails();
        } else if (ae.getSource() == flight) {
            fetchFlightDetails();
        } else if (ae.getSource() == bookflight) {
            bookFlightTicket();
        }
    }

    // Fetch User Details from DB
    private void fetchUserDetails() {
        String aadhar = tfaadhar.getText();
        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM passenger WHERE aadhar = '" + aadhar + "'";
            ResultSet rs = conn.s.executeQuery(query);
            if (rs.next()) {
                tfname.setText(rs.getString("name"));
                tfnationality.setText(rs.getString("nationality"));
                tfaddress.setText(rs.getString("address"));
                labelgender.setText(rs.getString("gender"));
            } else {
                JOptionPane.showMessageDialog(null, "❌ Invalid Aadhar. Please enter correct details.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fetch Flight Details
    private void fetchFlightDetails() {
        String src = source.getSelectedItem();
        String dest = destination.getSelectedItem();
        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM flight WHERE source = '" + src + "' AND destination = '" + dest + "'";
            ResultSet rs = conn.s.executeQuery(query);
            if (rs.next()) {
                labelfname.setText(rs.getString("f_name"));
                labelfcode.setText(rs.getString("f_code"));
            } else {
                JOptionPane.showMessageDialog(null, "❌ No Flights Found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Book Flight Ticket - Only If All Fields Are Filled
    private void bookFlightTicket() {
        // Fetch all input values
        String aadhar = tfaadhar.getText().trim();
        String name = tfname.getText().trim();
        String nationality = tfnationality.getText().trim();
        String flightname = labelfname.getText().trim();
        String flightcode = labelfcode.getText().trim();
        String src = source.getSelectedItem();
        String des = destination.getSelectedItem();
        String ddate = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText().trim();

        // 🔍 Validation - Check if any field is empty
        if (aadhar.isEmpty() || name.isEmpty() || nationality.isEmpty() ||
                flightname.isEmpty() || flightcode.isEmpty() || src.equals("") ||
                des.equals("") || ddate.isEmpty()) {

            // ⚠️ Show Warning if Fields are Incomplete
            JOptionPane.showMessageDialog(null, "❗ Please fill all the fields before booking.");
            return; // Stop execution if fields are empty
        }

        // 🎉 Proceed with Booking if Validation Passes
        try {
            Random random = new Random();
            Conn conn = new Conn();
            String query = "INSERT INTO reservation VALUES('PNR-" + random.nextInt(1000000) +
                    "', 'TIC-" + random.nextInt(10000) + "', '" + aadhar + "', '" + name +
                    "', '" + nationality + "', '" + flightname + "', '" + flightcode +
                    "', '" + src + "', '" + des + "', '" + ddate + "')";

            conn.s.executeUpdate(query);

            // ✅ Success Message
            JOptionPane.showMessageDialog(null, "✅ Flight Booked Successfully!");
            setVisible(false); // Close the booking window after success
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Error in Booking Flight. Please Try Again!");
        }
    }

    // Main Method
    public static void main(String[] args) {
        new BookFlight();
    }
}
