package airlinemanagementsystem;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.Date;
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
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(25, 118, 210);
                Color color2 = new Color(58, 175, 169);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        setContentPane(gradientPanel);
        gradientPanel.setLayout(null);

        JLabel heading = new JLabel("✈️ Book Your Flight ✈️");
        heading.setBounds(400, 20, 500, 40);
        heading.setFont(new Font("Serif", Font.BOLD, 32));
        heading.setForeground(Color.WHITE);
        add(heading);

        createLabel("Aadhar", 60, 80);
        tfaadhar = createTextField(220, 80);

        fetchButton = createAnimatedButton("Fetch User", 380, 80, 120, 30);
        fetchButton.addActionListener(this);
        add(fetchButton);

        createLabel("Name", 60, 130);
        tfname = createValueLabel(220, 130);

        createLabel("Nationality", 60, 180);
        tfnationality = createValueLabel(220, 180);

        createLabel("Address", 60, 230);
        tfaddress = createValueLabel(220, 230);

        createLabel("Gender", 60, 280);
        labelgender = createValueLabel(220, 280);

        createLabel("Source", 60, 330);
        source = new Choice();
        source.setBounds(220, 330, 150, 25);
        add(source);

        createLabel("Destination", 60, 380);
        destination = new Choice();
        destination.setBounds(220, 380, 150, 25);
        add(destination);

        loadFlightData();

        flight = createAnimatedButton("Fetch Flights", 380, 380, 120, 30);
        flight.addActionListener(this);
        add(flight);

        createLabel("Flight Name", 60, 430);
        labelfname = createValueLabel(220, 430);

        createLabel("Flight Code", 60, 480);
        labelfcode = createValueLabel(220, 480);

        createLabel("Date of Travel", 60, 530);
        dcdate = new JDateChooser();
        dcdate.setBounds(220, 530, 150, 25);
        dcdate.setMinSelectableDate(new Date()); // ✅ Prevent selecting past dates
        add(dcdate);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/details.jpg"));
        Image i2 = i1.getImage().getScaledInstance(450, 320, Image.SCALE_SMOOTH);
        lblimage = new JLabel(new ImageIcon(i2));
        lblimage.setBounds(550, 80, 500, 410);
        add(lblimage);

        addImageZoomEffect(lblimage);

        bookflight = createAnimatedButton("Book Flight", 220, 580, 150, 35);
        bookflight.addActionListener(this);
        add(bookflight);

        setSize(1100, 700);
        setLocation(200, 50);
        setVisible(true);
    }

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

    private void createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 25);
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        add(label);
    }

    private JLabel createValueLabel(int x, int y) {
        JLabel label = new JLabel();
        label.setBounds(x, y, 150, 25);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        add(label);
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 150, 25);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
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

    private JButton createAnimatedButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setBackground(new Color(58, 175, 169));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(25, 118, 210));
                button.setBounds(x - 2, y - 2, width + 4, height + 4);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(58, 175, 169));
                button.setBounds(x, y, width, height);
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

    private void addImageZoomEffect(JLabel lblimage) {
        lblimage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblimage.setBounds(545, 75, 510, 420);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblimage.setBounds(550, 80, 500, 410);
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            fetchUserDetails();
        } else if (ae.getSource() == flight) {
            fetchFlightDetails();
        } else if (ae.getSource() == bookflight) {
            bookFlightTicket();
        }
    }

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

    private void bookFlightTicket() {
        String aadhar = tfaadhar.getText().trim();
        String name = tfname.getText().trim();
        String nationality = tfnationality.getText().trim();
        String flightname = labelfname.getText().trim();
        String flightcode = labelfcode.getText().trim();
        String src = source.getSelectedItem();
        String des = destination.getSelectedItem();
        String ddate = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText().trim();

        if (aadhar.isEmpty() || name.isEmpty() || nationality.isEmpty() ||
                flightname.isEmpty() || flightcode.isEmpty() || src.equals("") ||
                des.equals("") || ddate.isEmpty()) {
            JOptionPane.showMessageDialog(null, "❗ Please fill all the fields before booking.");
            return;
        }

        try {
            Random random = new Random();
            Conn conn = new Conn();
            String query = "INSERT INTO reservation VALUES('PNR-" + random.nextInt(1000000) +
                    "', 'TIC-" + random.nextInt(10000) + "', '" + aadhar + "', '" + name +
                    "', '" + nationality + "', '" + flightname + "', '" + flightcode +
                    "', '" + src + "', '" + des + "', '" + ddate + "')";
            conn.s.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "✅ Flight Booked Successfully!");
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Error in Booking Flight. Please Try Again!");
        }
    }

    public static void main(String[] args) {
        new BookFlight();
    }
}
