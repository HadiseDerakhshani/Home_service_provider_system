package data.dao;

import data.dto.CustomerDto;
import data.enums.UserStatus;
import data.user.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
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


    public List<CustomerDto> filter(String name, String family, String email) {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Customer.class, "c");


        if (name != null && name.length() != 0) {
            criteria.add(Restrictions.eq("e.firstName", name));
        }

        if (family != null && family.length() != 0) {
            criteria.add(Restrictions.eq("e.lastName", family));
        }

        if (email != null && email.length() != 0) {
            criteria.add(Restrictions.eq("e.email", email));
        }

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

    public int update(String querySyntax, String value, String email, int filed) {
        int update;
        session = builderSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery(querySyntax);
        if (filed == 6)
            query.setParameter("newValue", Double.parseDouble(value));
        else
            query.setParameter("newValue", value);

        query.setParameter("email", email);
        update = query.executeUpdate();

        session.getTransaction().commit();

        session.close();
        return update;
    }

    public List<CustomerDto> findByStatus() {
        session = builderSessionFactory().openSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Customer.class, "c");
        criteria.add(Restrictions.eq("c.userStatus", UserStatus.WAITING_CONFIRM.name()));

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

    public int updateStatus(List<String> emailList) {

        int update = 0;
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        for (String email : emailList) {
            Query query = session.createQuery("update Customer  c set c.userStatus=:newValue where c.email=:email");
            query.setParameter("newValue", UserStatus.CONFIRMED);
            query.setParameter("email", email);
            update = query.executeUpdate();
        }
        session.getTransaction().commit();

        session.close();
        return update;
    }

    public CustomerDto showByEmail(String email) {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Customer.class, "c");
        criteria.add(Restrictions.eq("c.email", email));

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("c.firstName").as("firstName"))
                .add(Projections.property("c.lastName").as("lastName"))
                .add(Projections.property("c.email").as("emil"))
                .add(Projections.property("c.phoneNumber").as("phoneNumber"))
                .add(Projections.property("c.userStatus").as("userStatus"))
                .add(Projections.property("c.dateRegister").as("dateRegister"))
                .add(Projections.property("c.credit").as("credit")));
        criteria.setResultTransformer(Transformers.aliasToBean(CustomerDto.class));
        CustomerDto customer = (CustomerDto) criteria.uniqueResult();


        session.getTransaction().commit();

        session.close();
        return customer;
    }
}
