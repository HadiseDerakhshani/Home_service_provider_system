package data.dao;

import data.model.serviceSystem.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceDao extends JpaRepository<Service, Integer> {

    Service finByName(String name);

    @Override
    Service save(Service service);

    @Override
    void delete(Service service);
    /*private Session session;

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
    }*/
}
