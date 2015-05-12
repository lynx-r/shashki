package ru.shashki.server.dao.impl;

import ru.shashki.server.dao.ShashistDao;
import ru.shashki.server.entity.Shashist;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 0:19
 */
@Stateless
public class ShashistDaoImpl extends DaoImpl<Shashist> implements ShashistDao {

    @Inject
    private EntityManager entityManager;

    public ShashistDaoImpl() {
        super(Shashist.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Shashist findByUserId(String userId) {
        Query query = getEntityManager().createQuery("FROM Shashist " +
                "WHERE user_id = :userId");
        query.setParameter("userId", userId);
        List list = query.getResultList();
        return list.isEmpty() ? null : (Shashist) list.get(0);
    }

    @Override
    public Shashist findBySessionId(String sessionId) {
        Query query = getEntityManager().createQuery("FROM Shashist " +
                "WHERE sessionId = :sessionId");
        query.setParameter("sessionId", sessionId);
        List list = query.getResultList();
        return list.isEmpty() ? null : (Shashist) list.get(0);
    }
}
