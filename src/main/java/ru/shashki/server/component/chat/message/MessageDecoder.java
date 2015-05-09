package ru.shashki.server.component.chat.message;

import org.primefaces.push.Decoder;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 08.05.15
 * Time: 19:34
 */
public class MessageDecoder implements Decoder<String,Message> {

    //@Override
    public Message decode(String s) {
        String[] userAndMessage = s.split(":");
        if (userAndMessage.length >= 2) {
            return new Message().setUser(userAndMessage[0]).setText(userAndMessage[1]);
        }
        else {
            return new Message(s);
        }
    }
}