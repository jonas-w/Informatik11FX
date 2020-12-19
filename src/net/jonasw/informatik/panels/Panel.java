package net.jonasw.informatik.panels;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.jonasw.informatik.Statics;
import java.awt.FlowLayout;
import net.jonasw.informatik.Main;

/**
 * Grund Klasse für meine Panels Damit ich nicht immer die Größe und so wieter
 * in ChatPanel und StartPanel setzen muss, die diese Klasse "extenden"
 * 
 */
public class Panel extends JPanel {
    public Panel(Main p) {
        this.setSize(p.getSize());
        this.setLocation(0, 0);
        this.setBackground(Statics.background);
        this.setLayout(null);
    }
}