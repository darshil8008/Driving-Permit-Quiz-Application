package ui;

import model.User;
import util.UserStore;
import util.Session;
import util.UIStyle;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        UIStyle.apply(this, "Driving Permit — Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 360);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        JLabel title = UIStyle.heading("Welcome to Driving Permit Practice");
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

        JLabel userLabel = new JLabel("Username");
        JTextField userField = new JTextField(20);

        JLabel passLabel = new JLabel("Password");
        JPasswordField passField = new JPasswordField(20);

        JButton loginButton = UIStyle.primaryButton("Login");
        JButton registerButton = UIStyle.secondaryButton("Register");

        card.add(userLabel, c);
        c.gridx = 1;
        card.add(userField, c);

        c.gridx = 0; c.gridy++;
        card.add(passLabel, c);
        c.gridx = 1;
        card.add(passField, c);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        actions.add(registerButton);
        actions.add(loginButton);

        c.gridx = 0; c.gridy++;
        c.gridwidth = 2;
        card.add(actions, c);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String u = userField.getText().trim();
                String p = new String(passField.getPassword());
                if (u.isEmpty() || p.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginScreen.this, "Please enter username and password.");
                    return;
                }
                if (UserStore.authenticate(u, p)) {
                    Session.setCurrentUser(new User(u, p));
                    dispose();
                    new HomeScreen().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginScreen.this, "Invalid username or password.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterScreen().setVisible(true);
            }
        });
    }
}
