package ru.shashki.server.dao;

import ru.shashki.server.entity.GameMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 21:53
 */
public interface PlayerMessageDao extends Dao<GameMessage> {
  List<GameMessage> findLastMessages(int countLast, Long playerId, Long opponentId);
}
