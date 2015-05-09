package ru.shashki.server.component.chat.message;

import org.primefaces.json.JSONObject;
import org.primefaces.push.Encoder;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 08.05.15
 * Time: 19:35
 */
public final class MessageEncoder implements Encoder<Message, String> {

    //@Override
    public String encode(Message message) {
        return new JSONObject(message).toString();
    }
}