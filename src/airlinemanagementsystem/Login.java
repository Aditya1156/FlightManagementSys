package airlinemanagementsystem;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.ResultSet;
import javax.sound.sampled.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {

    // Define buttons and fields
    JButton submit, reset, close;
    JTextField tfusername;
    JPasswordField tfpassword;

    public Login() {
        // Set modern background color with gradient
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Gradient panel to make background attractive
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(50, 130, 184); // Blue
                Color color2 = new Color(72, 201, 176); // Green
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setBounds(0, 0, 400, 250);
        gradientPanel.setLayout(null);
        add(gradientPanel);

        // Title Label
        JLabel title = new JLabel("✈️ AIR INDIA LOGIN ✈️");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(90, 10, 300, 30);
        gradientPanel.add(title);

        // Username Label
        JLabel lblusername = new JLabel("Username:");
        lblusername.setFont(new Font("Arial", Font.PLAIN, 16));
        lblusername.setForeground(Color.WHITE);
        lblusername.setBounds(30, 60, 100, 25);
        gradientPanel.add(lblusername);

        // Username Field
        tfusername = new JTextField();
        tfusername.setBounds(140, 60, 200, 25);
        tfusername.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tfusername.setBackground(Color.WHITE);
        gradientPanel.add(tfusername);

        // Password Label
        JLabel lblpassword = new JLabel("Password:");
        lblpassword.setFont(new Font("Arial", Font.PLAIN, 16));
        lblpassword.setForeground(Color.WHITE);
        lblpassword.setBounds(30, 100, 100, 25);
        gradientPanel.add(lblpassword);

        // Password Field
        tfpassword = new JPasswordField();
        tfpassword.setBounds(140, 100, 200, 25);
        tfpassword.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tfpassword.setBackground(Color.WHITE);
        gradientPanel.add(tfpassword);

        // Submit Button
        submit = createButton("Login", 40, 150);
        gradientPanel.add(submit);

        // Reset Button
        reset = createButton("Reset", 160, 150);
        gradientPanel.add(reset);

        // Close Button
        close = createButton("Close", 280, 150);
        gradientPanel.add(close);

        // Add KeyListener for Enter key
        tfusername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submit.doClick(); // Simulate button click
                }
            }
        });

        tfpassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submit.doClick(); // Simulate button click
                }
            }
        });

        // Frame settings
        setSize(400, 250);
        setLocation(600, 250);
        setUndecorated(true); // Remove window border for modern look
        setVisible(true);
    }

    // Create button with hover and animation effects
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 100, 30);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 87, 34)); // Default orange
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 176, 255)); // Blue on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 87, 34)); // Back to orange
            }
        });

        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        playSound("src/airlinemanagementsystem/sounds/click.wav"); // Corrected path for sounds

        if (ae.getSource() == submit) {
            String username = tfusername.getText();
            String password = String.valueOf(tfpassword.getPassword());

            try {
                Conn c = new Conn();
                String query = "SELECT * FROM login WHERE username = '" + username + "' AND password = '" + password + "'";
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "✅ Login Successful! ✈️");
                    new Home();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Invalid Username or Password!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == reset) {
            tfusername.setText("");
            tfpassword.setText("");
        } else if (ae.getSource() == close) {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    // Play sound effect when button is clicked
    private void playSound(String filePath) {
        new Thread(() -> {
            try {
                File soundFile = new File(filePath);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                // Optional: Reduce sound volume
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-5.0f); // Reduce volume by 5 decibels

                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        new Login();
    }
}
