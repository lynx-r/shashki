package ru.shashki.server.dao;

import ru.shashki.server.entity.Game;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 17:33
 */
public interface GameDao extends Dao<Game> {
  Game findLazyFalse(Long id);
}
