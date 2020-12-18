package net.jonasw.informatik.panels;

import java.lang.reflect.Array;
import java.net.Socket;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import net.jonasw.informatik.Main;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import net.jonasw.informatik.panels.Panel;
import net.jonasw.informatik.panels.ChatPanel;
import net.jonasw.informatik.Statics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends net.jonasw.informatik.panels.Panel {
    public StartPanel(Main p) {
        super(p);// Generiert erstmal das, was in der Original Panel Datei drinne steht die oben
                 // extended wird

        JLabel jl = new JLabel("Chatserveradresse mit Port:");
        jl.setForeground(Statics.foreground);// Farbe wird zur in Statics festgelegten foreground color gesetzt
        JLabel jl2 = new JLabel("Gewünschter Nutzername:");
        jl2.setForeground(Statics.foreground);// S.O.

        JTextField serveraddr = new JTextField();// Textfeld für die Serveradresse
        JTextField username = new JTextField();// Textfeld für den Nutzernamen
        JButton connectButton = new JButton("Verbinden!");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (username.getText().length() < 3) {// Wenn nutzername kleiner als 3 Zeichen lang, dann Fehler
                    Statics.error("Nutzername muss mindestens 3 Zeichen lang sein");
                    return; // keinen weiteren code in diesem Block ausführen
                }
                if (serveraddr.getText().length() == 0 || !serveraddr.getText().contains(":")) {// wenn nichts
                                                                                                // eingegeben oder kein
                                                                                                // port vorhanden dann
                                                                                                // error
                    Statics.error("Bitte gib eine Serveradresse und einen Port ein");
                    return;
                }
                String[] addressparts = serveraddr.getText().split(":");// ip/domäne und port voneinander trennen
                int port = 0;
                try {
                    port = Integer.valueOf(addressparts[1]);
                } catch (Exception e) {
                    Statics.error("Port (" + addressparts[1] + ") ist keine Zahl...");
                    return;
                }
                try {
                    Socket client = new Socket(addressparts[0], port);
                    p.switchFrame(new ChatPanel(p, client, username.getText()));
                } catch (Exception e) {
                    Statics.error(e.toString());// Schick den error an meine Methode weiter
                }
            }
        });

        /**
         * Alle elemente schön in der Mitte Platzieren mit bisschen Mathematik
         *
         * Und beim Jlabel mit fontmetrics gearbeitet, damit ich weiß welche width das
         * JLabel braucht
         */

        serveraddr.setBounds(p.getWidth() / 2 - 100, p.getHeight() / 2 - 12, 200, 25);
        jl.setBounds(jl.getFontMetrics(jl.getFont()).getStringBounds(jl.getText(), jl.getGraphics()).getBounds());
        jl.setLocation(p.getWidth() / 2 - jl.getWidth() / 2, serveraddr.getY() - 20);

        username.setBounds(p.getWidth() / 2 - 100, p.getHeight() / 2 + 35, 200, 25);
        jl2.setBounds(jl2.getFontMetrics(jl2.getFont()).getStringBounds(jl2.getText(), jl2.getGraphics()).getBounds());
        jl2.setLocation(p.getWidth() / 2 - jl2.getWidth() / 2, username.getY() - 20);

        connectButton.setBounds(p.getWidth() / 2 - 50, p.getHeight() / 2 + 75, 100, 30);
        add(serveraddr);
        add(username);
        add(jl);
        add(jl2);
        add(connectButton);

    }
}
