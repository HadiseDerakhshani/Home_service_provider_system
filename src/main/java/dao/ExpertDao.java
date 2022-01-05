package dao;

import dto.ExpertDto;
import model.person.Expert;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import service.ExpertService;

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

    public int maxId() {
        session = builderSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("select max(id) from Expert ");
        int maxId = (int) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        return maxId;
    }

    public List<ExpertDto> filter(String name, String family, String email, String duty) {
        session = builderSessionFactory().openSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Expert.class, "e");
        criteria.createAlias("e.serviceList", "s");

        Criterion nameCond = null;
        if (name != null && name.length() != 0) {
            nameCond = Restrictions.eq("e.firstName", name);
        }
        Criterion familyCond = null;
        if (family != null && family.length() != 0) {
            familyCond = Restrictions.eq("e.lastName", family);
        }
        Criterion emailCond = null;
        if (email != null && email.length() != 0) {
            emailCond = Restrictions.eq("e.email", email);
        }
        Criterion dutyCond = null;
        if (duty != null && duty.length() != 0) {
            dutyCond = Restrictions.eq("s.name", duty);
        }


        if (nameCond != null && familyCond != null && emailCond != null && duty != null)
            criteria.add(Restrictions.or(nameCond, familyCond, emailCond, dutyCond));

        if (nameCond != null && familyCond != null && emailCond != null && duty == null)
            criteria.add(Restrictions.or(nameCond, familyCond, emailCond));
        if (nameCond != null && familyCond != null && emailCond == null && duty != null)
            criteria.add(Restrictions.or(nameCond, familyCond, dutyCond));
        if (nameCond != null && familyCond == null && emailCond != null && duty != null)
            criteria.add(Restrictions.or(nameCond, emailCond, dutyCond));
        if (nameCond == null && familyCond != null && emailCond != null && duty != null)
            criteria.add(Restrictions.or(familyCond, emailCond, dutyCond));

        if (nameCond != null && familyCond != null && emailCond == null && duty == null)
            criteria.add(Restrictions.or(nameCond, familyCond));
        if (nameCond != null && familyCond == null && emailCond != null && duty == null)
            criteria.add(Restrictions.or(nameCond, emailCond));
        if (nameCond == null && familyCond != null && emailCond != null && duty == null)
            criteria.add(Restrictions.or(familyCond, emailCond));
        if (nameCond == null && familyCond == null && emailCond != null && duty != null)
            criteria.add(Restrictions.or(emailCond, dutyCond));
        if (nameCond == null && familyCond != null && emailCond == null && duty != null)
            criteria.add(Restrictions.or(familyCond, dutyCond));
        if (nameCond != null && familyCond == null && emailCond == null && duty != null)
            criteria.add(Restrictions.or(nameCond, dutyCond));

        if (nameCond != null && familyCond == null && emailCond == null && duty == null)
            criteria.add(nameCond);
        if (nameCond == null && familyCond != null && emailCond == null && duty == null)
            criteria.add(familyCond);
        if (nameCond == null && familyCond == null && emailCond != null && duty == null)
            criteria.add(emailCond);
        if (nameCond == null && familyCond == null && emailCond == null && duty != null)
            criteria.add(dutyCond);


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
        ExpertService expertService = new ExpertService();
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

}
