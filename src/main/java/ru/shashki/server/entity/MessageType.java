package ru.shashki.server.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 12.05.15
 * Time: 11:07
 */
public enum MessageType implements Serializable {

    CHAT_MESSAGE("Chat Message", "chat_message"),
    CHAT_PRIVATE_MESSAGE("Chat Private Message", "chat_private_message"),
    USER_LIST_UPDATE("User List Update", "user_list_update"),
    PLAY_INVITE("Play Invite", "play_invite"),
    PLAY_REJECT_INVITE("Play Reject Inviate", "play_reject_invite"),
    PLAY_START("Play Start", "play_start"),
    PLAY_MOVE("Play Move", "play_move"),
    PLAY_CANCEL_MOVE("Play Cancel Move", "play_cancel_move"),
    PLAY_CANCEL_MOVE_RESPONSE("Play Cancel Move Response", "play_cancel_move_response"),
    PLAY_LEFT("Play Left", "play_left"),
    PLAY_PROPOSE_DRAW("Play Propose Draw", "play_propose_draw"),
    PLAY_ACCEPT_DRAW("Play Accept Draw", "play_accept_draw"),
    PLAY_END("Play End", "play_end"),
    PLAY_SURRENDER("Play Surrender", "play_surrender"),
    PLAYER_REGISTER("Player Register", "player_register");

    private String label;
    private String dbRepresentation;

    MessageType(String label, String dbRepresentation) {
        this.label = label;
        this.dbRepresentation = dbRepresentation;
    }

    public String getLabel() {
        return label;
    }

    public String getDbRepresentation() {
        return dbRepresentation;
    }
}
