package net.jonasw.informatik;

import java.awt.Color;

import javax.swing.JOptionPane;

public class Statics {
    public static Color background = new Color(35, 35, 40);
    public static Color foreground = Color.white;
    public static String lineSplitter = "_//|//_";// Damit der Server weiß was jetzt eine Nachricht ist und was ein
                                                  // "Command" ist

    /**
     * Funktion damit ich nicht immer den Joptionpane code schreiben muss
     * 
     * @param msg
     */
    public static void error(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
