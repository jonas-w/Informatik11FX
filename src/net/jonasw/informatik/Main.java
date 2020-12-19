package net.jonasw.informatik;

import java.awt.Panel;

import javax.swing.JFrame;
import net.jonasw.informatik.panels.*;
import javax.swing.UIManager;

public class Main extends JFrame {
    public net.jonasw.informatik.panels.Panel currentPanel;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");// die JComponents sehen dann bisschen
                                                                                  // besser aus
        } catch (Exception e) {
            // Wenn nich dann halt nich
        }
        new Main();
    }

    public Main() {
        setSize(600, 500);// Größe vom JFrame
        setTitle("Chat");
        setLocationRelativeTo(null);// Zentrieren
        setDefaultCloseOperation(3);// Soll Programm beenden wenn ich das X drücke
        currentPanel = new StartPanel(this);// ich habe verschiedene Panele die ich dann unten in der switchFrame
                                            // methode ändere
        getContentPane().add(currentPanel);// das Panel hinzufügen
        setVisible(true);// Ich will das JFrame auch sehen :)
    }

    // Dadurch wird das jetzige Panel entfernt und das neue hinzugefügt
    public void switchFrame(net.jonasw.informatik.panels.Panel p2) {
        this.remove(currentPanel);
        currentPanel = p2;
        this.getContentPane().add(currentPanel);
        System.out.println(p2.toString());
        this.repaint();
    }

}