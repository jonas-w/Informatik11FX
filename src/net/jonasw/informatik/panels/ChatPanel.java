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

import java.net.Socket;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatPanel extends net.jonasw.informatik.panels.Panel {
    final OutputStream clientOut;
    final BufferedReader clientIn;

    public ChatPanel(Main p, Socket client, String username) throws IOException {
        super(p);
        p.setTitle("Nutzername: " + username);
        clientOut = client.getOutputStream();
        clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
        clientOut.write((username + "\n").getBytes());// Dem Server sozusagen Hallo sagen
        clientOut.flush();// Ansonsten wird die Nachricht nicht rausgeschickt
        DefaultListModel<net.jonasw.informatik.Message> listM = new DefaultListModel<net.jonasw.informatik.Message>();
        JList<net.jonasw.informatik.Message> messages = new JList<net.jonasw.informatik.Message>(listM);
        JScrollPane messagesScroll = new JScrollPane(messages);
        new Thread(() -> {
            try {

                while (true) {
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
                }
            } catch (Exception e) {
                Statics.error(e.toString());
            }
        }).start();
        JTextField msg = new JTextField();
        JButton msgSend = new JButton("Senden!");

        msgSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
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
        });

        JLabel label = new JLabel("Nachricht: ");
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
