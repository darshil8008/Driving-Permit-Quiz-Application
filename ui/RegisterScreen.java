package ui;

import util.UserStore;
import model.User;
import util.UIStyle;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JLabel message;

    public RegisterScreen() {
        UIStyle.apply(this, "Driving Permit — Register");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(520, 380);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        JLabel title = UIStyle.heading("Create an account");
        title.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
        add(title, BorderLayout.NORTH);

        JPanel card = UIStyle.card(20);
        card.setLayout(new GridBagLayout());
        add(card, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0;

        JLabel userL = new JLabel("Username");
        usernameField = new JTextField(22);

        JLabel passL = new JLabel("Password");
        passwordField = new JPasswordField(22);

        JLabel confL = new JLabel("Confirm Password");
        confirmField = new JPasswordField(22);

        message = new JLabel("");
        message.setForeground(new java.awt.Color(180, 0, 0));

        JButton registerBtn = UIStyle.primaryButton("Register");

        card.add(userL, c);
        c.gridx = 1;
        card.add(usernameField, c);

        c.gridx = 0; c.gridy++;
        card.add(passL, c);
        c.gridx = 1;
        card.add(passwordField, c);

        c.gridx = 0; c.gridy++;
        card.add(confL, c);
        c.gridx = 1;
        card.add(confirmField, c);

        c.gridx = 0; c.gridy++;
        c.gridwidth = 2;
        card.add(message, c);

        c.gridy++;
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        actions.add(registerBtn);
        card.add(actions, c);

        registerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
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
