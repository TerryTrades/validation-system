import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.regex.Pattern;

/**
 * Data Validation System
 * Advanced Programming II - Java Swing
 *
 * A simple desktop form that validates personal information before saving it.
 */
public class DataValidationSystem extends JFrame {

    private final JTextField nameField = new JTextField();
    private final JTextField studentNumberField = new JTextField();
    private final JTextField dobField = new JTextField();
    private final JTextField ageField = new JTextField();
    private final JTextField contactField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JRadioButton maleButton = new JRadioButton("Male");
    private final JRadioButton femaleButton = new JRadioButton("Female");
    private final JRadioButton otherButton = new JRadioButton("Other");

    private final JLabel nameStatus = statusLabel();
    private final JLabel studentStatus = statusLabel();
    private final JLabel dobStatus = statusLabel();
    private final JLabel ageStatus = statusLabel();
    private final JLabel genderStatus = statusLabel();
    private final JLabel contactStatus = statusLabel();
    private final JLabel emailStatus = statusLabel();

    private final Color BACKGROUND = new Color(245, 248, 252);
    private final Color NAVY = new Color(27, 44, 68);
    private final Color BLUE = new Color(55, 112, 190);
    private final Color SUCCESS = new Color(35, 130, 75);
    private final Color ERROR = new Color(190, 55, 55);

