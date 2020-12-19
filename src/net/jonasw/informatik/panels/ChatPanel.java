package net.jonasw.informatik.panels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

import net.jonasw.informatik.Main;
import net.jonasw.informatik.Statics;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.Socket;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatPanel extends net.jonasw.informatik.panels.Panel {
    final OutputStream clientOut;
    final BufferedReader clientIn;

    public ChatPanel(Main p, Socket client, String username) throws IOException {
        super(p);
        p.setTitle("Nutzername: " + username);// Title from Frame ändern
        clientOut = client.getOutputStream();// Damit man dem "Server" was schreiben kann
        clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));// Lesen was vom Server Kommt
        clientOut.write((username + "\n").getBytes());// Dem Server sozusagen Hallo sagen
        clientOut.flush();// Ansonsten wird die Nachricht nicht rausgeschickt
        DefaultListModel<net.jonasw.informatik.Message> listM = new DefaultListModel<net.jonasw.informatik.Message>();// Damit
                                                                                                                      // ich
                                                                                                                      // später
                                                                                                                      // Nachrichten
                                                                                                                      // Hinzufügen
                                                                                                                      // kann
        JList<net.jonasw.informatik.Message> messages = new JList<net.jonasw.informatik.Message>(listM);// Alle
                                                                                                        // nachrichten
                                                                                                        // werden darin
                                                                                                        // angezeigt
        JScrollPane messagesScroll = new JScrollPane(messages);// Damit man alle Nachrichten sehen kann
        new Thread(() -> {
            try {
                /**
                 * Hier wird alles was vom Server kommt gelesen Das wird in ein "Message" object
                 * umgewandelt/ mehr dazu in der Message.java datei es wird auch drauf geachtet,
                 * dass es automatisch runterscrollt wenn eine neue Nachricht dazu kommt
                 * 
                 * die while schleife läuft solange, bis der Server die Verbindung abbricht,
                 * oder halt das Programm beendet wird Ich frag erstmal ob ob das eine Nachricht
                 * ist die mit "MSG" anfängt Somit weiß ich, dass es ganz sicher so aufgebaut
                 * ist wie ich es will und dann in ein Message OBject verwandeln kann
                 * 
                 * die try catch sollte eigentlich nur werfen wenn er die long nicht parsen
                 * kann, aber dann ist mir das auch egal :)
                 * 
                 */
                String line;
                while ((line = clientIn.readLine()) != null) {
                    String[] args = line.split(Pattern.quote(Statics.lineSplitter));
                    if (args.length > 3) {
                        try {
                            if (args[0].equals("MSG")) {
                                listM.addElement(
                                        new net.jonasw.informatik.Message(args[1], args[2], Long.valueOf(args[3])));
                                messages.ensureIndexIsVisible(listM.size() - 1);
                            }
                        } catch (Exception e) {

                        }
                    }
                }

            } catch (Exception e) {
                Statics.error(e.toString());
            }
        }).start();
        JTextField msg = new JTextField();// Zum Nachrichten Schreiben
        JButton msgSend = new JButton("Senden!");// Damit man die auch abschicken kann
        // Das ist sozusagen eine Funktion
        Runnable sendMSGRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (msg.getText().length() > 0) {
                        clientOut.write(("MSG" + Statics.lineSplitter + msg.getText() + "\n").getBytes());
                        clientOut.flush();
                        msg.setText("");
                    }
                } catch (Exception e) {
                    Statics.error(e.toString());
                }
            }
        };
        // wird aufgerufen wenn der Button geklickt wird
        ActionListener sendMSG = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                sendMSGRunnable.run();
            }
        };
        msgSend.addActionListener(sendMSG);
        // Damit man auch mit der Entertaste die Nachricht senden kann
        msg.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    sendMSGRunnable.run();
                super.keyPressed(e);
            }
        });

        JLabel label = new JLabel("Nachricht: ");
        /**
         * Siehe StartPanel.java
         * 
         */

        label.setBounds(label.getFontMetrics(label.getFont()).getStringBounds(label.getText(), label.getGraphics())
                .getBounds());
        label.setLocation(10, p.getHeight() - 60);
        label.setForeground(Statics.foreground);
        msg.setBounds(label.getX() + label.getWidth(), p.getHeight() - 70, 400, 30);
        msgSend.setBounds(msg.getWidth() + msg.getX() + 20, msg.getY() - 5, 100, 40);
        messagesScroll.setBounds(10, 10, p.getWidth() - 20, p.getHeight() - 90);
        add(label);
        add(msg);
        add(messagesScroll);
        add(msgSend);
    }
}
