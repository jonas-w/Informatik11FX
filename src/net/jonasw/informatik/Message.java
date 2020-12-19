package net.jonasw.informatik;

import java.util.Calendar;

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
        return "[" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + "] " + this.username + ": "
                + this.msg;
    }
}
