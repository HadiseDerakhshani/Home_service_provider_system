package ir.maktab.data.repasitory;

import ir.maktab.data.dto.UserCategoryDto;
import ir.maktab.data.entity.enums.UserRole;
import ir.maktab.data.entity.serviceSystem.SubService;
import ir.maktab.data.entity.user.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public interface UserSpecifications {
    static Specification<User> filterByCriteria(UserCategoryDto category) {
        return (Specification<User>) (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (category.getFirstName() != null && !category.getFirstName().isEmpty())
                predicateList.add(cb.equal(root.get("firstName"), category.getFirstName()));
            if (category.getLastName() != null && !category.getLastName().isEmpty())
                predicateList.add(cb.equal(root.get("lastName"), category.getLastName()));
            if (category.getEmail() != null && !category.getEmail().isEmpty())
                predicateList.add(cb.equal(root.get("email"), category.getEmail()));
            if (category.getUserRole() != null)
                predicateList.add(cb.equal(root.get("userRole"), category.getUserRole()));
            if (category.getScore() != null && category.getUserRole().equals(UserRole.EXPERT)) {
                predicateList.add(cb.equal(root.get("score"), category.getScore()));
            }
            if (category.getService() != null && category.getUserRole().equals(UserRole.EXPERT)) {
                Join<User, SubService> serviceJoin = root.joinList("serviceList");
                predicateList.add(cb.equal(serviceJoin.get("name"), category.getService()));
            }
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
