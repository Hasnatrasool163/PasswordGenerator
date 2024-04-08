
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.security.SecureRandom;
import java.awt.*;
import javax.swing.*;


public class Password_Generator_App extends JFrame {

    private JCheckBox lowerCaseCheckBox, upperCaseCheckBox, numbersCheckBox, specialCharsCheckBox, avoidAmbiguousCheckBox;
    private JSpinner lengthSpinner;
    private JPasswordField passwordTextField;
    private JButton generateButton, copyButton, clearButton, toggleVisibilityButton;
    private boolean isPasswordVisible = false;
    private SecureRandom random = new SecureRandom();

    public Password_Generator_App() {
        setTitle("Password Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 550); // Adjusted for additional button
        setLocationRelativeTo(null);
        initialize();
    }

    private void initialize() {

// heading
        JLabel headingLabel = new JLabel("Welcome to Password Generator App", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setForeground(new Color(63, 81, 181));
        // CheckBox initialization
        lowerCaseCheckBox = new JCheckBox("Include LowerCase");
        upperCaseCheckBox = new JCheckBox("Include UpperCase");
        numbersCheckBox = new JCheckBox("Include Numbers");
        specialCharsCheckBox = new JCheckBox("Include Special Characters");
        avoidAmbiguousCheckBox = new JCheckBox("Avoid Ambiguous Characters");

        setupCheckBox(lowerCaseCheckBox);
        setupCheckBox(upperCaseCheckBox);
        setupCheckBox(numbersCheckBox);
        setupCheckBox(specialCharsCheckBox);
        setupCheckBox(avoidAmbiguousCheckBox);

        lengthSpinner = new JSpinner(new SpinnerNumberModel(8, 4, 20, 1));

        passwordTextField = new JPasswordField(20);
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordTextField.setEditable(false);

        generateButton = new JButton("Generate Password");
        copyButton = new JButton("Copy to Clipboard");
        clearButton = new JButton("Clear");
        toggleVisibilityButton = new JButton("Show Password");

        setupButton(generateButton);
        setupButton(copyButton);
        setupButton(clearButton);
        setupButton(toggleVisibilityButton);

        JLabel BottomLabel = new JLabel("Muhammad Hasnat Rasool", JLabel.CENTER);
        BottomLabel.setFont(new Font("Arial", Font.BOLD, 20));
        BottomLabel.setForeground(Color.GREEN);

        generateButton.addActionListener(e -> generatePassword());
        copyButton.addActionListener(e -> copyToClipboard(new String(passwordTextField.getPassword())));
        clearButton.addActionListener(e -> passwordTextField.setText(""));
        toggleVisibilityButton.addActionListener(e -> togglePasswordVisibility());

        JPanel mainPanel = new JPanel(new GridLayout(12, 1, 10, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        mainPanel.setBackground(Color.white);
        mainPanel.add(headingLabel); // Add the heading label to the main panel

        mainPanel.add(lowerCaseCheckBox);
        mainPanel.add(upperCaseCheckBox);
        mainPanel.add(numbersCheckBox);
        mainPanel.add(specialCharsCheckBox);
        mainPanel.add(avoidAmbiguousCheckBox);

        JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lengthPanel.setBackground(Color.white);
        lengthPanel.add(new JLabel("Password Length:"));
        lengthPanel.add(lengthSpinner);
        mainPanel.add(lengthPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.white);
        buttonPanel.add(generateButton);
        buttonPanel.add(copyButton);
        buttonPanel.add(clearButton);
        mainPanel.add(buttonPanel);

        JPanel toggleButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        toggleButtonPanel.setBackground(Color.white);
        toggleButtonPanel.add(toggleVisibilityButton);
        mainPanel.add(toggleButtonPanel);

        mainPanel.add(passwordTextField);

        getContentPane().setBackground(Color.white);
        mainPanel.add(BottomLabel);
        add(mainPanel);
    }

    private void setupCheckBox(JCheckBox checkBox) {
        checkBox.setFocusPainted(false);
        checkBox.setBorderPainted(false);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setupButton(JButton button) {
        button.setBackground(new Color(63, 81, 181));
        button.setForeground(Color.white);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private String generatePassword() {
        int passwordLength = (int) lengthSpinner.getValue();
        String lowerCase = "abcdefghijkmnopqrstuvwxyz";
        String upperCase = "ABCDEFGHJKLMNPQRSTUVWXYZ";
        String numbers = "23456789";
        String specialChars = "!@#$%&*+=?<>^";
        String ambiguousChars = "{}[]()/\\'\"~,;:.<>";
        String characters = "";

        if (lowerCaseCheckBox.isSelected()) characters += lowerCase;
        if (upperCaseCheckBox.isSelected()) characters += upperCase;
        if (numbersCheckBox.isSelected()) characters += numbers;
        if (specialCharsCheckBox.isSelected()) characters += specialChars;
        if (!avoidAmbiguousCheckBox.isSelected()) characters += ambiguousChars;

        if (characters.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Select at Least one Character type");
            return "";
        }

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            password.append(randomChar);
        }

        passwordTextField.setText(password.toString());
        return password.toString();
    }
    private void copyToClipboard(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        JOptionPane.showMessageDialog(this, "Password copied to clipboard!");
    }
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide the password
            passwordTextField.setEchoChar('●');
            toggleVisibilityButton.setText("Show Password");
            isPasswordVisible = false;
        } else {
            // Show the password
            passwordTextField.setEchoChar((char) 0);
            toggleVisibilityButton.setText("Hide Password");
            isPasswordVisible = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Password_Generator_App().setVisible(true));
    }
}
