package airlinemanagementsystem;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.*;

public class Cancel extends JFrame implements ActionListener {
    JTextField tfpnr;
    JLabel tfname;
    JLabel cancellationno;
    JLabel lblfcode;
    JLabel lbldateoftravel;
    JButton fetchButton;
    JButton flight;

    public Cancel() {
        // Gradient Background Panel
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(85, 239, 196);
                Color color2 = new Color(129, 236, 236);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setBounds(0, 0, 800, 450);
        gradientPanel.setLayout(null);
        add(gradientPanel);

        Random random = new Random();
        
        // Heading with Zoom Animation
        JLabel heading = new JLabel("CANCELLATION");
        heading.setBounds(180, 20, 300, 35);
        heading.setFont(new Font("Serif", Font.BOLD, 32));
        heading.setForeground(Color.BLUE);
        gradientPanel.add(heading);

        // PNR Label
        JLabel lblaadhar = new JLabel("PNR Number");
        lblaadhar.setBounds(60, 80, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gradientPanel.add(lblaadhar);

        // PNR Input Field
        tfpnr = new JTextField();
        tfpnr.setBounds(220, 80, 150, 25);
        gradientPanel.add(tfpnr);

        // Fetch Button with Hover and Sound
        fetchButton = createAnimatedButton("Show Details");
        fetchButton.setBounds(380, 80, 120, 25);
        fetchButton.addActionListener(this);
        gradientPanel.add(fetchButton);

        // Name Label
        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 130, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gradientPanel.add(lblname);

        // Name Display
        tfname = new JLabel();
        tfname.setBounds(220, 130, 150, 25);
        gradientPanel.add(tfname);

        // Cancellation Number
        JLabel lblnationality = new JLabel("Cancellation No");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gradientPanel.add(lblnationality);

        cancellationno = new JLabel("" + random.nextInt(1000000));
        cancellationno.setBounds(220, 180, 150, 25);
        gradientPanel.add(cancellationno);

        // Flight Code
        JLabel lbladdress = new JLabel("Flight Code");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gradientPanel.add(lbladdress);

        lblfcode = new JLabel();
        lblfcode.setBounds(220, 230, 150, 25);
        gradientPanel.add(lblfcode);

        // Date of Travel
        JLabel lblgender = new JLabel("Date");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gradientPanel.add(lblgender);

        lbldateoftravel = new JLabel();
        lbldateoftravel.setBounds(220, 280, 150, 25);
        gradientPanel.add(lbldateoftravel);

        // Cancel Button with Sound and Animation
        flight = createAnimatedButton("Cancel");
        flight.setBounds(220, 330, 120, 25);
        flight.addActionListener(this);
        gradientPanel.add(flight);

        // Set Frame Properties
        setSize(800, 450);
        setLocation(350, 150);
        setUndecorated(false);
        setVisible(true);
    }

    // Handle Button Actions
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            playClickSound(); // Play click sound
            String pnr = tfpnr.getText();

            try {
                Conn conn = new Conn();
                String query = "SELECT * FROM reservation WHERE PNR = '" + pnr + "'";
                ResultSet rs = conn.s.executeQuery(query);
                if (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    lblfcode.setText(rs.getString("flightcode"));
                    lbldateoftravel.setText(rs.getString("ddate"));
                } else {
                    JOptionPane.showMessageDialog(null, "❗ Please enter correct PNR.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == flight) {
            playClickSound(); // Play click sound
            String name = tfname.getText();
            String pnr = tfpnr.getText();
            String cancelno = cancellationno.getText();
            String fcode = lblfcode.getText();
            String date = lbldateoftravel.getText();

            try {
                Conn conn = new Conn();
                String query = "INSERT INTO cancel VALUES('" + pnr + "', '" + name + "', '" + cancelno + "', '" + fcode + "', '" + date + "')";
                conn.s.executeUpdate(query);
                conn.s.executeUpdate("DELETE FROM reservation WHERE PNR = '" + pnr + "'");
                JOptionPane.showMessageDialog(null, "✅ Ticket Cancelled Successfully.");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Method to Create Animated Button with Hover and Zoom
    private JButton createAnimatedButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));

        // Hover Effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(34, 153, 84)); // Green Shade
                button.setFont(new Font("Arial", Font.BOLD, 16));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK);
                button.setFont(new Font("Arial", Font.BOLD, 14));
            }
        });
        return button;
    }

    // Play Click Sound Effect
    // Play Click Sound Effect
private void playClickSound() {
    try {
        // Correct path to sounds folder
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


    // Main Method
    public static void main(String[] args) {
        new Cancel();
    }
}
