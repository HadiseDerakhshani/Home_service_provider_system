package data.dao;

import data.model.serviceSystem.Service;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ServiceDao extends BaseDao {
    private Session session;

    public int save(Service service) {
        if (service == null)
            throw new RuntimeException("masterService is null ");
        else {
            session = builderSessionFactory().openSession();
            session.beginTransaction();
            int id = (int) session.save(service);
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }


    public boolean findByName(String name) {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Service where name=:name");
        query.setParameter("name", name);
        Service service = (Service) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        if (service != null)
            return true;
        return false;
    }

    public List<Service> selectAll() {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Service");
        List<Service> list = (List<Service>) query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }
}
