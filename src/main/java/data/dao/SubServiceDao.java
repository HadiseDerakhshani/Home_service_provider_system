package data.dao;

import data.serviceSystem.SubService;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SubServiceDao extends BaseDao {
    private Session session;


    public int save(SubService subService) {
        if (subService == null)
            throw new RuntimeException("SubService is null ");
        else {
            session = builderSessionFactory().openSession();
            session.beginTransaction();
            int id = (int) session.save(subService);
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }


    public List<SubService> findByName() {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from SubService");
        List<SubService> resultList = query.getResultList();
        session.getTransaction().commit();
        session.close();
        return resultList;
    }
}
