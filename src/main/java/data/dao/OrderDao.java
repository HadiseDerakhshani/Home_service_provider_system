package data.dao;

import data.model.enums.OrderStatus;
import data.model.order.Order;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
    Order save(Order order);

    List<Order> findByStatusOrStatus(OrderStatus statusSelect, OrderStatus statusSuggest);

    Order findByReceptionNumber(long number);

    @Override
    <S extends Order> long count(Example<S> example);

    @Override
    List<Order> findAll();
    /* @Transactional
    @Modifying
    @Query(value = "update Order set status=:nawValue where id=:id", nativeQuery = true)
    void updateStatus(@Param("newValue") OrderStatus newValue, @Param("id") int id);

    @Transactional
    @Modifying
    @Query(value = "update Order set suggestion=:nawValue where id=:id", nativeQuery = true)
    void updateSuggestion(@Param("newValue") List<Suggestion> newValue, @Param("id") int id);
*/
}

  /*  public int save(Order order) {
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

    public List<OrderDto> findToGetSuggest() {

        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Order.class, "0");
        criteria.add(Restrictions.eq("o.status", OrderStatus.WAITING_FOR_EXPERT_SUGGESTION.name()));

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("o.id").as("id"))
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


    public int updateStatus(int id, OrderStatus status) {

        int update;
        session = builderSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("update Order  o set o.status=:newValue , o.suggestion=:newValue where o.id=:id");
        query.setParameter("newValue", status);
        query.setParameter("newValue", status);
        query.setParameter("id", id);
        update = query.executeUpdate();

        session.getTransaction().commit();

        session.close();
        return update;
    }

    public Order findById(int id) {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Order o where  o.id=:id");
        query.setParameter("id", id);
        Order order = (Order) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return order;
    }

    public int updateSuggestion(int id, List<Suggestion> suggest) {

        int update;
        session = builderSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("update Order  o set o.suggestion=:newValue where o.id=:id");
        query.setParameter("newValue", suggest);
        query.setParameter("id", id);
        update = query.executeUpdate();

        session.getTransaction().commit();

        session.close();
        return update;
    }

    public List<Order> findByStatus(OrderStatus status) {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Order o where  o.status=:status");
        query.setParameter("status", status);
        List<Order> order = (List<Order>) query.list();
        session.getTransaction().commit();
        session.close();
        return order;
    }
*/

