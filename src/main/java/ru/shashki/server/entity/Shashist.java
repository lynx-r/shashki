package ru.shashki.server.entity;

import org.hibernate.validator.constraints.Email;
import ru.shashki.server.util.converter.AuthProviderConverter;
import ru.shashki.server.util.converter.LocalDateConverter;
import ru.shashki.server.util.converter.ShashistStatusConverter;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
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
@Converts(value = {
        @Convert(attributeName = "authProvider", converter = AuthProviderConverter.class),
        @Convert(attributeName = "registerDate", converter = LocalDateConverter.class),
        @Convert(attributeName = "lastVisitedDate", converter = LocalDateConverter.class),
        @Convert(attributeName = "status", converter = ShashistStatusConverter.class),
})
public class Shashist extends PersistableObjectImpl {

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "user_id")
    private String userId;

    @Email
    private String email;

    @Lob
    private byte[] photo;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "auth_provider")
    private AuthProvider authProvider;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.friendOf", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Friend> friends = new HashSet<>();

    @Column(name = "fiends_of")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.friend", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Friend> friendOf = new HashSet<>();

    @OneToMany(mappedBy = "receiver")
    private Set<GameMessage> receivedPlayerMessages = new HashSet<>();

    @OneToMany(mappedBy = "sender")
    private Set<GameMessage> sentPlayerMessages = new HashSet<>();

    @OneToMany(mappedBy = "receiver")
    private Set<GameMessage> receivedGameMessages = new HashSet<>();

    @OneToMany(mappedBy = "sender")
    private Set<GameMessage> sentGameMessages = new HashSet<>();

    @OneToMany(mappedBy = "playerWhite")
    private Set<Game> whiteRoleGames = new HashSet<>();

    @OneToMany(mappedBy = "playerBlack")
    private Set<Game> blackRoleGames = new HashSet<>();

    private ShashistStatus status;
    @Column(name = "register_date")
    private LocalDate registerDate;
    @Column(name = "last_visited_date")
    private LocalDate lastVisitedDate;
    @Column(name = "visit_counter")
    private int visitCounter;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
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

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public LocalDate getLastVisitedDate() {
        return lastVisitedDate;
    }

    public void setLastVisitedDate(LocalDate lastVisited) {
        this.lastVisitedDate = lastVisited;
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

    public ShashistStatus getStatus() {
        return status;
    }

    public void setStatus(ShashistStatus status) {
        this.status = status;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    // Some Logic
    public boolean isOnline() {
        return !ShashistStatus.OFFLINE.equals(status);
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
        } else if (getUserId() != null) {
            uid = getUserId();
        }
        return BigInteger.valueOf(uid.hashCode()).toString(16);
    }
}
