package dao;

import dto.CustomerDto;
import model.person.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.util.List;

public class CustomerDao extends BaseDao {
    private Session session;

    public int save(Customer customer) {//saveCustomer or save
        if (customer == null)
            throw new RuntimeException("SECOND NUMBER IS ZERO ");
        else {
            session = builderSessionFactory().openSession();
            session.beginTransaction();
            int customerId = (int) session.save(customer);
            session.getTransaction().commit();
            session.close();
            return customerId;
        }

    }

    public int maxId() {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("select max(id) from Customer ");
        int maxId = (int) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return maxId;
    }

    public List<CustomerDto> filter(String caseFilter) {
        String[] split = caseFilter.split(",");
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Customer.class, "c");
        SimpleExpression name = Restrictions.eq("c.firstName", split[0]);
        SimpleExpression family = Restrictions.eq("c.lastName", split[1]);
        SimpleExpression email = Restrictions.eq("c.email", split[2]);
        Disjunction orCon = Restrictions.or(name, family, email);
        criteria.add(orCon);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("c.firstName").as("firstName"))
                .add(Projections.property("c.lastName").as("lastName"))
                .add(Projections.property("c.email").as("emil"))
                .add(Projections.property("c.phoneNumber").as("phoneNumber"))
                .add(Projections.property("c.userStatus").as("userStatus"))
                .add(Projections.property("c.dateRegister").as("dateRegister"))
                .add(Projections.property("c.credit").as("credit")));
        criteria.setResultTransformer(Transformers.aliasToBean(CustomerDto.class));
        List<CustomerDto> list = criteria.list();

        session.getTransaction().commit();
        session.close();
        return list;

    }

}
