package airlinemanagementsystem;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.ResultSet;
import javax.sound.sampled.*;
import javax.swing.*;

public class BoardingPass extends JFrame implements ActionListener {

    JTextField tfpnr;
    JLabel tfname, tfnationality, lblsrc, lbldest, labelfname, labelfcode, labeldate;
    JButton fetchButton, closeButton; // Added close button

    public BoardingPass() {
        // Frame settings
        setTitle("Air India - Boarding Pass");
        setSize(1000, 450);
        setLocation(300, 150);
        setUndecorated(true);
        setLayout(null);

        // Gradient background panel
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(58, 175, 169); // Teal
                Color color2 = new Color(255, 209, 102); // Orange
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setBounds(0, 0, 1000, 450);
        gradientPanel.setLayout(null);
        add(gradientPanel);

        // Close Button
        closeButton = new JButton("X");
        closeButton.setBounds(960, 10, 30, 30);
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.RED);
        closeButton.setFocusPainted(false);
        gradientPanel.add(closeButton);

        closeButton.addActionListener(e -> {
            playSound("close.wav");
            dispose(); // Close the frame
        });

        // Main heading
        JLabel heading = new JLabel("✈️ AIR INDIA ✈️");
        heading.setBounds(380, 10, 450, 35);
        heading.setFont(new Font("Serif", Font.BOLD, 32));
        heading.setForeground(Color.WHITE);
        gradientPanel.add(heading);

        // Subheading
        JLabel subheading = new JLabel("Boarding Pass");
        subheading.setBounds(360, 50, 300, 30);
        subheading.setFont(new Font("Tahoma", Font.BOLD, 24));
        subheading.setForeground(Color.BLUE);
        gradientPanel.add(subheading);

        // PNR Details
        JLabel lblaadhar = new JLabel("PNR DETAILS");
        lblaadhar.setBounds(60, 100, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblaadhar.setForeground(Color.DARK_GRAY);
        gradientPanel.add(lblaadhar);

        tfpnr = new JTextField();
        tfpnr.setBounds(220, 100, 150, 25);
        gradientPanel.add(tfpnr);

        // Fetch Button
        fetchButton = new JButton("Fetch Details");
        fetchButton.setBounds(380, 100, 120, 25);
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setFocusPainted(false);
        gradientPanel.add(fetchButton);

        // Hover effect for button
        fetchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fetchButton.setBackground(new Color(58, 175, 169)); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fetchButton.setBackground(Color.BLACK);
            }
        });

        fetchButton.addActionListener(this);

        // Labels and placeholders for fetched details
        createLabel("NAME", 60, 140, gradientPanel);
        tfname = createValueLabel(220, 140, gradientPanel);

        createLabel("NATIONALITY", 60, 180, gradientPanel);
        tfnationality = createValueLabel(220, 180, gradientPanel);

        createLabel("SRC", 60, 220, gradientPanel);
        lblsrc = createValueLabel(220, 220, gradientPanel);

        createLabel("DEST", 380, 220, gradientPanel);
        lbldest = createValueLabel(540, 220, gradientPanel);

        createLabel("Flight Name", 60, 260, gradientPanel);
        labelfname = createValueLabel(220, 260, gradientPanel);

        createLabel("Flight Code", 380, 260, gradientPanel);
        labelfcode = createValueLabel(540, 260, gradientPanel);

        createLabel("Date", 60, 300, gradientPanel);
        labeldate = createValueLabel(220, 300, gradientPanel);

        // Airline logo
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/airindia.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 230, Image.SCALE_SMOOTH);
        JLabel lblimage = new JLabel(new ImageIcon(i2));
        lblimage.setBounds(650, 100, 300, 230);
        gradientPanel.add(lblimage);

        setVisible(true);
    }

    // Method to create labels with fixed styles
    private JLabel createLabel(String text, int x, int y, JPanel panel) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 25);
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setForeground(Color.DARK_GRAY);
        panel.add(label);
        return label;
    }

    // Method to create value labels
    private JLabel createValueLabel(int x, int y, JPanel panel) {
        JLabel label = new JLabel();
        label.setBounds(x, y, 150, 25);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setForeground(Color.BLUE);
        panel.add(label);
        return label;
    }

    // Method to Play Sound Effect with Correct Path
    private void playSound(String soundFile) {
        try {
            // Correct path for sound file
            File soundPath = new File("src/airlinemanagementsystem/sounds/" + soundFile);

            // Check if file exists
            if (soundPath.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                // Optional: Set volume
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-5.0f);

                clip.start(); // Play sound
            } else {
                System.out.println("⚠️ Sound file not found: " + soundFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ActionListener for button
public void actionPerformed(ActionEvent ae) {
    String pnr = tfpnr.getText();

    try {
        Conn conn = new Conn();
        String query = "SELECT * FROM reservation WHERE PNR = '" + pnr + "'";
        ResultSet rs = conn.s.executeQuery(query);

        if (rs.next()) {
            tfname.setText(rs.getString("name"));
            tfnationality.setText(rs.getString("nationality"));
            lblsrc.setText(rs.getString("src"));
            lbldest.setText(rs.getString("des"));
            labelfname.setText(rs.getString("flightname"));
            labelfcode.setText(rs.getString("flightcode"));
            labeldate.setText(rs.getString("ddate"));

            // Assuming you generate a boarding pass here (PDF, image, etc.)
            String boardingPassPath = "path/to/generated/boarding-pass.pdf";  // Replace with actual path
            String recipientEmail = "recipient-email@example.com";  // Replace with the recipient's email

            // Send the email with the boarding pass attached
            EmailSender.sendEmail(recipientEmail, "Your Boarding Pass", "Please find your boarding pass attached.", boardingPassPath);

            // Play success sound
            playSound("success.wav");
            JOptionPane.showMessageDialog(null, "✅ PNR Found! Boarding Pass Generated and Sent via Email.");
        } else {
            // Play error sound
            playSound("error.wav");
            JOptionPane.showMessageDialog(null, "❌ Invalid PNR. Please enter correct details.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public static void main(String[] args) {
        new BoardingPass();
    }
}
