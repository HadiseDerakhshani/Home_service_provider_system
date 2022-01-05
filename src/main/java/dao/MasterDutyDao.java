package dao;

import model.serviceSystem.MasterDuty;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class MasterDutyDao extends BaseDao {
    private Session session;

    public int save(MasterDuty masterDuty) {
        if (masterDuty == null)
            throw new RuntimeException("masterService is null ");
        else {
            session = builderSessionFactory().openSession();
            session.beginTransaction();
            int id = (int) session.save(masterDuty);
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }

    public int maxId() {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("select max(id) from MasterDuty ");
        int maxId = (int) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return maxId;
    }

    public boolean findByName(String name) {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from MasterDuty where name=:name");
        query.setParameter("name", name);
        MasterDuty masterDuty = (MasterDuty) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        if (masterDuty != null)
            return true;
        return false;
    }

    public List<MasterDuty> showAll() {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from MasterDuty");
        List<MasterDuty> list = (List<MasterDuty>) query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
