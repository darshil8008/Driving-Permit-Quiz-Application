package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIStyle {
    public static final Color BG = new Color(245, 249, 252);
    public static final Color PANEL_BG = Color.WHITE;
    public static final Color ACCENT = new Color(52, 120, 246);
    public static final Color TEXT = new Color(34, 34, 34);

    public static Font h1() { return new Font("SansSerif", Font.BOLD, 20); }
    public static Font h2() { return new Font("SansSerif", Font.BOLD, 16); }
    public static Font body() { return new Font("SansSerif", Font.PLAIN, 14); }

    public static void apply(JFrame frame, String titleText) {
        frame.setTitle(titleText);
        frame.getContentPane().setBackground(BG);
        UIManager.put("Panel.background", PANEL_BG);
        UIManager.put("Button.font", body());
        UIManager.put("Label.font", body());
    }

    public static JPanel card(int padding) {
        JPanel p = new JPanel();
        p.setBackground(PANEL_BG);
        p.setBorder(new EmptyBorder(padding, padding, padding, padding));
        return p;
    }

    public static JLabel heading(String text) {
        JLabel l = new JLabel(text);
        l.setFont(h1());
        l.setForeground(TEXT);
        return l;
    }

    public static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(ACCENT);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(h2());
        b.setPreferredSize(new Dimension(160, 36));
        return b;
    }

    public static JButton secondaryButton(String text) {
        JButton b = new JButton(text);
        b.setFont(body());
        b.setPreferredSize(new Dimension(160, 36));
        return b;
    }
}
