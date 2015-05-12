package ru.shashki.server.dao.impl;

import ru.shashki.server.dao.GameDao;
import ru.shashki.server.entity.Game;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 17:34
 */
@Stateless
public class GameDaoImpl extends DaoImpl<Game> implements GameDao {

  @Inject
  private EntityManager entityManager;

  public GameDaoImpl() {
    super(Game.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Game findLazyFalse(Long id) {
    String hql = "SELECT g " +
        "FROM Game g " +
        "JOIN FETCH g.playerWhite " +
        "JOIN FETCH g.playerBlack " +
        "WHERE g.id = :gameId";
    Query query = entityManager.createQuery(hql);
    query.setParameter("gameId", id);
    return (Game) query.getSingleResult();
  }
}
