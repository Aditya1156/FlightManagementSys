package airlinemanagementsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.sql.*;

public class AddCustomer extends JFrame implements ActionListener {

    JTextField tfname, tfphone, tfaadhar, tfnationality, tfaddress;
    JRadioButton rbmale, rbfemale;
    JButton save;
    Timer fadeInTimer;
    float opacity = 0.0f;

    public AddCustomer() {
        // Create gradient background
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(72, 201, 176); // Light Green
                Color color2 = new Color(50, 130, 184); // Sky Blue
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setLayout(null);
        setContentPane(gradientPanel);

        // Heading Label
        JLabel heading = new JLabel("✈️ ADD CUSTOMER DETAILS ✈️");
        heading.setBounds(230, 20, 500, 35);
        heading.setFont(new Font("Serif", Font.BOLD, 32));
        heading.setForeground(Color.WHITE);
        gradientPanel.add(heading);

        // Add Form Labels & Text Fields
        addLabel("Name", 60, 80, gradientPanel);
        tfname = createAnimatedTextField(220, 80, gradientPanel);

        addLabel("Nationality", 60, 130, gradientPanel);
        tfnationality = createAnimatedTextField(220, 130, gradientPanel);

        addLabel("Aadhar Number", 60, 180, gradientPanel);
        tfaadhar = createAnimatedTextField(220, 180, gradientPanel);

        addLabel("Address", 60, 230, gradientPanel);
        tfaddress = createAnimatedTextField(220, 230, gradientPanel);

        addLabel("Gender", 60, 280, gradientPanel);
        rbmale = createAnimatedRadioButton("Male", 220, 280, gradientPanel);
        rbfemale = createAnimatedRadioButton("Female", 300, 280, gradientPanel);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rbmale);
        genderGroup.add(rbfemale);

        addLabel("Phone", 60, 330, gradientPanel);
        tfphone = createAnimatedTextField(220, 330, gradientPanel);

        // Save Button with animation
        save = createAnimatedButton("SAVE", 220, 380, gradientPanel);

        // Add Customer Image
        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/emp.png"));
        JLabel lblimage = new JLabel(image);
        lblimage.setBounds(500, 80, 280, 400);
        gradientPanel.add(lblimage);

        // Frame settings
        setSize(900, 600);
        setLocation(300, 150);
        setUndecorated(false);
        setVisible(true);

        // Fade-in animation
        startFadeInAnimation(gradientPanel);
    }

    // Create and add label
    private void addLabel(String text, int x, int y, JPanel panel) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, 150, 25);
        panel.add(label);
    }

    // Create text field with focus animation
    private JTextField createAnimatedTextField(int x, int y, JPanel panel) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 200, 30);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        Border defaultBorder = new LineBorder(Color.GRAY, 1);
        Border focusedBorder = new LineBorder(new Color(0, 176, 255), 2);

        textField.setBorder(defaultBorder);

        // Focus animation with border and shadow
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(focusedBorder);
                textField.setBackground(new Color(230, 247, 255)); // Light blue on focus
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(defaultBorder);
                textField.setBackground(Color.WHITE);
            }
        });

        panel.add(textField);
        return textField;
    }

    // Create radio button with transparent background and consistent color
    private JRadioButton createAnimatedRadioButton(String text, int x, int y, JPanel panel) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setBounds(x, y, 80, 25);
        radioButton.setOpaque(false); // Transparent background
        radioButton.setForeground(Color.WHITE); // Text color remains white
        radioButton.setFocusPainted(false);

        // Set selected color or keep background transparent
        radioButton.addItemListener(e -> {
            if (radioButton.isSelected()) {
                radioButton.setForeground(new Color(0, 255, 127)); // Light Green on selection
            } else {
                radioButton.setForeground(Color.WHITE); // Back to white on unselect
            }
        });

        panel.add(radioButton);
        return radioButton;
    }

    // Create animated button with ripple and hover effects
    private JButton createAnimatedButton(String text, int x, int y, JPanel panel) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 150, 35);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(255, 87, 34)); // Orange
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setUI(new BasicButtonUI());

        // Add hover and click animation
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 176, 255)); // Blue on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 87, 34)); // Back to orange
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBounds(x + 2, y + 2, 146, 33); // Button press animation
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBounds(x, y, 150, 35);
            }
        });

        button.addActionListener(this);
        panel.add(button);
        return button;
    }

    // Fade-in animation for UI
    private void startFadeInAnimation(JPanel panel) {
        fadeInTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    fadeInTimer.stop();
                }
                panel.repaint();
            }
        });
        fadeInTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == save) {
            String name = tfname.getText();
            String nationality = tfnationality.getText();
            String phone = tfphone.getText();
            String address = tfaddress.getText();
            String aadhar = tfaadhar.getText();
            String gender = rbmale.isSelected() ? "Male" : "Female";

            // Validate fields
            if (name.isEmpty() || nationality.isEmpty() || phone.isEmpty() || address.isEmpty() || aadhar.isEmpty()) {
                JOptionPane.showMessageDialog(null, "❗ All fields are required.");
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "INSERT INTO passenger (name, nationality, phone, address, aadhar, gender) VALUES ('"
                        + name + "', '" + nationality + "', '" + phone + "', '" + address + "', '" + aadhar + "', '" + gender + "')";
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "✅ Customer Details Added Successfully!");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new AddCustomer();
    }
}