    public DataValidationSystem() {
        setTitle("Personal Data Validation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(780, 720);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);
        root.setBorder(new EmptyBorder(22, 28, 22, 28));

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createForm(), BorderLayout.CENTER);
        root.add(createButtons(), BorderLayout.SOUTH);

        setContentPane(root);
        setResizable(false);
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel title = new JLabel("Personal Data Validation System");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(NAVY);

        JLabel subtitle = new JLabel("Enter your details and check each field before saving.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(80, 95, 115));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(title);
        text.add(Box.createVerticalStrut(6));
        text.add(subtitle);

        panel.add(text, BorderLayout.WEST);
        panel.setBorder(new EmptyBorder(0, 0, 18, 0));
        return panel;
    }

    private JPanel createForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 226, 236)),
                new EmptyBorder(20, 22, 20, 22)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        addRowComponent(form, gbc, row++, "Name and Surname *", nameField, nameStatus);
        addRowComponent(form, gbc, row++, "Student Number *", studentNumberField, studentStatus);
        addRowComponent(form, gbc, row++, "Date of Birth (DD/MM/YYYY) *", dobField, dobStatus);
        addRowComponent(form, gbc, row++, "Age *", ageField, ageStatus);

        JLabel genderLabel = new JLabel("Gender *");
        genderLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderPanel.setOpaque(false);

        ButtonGroup group = new ButtonGroup();
        group.add(maleButton);
        group.add(femaleButton);
        group.add(otherButton);

        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        genderPanel.add(otherButton);

        addRowComponent(form, gbc, row++, genderLabel, genderPanel, genderStatus);

        addRowComponent(form, gbc, row++, "Contact Number *", contactField, contactStatus);
        addRowComponent(form, gbc, row++, "Email Address *", emailField, emailStatus);

        return form;
    }

    

    private void addRowComponent(JPanel panel, GridBagConstraints gbc, int row,
                        String labelText, JComponent input, JLabel status) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        addRowComponent(panel, gbc, row, label, input, status);
    }

    private void addRowComponent(JPanel panel, GridBagConstraints gbc, int row,
                        JLabel label, JComponent input, JLabel status) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        prepareInput(input);
        panel.add(input, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        status.setPreferredSize(new Dimension(250, 28));
        panel.add(status, gbc);
    }

    private void prepareInput(JComponent component) {
        if (component instanceof JTextField) {
            JTextField field = (JTextField) component;
            field.setFont(new Font("SansSerif", Font.PLAIN, 13));
            field.setPreferredSize(new Dimension(240, 32));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(190, 202, 218)),
                    new EmptyBorder(4, 8, 4, 8)));
        }
    }

    private JLabel statusLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        return label;
    }

    private JPanel createButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        panel.setOpaque(false);

        JButton save = new JButton("Save");
        JButton clear = new JButton("Clear");
        JButton exit = new JButton("Exit");

        styleButton(save, BLUE);
        styleButton(clear, new Color(110, 125, 145));
        styleButton(exit, new Color(85, 95, 110));

        save.addActionListener(e -> validateAll());
        clear.addActionListener(e -> clearForm());
        exit.addActionListener(e -> System.exit(0));

        panel.add(save);
        panel.add(clear);
        panel.add(exit);
        return panel;
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(9, 18, 9, 18));
    }

    private boolean validateAll() {
        boolean valid = true;

        valid &= validateName();
        valid &= validateStudentNumber();
        valid &= validateDateOfBirth();
        valid &= validateAge();
        valid &= validateGender();
        valid &= validateContact();
        valid &= validateEmail();

        if (valid) {
            JOptionPane.showMessageDialog(
                    this,
                    "All information is valid and ready to be saved.",
                    "Validation Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please correct the highlighted fields before saving.",
                    "Validation Required",
                    JOptionPane.WARNING_MESSAGE);
        }

        return valid;
    }

    private boolean validateName() {
        String value = nameField.getText().trim();
        if (value.isEmpty()) return fail(nameStatus, "Required: enter your name.");
        if (!value.matches("[A-Za-z ]+")) return fail(nameStatus, "Use letters and spaces only.");
        if (value.length() < 2 || value.length() > 60)
            return fail(nameStatus, "Name must be 2–60 characters.");
        return pass(nameStatus);
    }

    private boolean validateStudentNumber() {
        String value = studentNumberField.getText().trim();
        if (value.isEmpty()) return fail(studentStatus, "Required: enter your student number.");
        if (!value.matches("\\d+")) return fail(studentStatus, "Student number must contain digits only.");
        if (value.length() < 8 || value.length() > 15)
            return fail(studentStatus, "Student number must be 8–15 digits.");
        return pass(studentStatus);
    }

    private boolean validateDateOfBirth() {
        String value = dobField.getText().trim();
        if (value.isEmpty()) return fail(dobStatus, "Required: enter your date of birth.");
        if (!value.matches("\\d{2}/\\d{2}/\\d{4}"))
            return fail(dobStatus, "Use DD/MM/YYYY format.");
        String[] parts = value.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        if (month < 1 || month > 12 || day < 1 || day > 31 || year < 1900 || year > 2026)
            return fail(dobStatus, "Enter a realistic date.");
        return pass(dobStatus);
    }

    private boolean validateAge() {
        String value = ageField.getText().trim();
        if (value.isEmpty()) return fail(ageStatus, "Required: enter your age.");
        if (!value.matches("\\d+")) return fail(ageStatus, "Age must contain numbers only.");
        int age = Integer.parseInt(value);
        if (age < 16 || age > 100) return fail(ageStatus, "Age must be between 16 and 100.");
        return pass(ageStatus);
    }

    private boolean validateGender() {
        if (!maleButton.isSelected() && !femaleButton.isSelected() && !otherButton.isSelected())
            return fail(genderStatus, "Please select a gender.");
        return pass(genderStatus);
    }

    private boolean validateContact() {
        String value = contactField.getText().trim();
        if (value.isEmpty()) return fail(contactStatus, "Required: enter your contact number.");
        if (!value.matches("\\d+")) return fail(contactStatus, "Contact number must contain digits only.");
        if (value.length() < 10 || value.length() > 15)
            return fail(contactStatus, "Contact number must be 10–15 digits.");
        return pass(contactStatus);
    }

    private boolean validateEmail() {
        String value = emailField.getText().trim();
        if (value.isEmpty()) return fail(emailStatus, "Required: enter your email address.");
        String pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!Pattern.matches(pattern, value)) return fail(emailStatus, "Enter a valid email address.");
        if (value.length() > 100) return fail(emailStatus, "Email must be 100 characters or less.");
        return pass(emailStatus);
    }

    private boolean pass(JLabel label) {
        label.setForeground(SUCCESS);
        label.setText("✓ Valid");
        return true;
    }

    private boolean fail(JLabel label, String message) {
        label.setForeground(ERROR);
        label.setText("✗ " + message);
        return false;
    }

    private void clearForm() {
        nameField.setText("");
        studentNumberField.setText("");
        dobField.setText("");
        ageField.setText("");
        contactField.setText("");
        emailField.setText("");

        ButtonGroup group = new ButtonGroup();
        group.add(maleButton);
        group.add(femaleButton);
        group.add(otherButton);
        group.clearSelection();

        for (JLabel label : new JLabel[]{
                nameStatus, studentStatus, dobStatus, ageStatus,
                genderStatus, contactStatus, emailStatus}) {
            label.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DataValidationSystem().setVisible(true));
    }
}
