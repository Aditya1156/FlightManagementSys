package airlinemanagementsystem;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

public class Home extends JFrame implements ActionListener {

    JButton flightDetails, addCustomer, bookFlight, journeyDetails, cancelTicket, passengerHistory, boardingPass, exitButton, registeredPassenger;
    JPanel boxPanel, appBar;
    JTextField searchField;
    JButton searchButton;
    JLabel backgroundLabel;
    JLayeredPane layeredPane;

    public Home() {
        setLayout(null);
        layeredPane = getLayeredPane();

        loadBackgroundImage();
        addAppBar();
        addHeadingWithShadow();
        addBoxPanel();
        addAirlineLogo();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadBackgroundImage() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/front.png"));
        backgroundLabel = new JLabel(i1);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Image scaledImage = i1.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                backgroundLabel.setIcon(new ImageIcon(scaledImage));
                backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
            }
        });
    }

    private void addHeadingWithShadow() {
        JLabel heading = new JLabel("✈️ AIR INDIA WELCOMES YOU ✈️");
        heading.setBounds(450, 100, 1000, 50);
        heading.setForeground(new Color(255, 215, 0));
        heading.setFont(new Font("Serif", Font.BOLD, 40));
        layeredPane.add(heading, JLayeredPane.PALETTE_LAYER);

        JLabel shadow = new JLabel("✈️ AIR INDIA WELCOMES YOU ✈️");
        shadow.setBounds(452, 102, 1000, 50);
        shadow.setForeground(new Color(0, 0, 0, 120));
        shadow.setFont(new Font("Serif", Font.BOLD, 40));
        layeredPane.add(shadow, JLayeredPane.PALETTE_LAYER);
    }

    private void addBoxPanel() {
        boxPanel = new JPanel(new GridLayout(3, 3, 20, 20));
        boxPanel.setBounds(350, 200, 900, 500);
        boxPanel.setOpaque(false);
        layeredPane.add(boxPanel, JLayeredPane.PALETTE_LAYER);

        flightDetails = createBoxButton("Flight Details", "airplane.png", "📊 View and manage flight details.");
        addCustomer = createBoxButton("Add Customer", "user.png", "👤 Add new customer details.");
        bookFlight = createBoxButton("Book Flight", "booking.png", "🛫 Book flight tickets easily.");
        journeyDetails = createBoxButton("Journey Details", "journey.png", "📅 View journey details.");
        cancelTicket = createBoxButton("Cancel Ticket", "cancel.png", "❌ Cancel booked tickets.");
        passengerHistory = createBoxButton("Passenger History", "history.png", "📚 View passenger history.");
        boardingPass = createBoxButton("Boarding Pass", "boarding.png", "🎫 Generate boarding pass.");
        exitButton = createBoxButton("Exit", "exit.png", "❌ Exit the application safely.");
        registeredPassenger = createBoxButton("Registered Passenger", "passenger.png", "👥 View all registered passengers.");

        boxPanel.add(flightDetails);
        boxPanel.add(addCustomer);
        boxPanel.add(bookFlight);
        boxPanel.add(journeyDetails);
        boxPanel.add(cancelTicket);
        boxPanel.add(passengerHistory);
        boxPanel.add(boardingPass);
        boxPanel.add(registeredPassenger);
        boxPanel.add(exitButton);
    }

    private void addAirlineLogo() {
        URL logoUrl = ClassLoader.getSystemResource("airlinemanagementsystem/icons/logo.png");
        if (logoUrl != null) {
            ImageIcon logoIcon = new ImageIcon(logoUrl);
            Image logoImage = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel logo = new JLabel(new ImageIcon(logoImage));
            logo.setBounds(20, getHeight() - 150, 120, 120);
            layeredPane.add(logo, JLayeredPane.PALETTE_LAYER);
        } else {
            System.err.println("⚠️ Logo image not found!");
        }
    }

    private JButton createBoxButton(String name, String iconName, String proTip) {
        URL resource = ClassLoader.getSystemResource("airlinemanagementsystem/icons/" + iconName);
        JButton button;

        if (resource == null) {
            System.err.println("⚠️ Icon not found: " + iconName);
            button = new JButton(name);
        } else {
            ImageIcon icon = new ImageIcon(resource);
            Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            button = new JButton(name, new ImageIcon(img));
        }

        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        button.setToolTipText(proTip);
        button.addActionListener(this);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 140, 0));
                button.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
                playSound("hover.wav");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            }
        });

        return button;
    }

    private void addAppBar() {
        appBar = new JPanel(null);
        appBar.setBackground(new Color(0, 51, 102));
        appBar.setBounds(0, 0, getWidth(), 70);
        layeredPane.add(appBar, JLayeredPane.MODAL_LAYER);
    }

    private void playSound(String soundName) {
        try {
            File soundFile = new File("src/airlinemanagementsystem/sounds/" + soundName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            System.err.println("⚠️ Sound error: " + soundName);
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String text = ae.getActionCommand();
        playSound("click.wav");

        switch (text) {
            case "Flight Details" -> new FlightInfo();
            case "Add Customer" -> new AddCustomer();
            case "Book Flight" -> new BookFlight();
            case "Journey Details" -> new JourneyDetails();
            case "Cancel Ticket" -> new Cancel();
            case "Passenger History" -> new PassengerHistory();
            case "Boarding Pass" -> new BoardingPass();
            case "Registered Passenger" -> new RegisteredPassenger();
            case "Exit" -> handleExit();
        }
    }

    private void handleExit() {
        playSound("exit.wav");
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}
