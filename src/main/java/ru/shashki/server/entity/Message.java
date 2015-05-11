package ru.shashki.server.entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.10.14
 * Time: 22:51
 */
 interface Message {

     Shashist getSender();

     void setSender(Shashist sender);

     Shashist getReceiver();

     void setReceiver(Shashist receiver);

     MessageType getMessageType();

     void setMessageType(MessageType messageType);

     String getMessage();

     void setMessage(String message);

     String getData();

     void setData(String data);

     LocalDate getSentDate();

     void setSentDate(LocalDate sentDate);

     enum MessageType implements Serializable {

        CHAT_MESSAGE,
        CHAT_PRIVATE_MESSAGE,
        USER_LIST_UPDATE,
        PLAY_INVITE,
        PLAY_REJECT_INVITE,
        PLAY_START,
        PLAY_MOVE,
        PLAY_CANCEL_MOVE,
        PLAY_CANCEL_MOVE_RESPONSE,
        PLAY_LEFT,
        PLAY_PROPOSE_DRAW,
        PLAY_ACCEPT_DRAW,
        PLAY_END,
        PLAY_SURRENDER,
        PLAYER_REGISTER

    }
}
