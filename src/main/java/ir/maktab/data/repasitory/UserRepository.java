package ir.maktab.data.repasitory;

import ir.maktab.data.dto.UserCategoryDto;
import ir.maktab.data.entity.enums.UserRole;
import ir.maktab.data.entity.serviceSystem.SubService;
import ir.maktab.data.entity.user.User;
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
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    // static Specification<User> filterByCriteria(String name, String family, String email, UserRole role, String service) {
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

    Optional<User> findByEmail(String email);


}

/* if (service != null && role.equals(UserRole.EXPERT)) {
                Join<User, SubService> serviceJoin = root.joinList("serviceList");
                predicateList.add(cb.equal(serviceJoin.get("name"), service));
            }*/
   /* static Specification<Product> filterProducts(ProductCategoryDto dto) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            //category names: shoes, accessory, ...
            //min price: 100
            //max price: 500
            CriteriaQuery<Product> resultCriteria = criteriaBuilder.createQuery(Product.class);
            Join<Product, Category> category = root.join("category");
           *//* Subquery<Category> subquery = resultCriteria.subquery(Category.class);
            Predicate truePredicate = criteriaBuilder.isTrue(criteriaBuilder.literal(true));
*//*
            List<Predicate> predicates = new ArrayList<>();
            if (!CollectionUtils.isEmpty(dto.getCategoryNames())) {
                //where category.name in (shoes,accessory)
                predicates.add(criteriaBuilder.in(category.get("name")).value(dto.getCategoryNames()));
//                predicates.add(criteriaBuilder.like(category.get("name"),"%shoes%"));
            }
            if (dto.getMinPrice() != null) {
                //and product.price>=100
                predicates.add(criteriaBuilder.ge(root.get("price"), dto.getMinPrice()));
            }
            if (dto.getMaxPrice() != null) {
                //and product.price<=500
                predicates.add(criteriaBuilder.le(root.get("price"), dto.getMaxPrice()));
            }
//            criteriaQuery.groupBy(root.get("productType")).orderBy(criteriaBuilder.asc(category.get("name")));
            resultCriteria.select(root).where(predicates.toArray(new Predicate[0]));
            return resultCriteria.getRestriction();
        };
    }*/
