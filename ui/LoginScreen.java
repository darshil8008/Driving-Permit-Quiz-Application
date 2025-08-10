package ui;

import model.User;
import util.UserStore;
import util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        setTitle("Login");
        setSize(380, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 8, 8));

        JLabel userLabel = new JLabel("Username:");
        final JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        final JPasswordField passField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(loginButton);
        add(registerButton);

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
