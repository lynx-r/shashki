package ru.shashki.server.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 16:18
 */
@Entity
@Table(name = "game")
public class Game extends PersistableObjectImpl {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_white_id")
  private Shashist playerWhite;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "player_black_id")
  private Shashist playerBlack;

  @Enumerated(EnumType.STRING)
  @Column(name = "play_end_status")
  private GameEnds playEndStatus;

  @Column(name = "play_start_date")
  private Date playStartDate;

  @Column(name = "play_end_date")
  private Date playEndDate;

  @Column(name = "party_notation", length = 1000)
  private String partyNotation;

  public Shashist getPlayerWhite() {
    return playerWhite;
  }

  public Shashist getPlayerBlack() {
    return playerBlack;
  }

  public void setPlayerBlack(Shashist playerBlack) {
    this.playerBlack = playerBlack;
  }

  public GameEnds getPlayEndStatus() {
    return playEndStatus;
  }

  public void setPlayEndStatus(GameEnds playEndStatus) {
    this.playEndStatus = playEndStatus;
  }

  public Date getPlayStartDate() {
    return playStartDate;
  }

  public void setPlayStartDate(Date playStartDate) {
    this.playStartDate = playStartDate;
  }

  public Date getPlayEndDate() {
    return playEndDate;
  }

  public void setPlayEndDate(Date playEndDate) {
    this.playEndDate = playEndDate;
  }

  public String getPartyNotation() {
    return partyNotation;
  }

  public void setPartyNotation(String partyNotation) {
    this.partyNotation = partyNotation;
  }
}
