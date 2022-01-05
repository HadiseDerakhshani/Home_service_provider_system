package dao;

import dto.CustomerDto;
import model.person.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.util.List;

public class CustomerDao extends BaseDao {
    private Session session;

    public int save(Customer customer) {
        if (customer == null)
            throw new RuntimeException("Customer is null ");
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

    public List<CustomerDto> filter(String name, String family, String email) {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Customer.class, "c");

        Criterion nameCond = null;
        if (name != null && name.length() != 0) {
            nameCond = Restrictions.eq("c.firstName", name);
        }
        Criterion familyCond = null;
        if (family != null && family.length() != 0) {
            familyCond = Restrictions.eq("c.lastName", family);
        }
        Criterion emailCond = null;
        if (email != null && email.length() != 0) {
            emailCond = Restrictions.eq("c.email", email);
        }


        if (nameCond != null && familyCond != null && emailCond != null)
            criteria.add(Restrictions.or(nameCond, familyCond, emailCond));
        if (nameCond != null && familyCond != null && emailCond == null)
            criteria.add(Restrictions.or(nameCond, familyCond));
        if (nameCond != null && familyCond == null && emailCond != null)
            criteria.add(Restrictions.or(nameCond, emailCond));
        if (nameCond == null && familyCond != null && emailCond != null)
            criteria.add(Restrictions.or(familyCond, emailCond));
        if (nameCond != null && familyCond == null && emailCond == null)
            criteria.add(nameCond);
        if (nameCond == null && familyCond != null && emailCond == null)
            criteria.add(familyCond);
        if (nameCond == null && familyCond == null && emailCond != null)
            criteria.add(emailCond);


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

    public Customer findByEmail(String email) {
        session = builderSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Customer c where c.email=:email");
        query.setParameter("email", email);
        Customer customer = (Customer) query.uniqueResult();

        session.getTransaction().commit();

        session.close();
        return customer;
    }

}
