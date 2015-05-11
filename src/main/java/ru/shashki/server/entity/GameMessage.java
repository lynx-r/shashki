package ru.shashki.server.entity;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 15:49
 */
@Entity
@Table(name = "game_message")
public class GameMessage extends PersistableObject implements Message {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Shashist sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Shashist receiver;

    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType;

    private String data;

    @Column(name = "sent_date")
    private LocalDate sentDate;

    @Column(name = "start_step")
    private String startStep;

    @Column(name = "end_step")
    private String endStep;

    private String captured;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Override
    public Shashist getSender() {
        return sender;
    }

    @Override
    public void setSender(Shashist entity) {
        this.sender = entity;
    }

    @Override
    public Shashist getReceiver() {
        return receiver;
    }

    @Override
    public void setReceiver(Shashist receiver) {
        this.receiver = receiver;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public LocalDate getSentDate() {
        return sentDate;
    }

    @Override
    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getStartMove() {
        return startStep;
    }

    public void setStartMove(String startStep) {
        this.startStep = startStep;
    }

    public String getEndMove() {
        return endStep;
    }

    public void setEndMove(String endStep) {
        this.endStep = endStep;
    }

    public String getCaptured() {
        return captured;
    }

    public void setCaptured(String captured) {
        this.captured = captured;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game entity) {
        this.game = entity;
    }
}
