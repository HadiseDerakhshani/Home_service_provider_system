package dao;

import model.Suggestion;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class SuggestionDao extends BaseDao {
    private Session session;

    public int save(Suggestion suggestion) {
        if (suggestion == null)
            throw new RuntimeException("Suggestion is null ");
        else {
            session = builderSessionFactory().openSession();
            session.beginTransaction();
            int id = (int) session.save(suggestion);
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }

    public int maxId() {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("select max(id) from Suggestion ");
        int maxId = (int) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return maxId;
    }
}
