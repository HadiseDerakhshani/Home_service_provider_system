package ir.maktab.data.dao;

import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    static Specification<User> filterByCriteria(String name, String family, String email, UserRole role, String service) {
        return (Specification<User>) (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (name != null && !name.isEmpty())
                predicateList.add(cb.equal(root.get("firstName"), name));
            if (family != null && !family.isEmpty())
                predicateList.add(cb.equal(root.get("lastName"), family));
            if (email != null && !email.isEmpty())
                predicateList.add(cb.equal(root.get("email"), email));
            if (role != null)
                predicateList.add(cb.equal(root.get("userRole"), role));
            if (service != null && role.equals(UserRole.EXPERT)) {
                Join<User, SubService> serviceJoin = root.joinList("serviceList");
                predicateList.add(cb.equal(serviceJoin.get("name"), service));
            }
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }


    Optional<User> findByEmail(String email);
}
