import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    public LoginScreen() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        add(userLabel); add(userField);
        add(passLabel); add(passField);
        add(new JLabel()); add(loginButton);

        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (DatabaseManager.getHardcodedUser().authenticate(user, pass)) {
                dispose();
                new HomeScreen(DatabaseManager.getHardcodedUser()).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login.");
            }
        });
    }
}
