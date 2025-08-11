package util;

import model.User;
import java.io.*;

public class UserStore {
    private static final String USER_FILE = "users.txt";

    private static void ensureFile() {
        try {
            File f = new File(USER_FILE);
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean userExists(String username) {
        ensureFile();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(USER_FILE));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 2 && parts[0].equals(username)) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { if (br != null) br.close(); } catch (IOException ignored) {}
        }
        return false;
    }

    public static void saveUser(User user) {
        ensureFile();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(USER_FILE, true));
            bw.write(user.getUsername() + "," + user.getPassword());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { if (bw != null) bw.close(); } catch (IOException ignored) {}
        }
    }

    public static User findUser(String username) {
        ensureFile();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(USER_FILE));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 2 && parts[0].equals(username)) return new User(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { if (br != null) br.close(); } catch (IOException ignored) {}
        }
        return null;
    }

    public static boolean authenticate(String username, String password) {
        User u = findUser(username);
        return u != null && u.authenticate(username, password);
    }
}
