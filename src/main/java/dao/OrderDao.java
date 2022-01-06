package dao;

import dto.OrderDto;
import model.Order;
import model.enums.OrderStatus;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.util.List;

public class OrderDao extends BaseDao {
    private Session session;

    public int save(Order order) {
        if (order == null)
            throw new RuntimeException("order is null ");
        else {
            session = builderSessionFactory().openSession();
            session.beginTransaction();
            int id = (int) session.save(order);
            session.getTransaction().commit();
            session.close();
            return id;
        }
    }

    public int maxId() {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("select max(id) from Order ");
        int maxId = (int) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return maxId;
    }

    public List<OrderDto> findToGetSuggest() {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Order.class, "0");
        criteria.add(Restrictions.eq("o.status", OrderStatus.WAITING_FOR_EXPERT_SUGGESTION.name()));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("o.ProposedPrice").as("ProposedPrice"))
                .add(Projections.property("o.jobDescription").as("jobDescription"))
                .add(Projections.property("o.doDate").as("doDate"))
                .add(Projections.property("o.address").as("address"))
        );
        criteria.setResultTransformer(Transformers.aliasToBean(OrderDto.class));
        List<OrderDto> list = (List<OrderDto>) criteria.list();

        session.getTransaction().commit();
        session.close();
        return list;
    }
}
