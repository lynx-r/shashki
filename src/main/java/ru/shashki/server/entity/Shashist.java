package ru.shashki.server.entity;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.12.14
 * Time: 0:36
 */
@Entity
@Table(name = "shashist")
public class Shashist extends PersistableObject {

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "vk_uid")
    private String vkUid;

    @Email
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "auth_provider")
    private String authProvider;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.friendOf", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Friend> friends;

    @Column(name = "fiends_of")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.friend", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Friend> friendOf;

    @OneToMany(mappedBy = "receiver")
    private Set<GameMessage> receivedPlayerMessages;

    @OneToMany(mappedBy = "sender")
    private Set<GameMessage> sentPlayerMessages;

    @OneToMany(mappedBy = "receiver")
    private Set<GameMessage> receivedGameMessages;

    @OneToMany(mappedBy = "sender")
    private Set<GameMessage> sentGameMessages;

    @OneToMany(mappedBy = "playerWhite")
    private Set<Game> whiteRoleGames;

    @OneToMany(mappedBy = "playerBlack")
    private Set<Game> blackRoleGames;

    @Column(name = "logged_in")
    private boolean loggedIn;
    private boolean playing;
    private boolean online;
    @Column(name = "register_date")
    private LocalDate registerDate;
    @Column(name = "last_visited")
    private LocalDate lastVisited;
    @Column(name = "visit_counter")
    private int visitCounter;
    @Column(name = "hash_id")
    private String hashId;

    public Shashist() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shashist shashist = (Shashist) o;
        return Objects.equals(sessionId, shashist.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }

    public String getVkUid() {
        return vkUid;
    }

    public void setVkUid(String vkUid) {
        this.vkUid = vkUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(String authProvider) {
        this.authProvider = authProvider;
    }

    public Set<Friend> getFriends() {
        return friends;
    }

    public void setFriends(Set<Friend> friends) {
        this.friends = friends;
    }

    public Set<Friend> getFriendOf() {
        return friendOf;
    }

    public void setFriendOf(Set<Friend> friendOf) {
        this.friendOf = friendOf;
    }

    public Set<GameMessage> getReceivedPlayerMessages() {
        return receivedPlayerMessages;
    }

    public void setReceivedPlayerMessages(Set<GameMessage> playerMessageEntities) {
        this.receivedPlayerMessages = playerMessageEntities;
    }

    public Set<GameMessage> getSentPlayerMessages() {
        return sentPlayerMessages;
    }

    public void setSentPlayerMessages(Set<GameMessage> playerMessageEntities) {
        this.sentPlayerMessages = playerMessageEntities;
    }

    public Set<GameMessage> getReceivedGameMessages() {
        return receivedGameMessages;
    }

    public void setReceivedGameMessages(Set<GameMessage> receivedGameMessages) {
        this.receivedGameMessages = receivedGameMessages;
    }

    public Set<GameMessage> getSentGameMessages() {
        return sentGameMessages;
    }

    public void setSentGameMessages(Set<GameMessage> sentGameMessages) {
        this.sentGameMessages = sentGameMessages;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public LocalDate getLastVisited() {
        return lastVisited;
    }

    public void setLastVisited(LocalDate lastVisited) {
        this.lastVisited = lastVisited;
    }

    public int getVisitCounter() {
        return visitCounter;
    }

    public void setVisitCounter(int visitCounter) {
        this.visitCounter = visitCounter;
    }

    public Set<Game> getWhiteRoleGames() {
        return whiteRoleGames;
    }

    public void setWhiteRoleGames(Set<Game> whiteRoleGames) {
        this.whiteRoleGames = whiteRoleGames;
    }

    public Set<Game> getBlackRoleGames() {
        return blackRoleGames;
    }

    public void setBlackRoleGames(Set<Game> blackRoleGames) {
        this.blackRoleGames = blackRoleGames;
    }

    public String getPublicName() {
        if (getPlayerName() == null) {
            String fullName = getFullName().trim();
            if (fullName.isEmpty()) {
                return getEmail().split("@")[0];
            }
            return fullName;
        }
        return getPlayerName();
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getSystemId() {
        String uid = "";
        if (getEmail() != null) {
            uid = getEmail();
        } else if (getVkUid() != null) {
            uid = getVkUid();
        }
        return BigInteger.valueOf(uid.hashCode()).toString(16);
    }
}
