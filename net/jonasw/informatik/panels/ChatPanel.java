package net.jonasw.informatik.panels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import net.jonasw.informatik.Main;

import javax.swing.JFrame;
import java.net.Socket;


public class ChatPanel extends net.jonasw.informatik.panels.Panel {
    final OutputStream clientOut;
    final InputStream clientIn;

    public ChatPanel(Main p, Socket client, String username) throws IOException {
        super(p);
        clientOut = client.getOutputStream();
        clientIn = client.getInputStream();
        clientOut.write((username + "\n").getBytes());// Dem Server sozusagen Hallo sagen
        clientOut.flush();// Ansonsten wird die Nachricht nicht rausgeschickt

    }
}
