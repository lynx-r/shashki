package ru.shashki.server.service;

import ru.shashki.server.dao.Dao;
import ru.shashki.server.dao.GameDao;
import ru.shashki.server.entity.Game;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 17:38
 */
@Stateless
public class GameService extends BaseService<Game> {

    @Inject
    private GameDao gameDao;

    @Override
    protected Dao<Game> getDao() {
        return gameDao;
    }

    public Game findLazyFalse(Long id) {
        return gameDao.findLazyFalse(id);
    }
}
