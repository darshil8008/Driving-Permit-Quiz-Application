package util;

import model.User;
import java.io.*;
import java.util.*;

public class UserStore {

    private static final String USER_FILE = "users.txt";

    // I just want to make sure the file exists before we try to read/write
    private static void ensureFile() {
        try {
            File f = new File(USER_FILE);
            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // I have added below method to check if a username already exists in the file
    public static boolean userExists(String username) {
        ensureFile();
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 2 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // I have added below method to save a new user to the file (username,password)
    public static void saveUser(User user) {
        ensureFile();
        try (FileWriter fw = new FileWriter(USER_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(user.getUsername() + "," + user.getPassword());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // I have added below method to find a user by username
    public static User findUser(String username) {
        ensureFile();
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 2 && parts[0].equals(username)) {
                    return new User(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // I have added below method to  check if username and password match a stored user
    public static boolean authenticate(String username, String password) {
        User u = findUser(username);
        return u != null && u.authenticate(username, password);
    }
}
