package ru.shashki.server.service;

import ru.shashki.server.dao.Dao;
import ru.shashki.server.dao.ShashistDao;
import ru.shashki.server.entity.Shashist;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 0:08
 */
@Stateless
public class ShashistService extends BaseService<Shashist> {

    @Inject
    private ShashistDao shashistDao;

    @Override
    protected Dao<Shashist> getDao() {
        return shashistDao;
    }

    public Shashist findByUserId(String uid) {
        return shashistDao.findByUserId(uid);
    }

    public Shashist findBySessionId(String sessionId) {
        return shashistDao.findBySessionId(sessionId);
    }
}
