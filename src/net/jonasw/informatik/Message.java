package net.jonasw.informatik;

import java.util.Calendar;

/**
 * Das habe ich extra so gemacht, damit ich in der JList als Datentyp "Message"
 * angeben kann und es mir dann automatisch so formatiert wird wie ich es in der
 * toString methode gesagt habe
 * 
 * Die Daten kommen vom Server
 * 
 * time sind die Millisekunden seit 1.1.1970 Deswegen wird das da unten in eine
 * Uhrzeit umgewandelt mit Hilfe der Calendar Klasse
 */
public class Message {
    String username;
    String msg;
    long time;

    public Message(String username, String msg, long time) {
        this.username = username;
        this.msg = msg;
        this.time = time;
    }

    @Override
    public String toString() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(this.time);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int mins = c.get(Calendar.MINUTE);
        // So formatieren das es ein 0 hat wenn es eine braucht
        String stunden = hours <= 9 ? "0" + hours : String.valueOf(hours);
        String minuten = mins <= 9 ? "0" + mins : String.valueOf(mins);
        return "[" + stunden + ":" + minuten + "] " + this.username + ": " + this.msg;
    }
}
