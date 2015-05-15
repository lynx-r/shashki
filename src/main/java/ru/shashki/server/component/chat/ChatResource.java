package ru.shashki.server.component.chat;

import org.jboss.logging.Logger;
import org.primefaces.push.EventBus;
import org.primefaces.push.RemoteEndpoint;
import org.primefaces.push.annotation.*;
import ru.shashki.server.component.chat.message.Message;
import ru.shashki.server.component.chat.message.MessageDecoder;
import ru.shashki.server.component.chat.message.MessageEncoder;

import javax.inject.Inject;
import javax.servlet.ServletContext;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 08.05.15
 * Time: 13:32
 */
@PushEndpoint("/{room}/{user}")
@Singleton
public class ChatResource {

    private final Logger logger = Logger.getLogger(ChatResource.class);

    @PathParam("room")
    private String room;

    @PathParam("user")
    private String username;

    @Inject
    private ServletContext context;

    @OnOpen
    public void onOpen(RemoteEndpoint r, EventBus eventBus) {
        logger.info("OnOpen {} " + r.toString());

        eventBus.publish(room + "/*", new Message(String.format("%s has entered the room '%s'", username, room), true));
    }

    @OnClose
    public void onClose(RemoteEndpoint r, EventBus eventBus) {
        ChatUsers users = (ChatUsers) context.getAttribute("chatUsers");
        users.removeUser(username);

        eventBus.publish(room + "/*", new Message(String.format("%s has left the room", username), true));
    }

    @OnMessage(decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
    public Message onMessage(Message message) {
        return message;
    }
}
