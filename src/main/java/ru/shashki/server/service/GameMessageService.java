package ru.shashki.server.service;

import ru.shashki.server.dao.Dao;
import ru.shashki.server.dao.PlayerMessageDao;
import ru.shashki.server.entity.GameMessage;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 21:52
 */
@Stateless
public class GameMessageService extends BaseService<GameMessage> {

    @Inject
    private PlayerMessageDao playerMessageDao;

    @Override
    protected Dao<GameMessage> getDao() {
        return playerMessageDao;
    }

    public List<GameMessage> findLastMessages(int countLast, Long playerId, Long opponentId) {
        return playerMessageDao.findLastMessages(countLast, playerId, opponentId);
    }
}
