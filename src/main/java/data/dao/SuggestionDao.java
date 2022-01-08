package data.dao;

import data.order.Suggestion;
import org.hibernate.Session;

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


}
