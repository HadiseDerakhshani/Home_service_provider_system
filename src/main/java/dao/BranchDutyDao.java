package dao;

import model.serviceSystem.BranchDuty;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class BranchDutyDao extends BaseDao {
    private Session session;


    public int save(BranchDuty branchDuty) {
        if (branchDuty == null)
            throw new RuntimeException("branchService is null ");
        else {
            session = builderSessionFactory().openSession();
            session.beginTransaction();
            int id = (int) session.save(branchDuty);
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }

    public int maxId() {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("select max(id) from BranchDuty ");
        int maxId = (int) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return maxId;
    }

}
