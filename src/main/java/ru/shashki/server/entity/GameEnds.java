package ru.shashki.server.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 10.01.15
 * Time: 22:51
 */
public enum GameEnds implements Serializable {
  BLACK_WON,
  WHITE_WON,
  BLACK_LEFT,
  WHITE_LEFT,
  SURRENDER_BLACK,
  SURRENDER_WHITE,
  DRAW
}
