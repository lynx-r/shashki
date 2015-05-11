package ru.shashki.server.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.12.14
 * Time: 14:19
 */
@Embeddable
public class FriendId implements Serializable {

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Shashist friend;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Shashist friendOf;

  public Shashist getFriend() {
    return friend;
  }

  public void setFriend(Shashist friend) {
    this.friend = friend;
  }

  public Shashist getFriendOf() {
    return friendOf;
  }

  public void setFriendOf(Shashist friendOf) {
    this.friendOf = friendOf;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FriendId friendId = (FriendId) o;
    return Objects.equals(friend, friendId.friend) &&
            Objects.equals(friendOf, friendId.friendOf);
  }

  @Override
  public int hashCode() {
    return Objects.hash(friend, friendOf);
  }
}
