package ru.shashki.server.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.12.14
 * Time: 14:10
 */
@Entity
@Table(name = "friend")
@AssociationOverrides({
        @AssociationOverride(name = "pk.friend", joinColumns = @JoinColumn(name = "friend_id", insertable = false, updatable = false)),
        @AssociationOverride(name = "pk.friendOf", joinColumns = @JoinColumn(name = "friend_of_id", insertable = false, updatable = false))})
public class Friend implements Serializable {

    @EmbeddedId
    private FriendId pk = new FriendId();

    private boolean favorite;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return Objects.equals(pk, friend.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public FriendId getPk() {
        return pk;
    }

    public void setPk(FriendId pk) {
        this.pk = pk;
    }
}
