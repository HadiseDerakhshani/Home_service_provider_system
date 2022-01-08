package data.dao;

import data.dto.ExpertDto;
import data.user.Expert;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.util.List;

public class ExpertDao extends BaseDao {
    private Session session;

    public int save(Expert expert) {
        if (expert == null)
            throw new RuntimeException("Expert is null ");
        else {
            session = builderSessionFactory().openSession();
            session.beginTransaction();
            int id = (int) session.save(expert);
            session.getTransaction().commit();
            session.close();
            return id;
        }

    }


    public List<ExpertDto> filter(String name, String family, String email, String duty) {
        session = builderSessionFactory().openSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Expert.class, "e");
        criteria.createAlias("e.serviceList", "s");


        if (name != null && name.length() != 0) {
            criteria.add(Restrictions.eq("e.firstName", name));
        }

        if (family != null && family.length() != 0) {
            criteria.add(Restrictions.eq("e.lastName", family));
        }

        if (email != null && email.length() != 0) {
            criteria.add(Restrictions.eq("e.email", email));
        }

        if (duty != null && duty.length() != 0) {
            criteria.add(Restrictions.eq("s.name", duty));
        }

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("e.firstName").as("firstName"))
                .add(Projections.property("e.lastName").as("lastName"))
                .add(Projections.property("e.email").as("emil"))
                .add(Projections.property("e.phoneNumber").as("phoneNumber"))
                .add(Projections.property("e.dateRegister").as("dateRegister"))
                .add(Projections.property("e.image").as("image"))
                .add(Projections.property("e.score").as("score"))
                .add(Projections.property("e.serviceList").as("serviceList")));

        criteria.setResultTransformer(Transformers.aliasToBean(ExpertDto.class));

        List<ExpertDto> list = criteria.list();
        session.getTransaction().commit();
        session.close();

        return list;
    }

    public Expert findByEmail(String email) {

        session = builderSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Expert e where e.email=:email");
        query.setParameter("email", email);
        Expert expert = (Expert) query.uniqueResult();

        session.getTransaction().commit();
        session.close();
        return expert;
    }

    public int update(String querySyntax, String value, String email, int filed) {

        int update;
        session = builderSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery(querySyntax);
        switch (filed) {
            case 6 -> query.setParameter("newValue", Double.parseDouble(value));
            case 7 -> query.setParameter("newValue", Integer.parseInt(value));
            default -> query.setParameter("newValue", value);
        }
        query.setParameter("email", email);
        update = query.executeUpdate();

        session.getTransaction().commit();

        session.close();
        return update;
    }

    public ExpertDto selectByEmail(String email) {
        session = builderSessionFactory().openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Expert.class, "c");
        criteria.add(Restrictions.eq("c.email", email));

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("e.firstName").as("firstName"))
                .add(Projections.property("e.lastName").as("lastName"))
                .add(Projections.property("e.email").as("emil"))
                .add(Projections.property("e.phoneNumber").as("phoneNumber"))
                .add(Projections.property("e.dateRegister").as("dateRegister"))
                .add(Projections.property("e.image").as("image"))
                .add(Projections.property("e.score").as("score"))
                .add(Projections.property("e.serviceList").as("serviceList")));
        criteria.setResultTransformer(Transformers.aliasToBean(ExpertDto.class));
        ExpertDto expert = (ExpertDto) criteria.uniqueResult();


        session.getTransaction().commit();

        session.close();
        return expert;
    }

}
