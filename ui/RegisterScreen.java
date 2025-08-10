package ui;

import util.UserStore;
import model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JLabel message;

    public RegisterScreen() {
        setTitle("Register - Driving Permit Test");
        setSize(360, 260);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 8, 8));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Confirm Password:"));
        confirmField = new JPasswordField();
        add(confirmField);

        message = new JLabel("", SwingConstants.CENTER);
        add(message);

        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(e -> register());
        add(registerBtn);
    }

    private void register() {
        String u = usernameField.getText().trim();
        String p = new String(passwordField.getPassword());
        String c = new String(confirmField.getPassword());

        if (u.isEmpty() || p.isEmpty() || c.isEmpty()) {
            message.setText("All fields are required.");
            return;
        }
        if (!p.equals(c)) {
            message.setText("Passwords do not match.");
            return;
        }
        if (UserStore.userExists(u)) {
            message.setText("Username already exists.");
            return;
        }

        User newUser = new User(u, p);
        UserStore.saveUser(newUser);
        JOptionPane.showMessageDialog(this, "Registration successful. You can log in now.");
        dispose();
    }
}
