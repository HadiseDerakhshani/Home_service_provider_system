package ir.maktab.data.repasitory;

import ir.maktab.data.dto.OrderFilterDto;
import ir.maktab.data.entity.enums.OrderStatus;
import ir.maktab.data.entity.order.Order;
import ir.maktab.data.entity.serviceSystem.SubService;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface OrderSpecifications {
    static Specification<Order> filterByCriteria(OrderFilterDto filterDto, Set<SubService> listService) {
        return (Specification<Order>) (root, cq, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (filterDto.getBeginDate() != null)
                predicateList.add(cb.equal(root.get("registerDate"), filterDto.getBeginDate()));
            if (filterDto.getEndDate() != null)
                predicateList.add(cb.equal(root.get("registerDate"), filterDto.getEndDate()));
            if (filterDto.getBeginDate() != null && filterDto.getEndDate() != null)
                predicateList.add(cb.between(root.get("registerDate"), filterDto.getBeginDate(),filterDto.getEndDate()));
            if (filterDto.getSubService() != null && !filterDto.getSubService().isEmpty())
                predicateList.add(cb.equal(root.get("service"), filterDto.getSubService()));
            if (filterDto.getStatus() != null)
                predicateList.add(cb.equal(root.get("status"), OrderStatus.valueOf(filterDto.getStatus())));

            if (filterDto.getService() != null && !filterDto.getSubService().isEmpty()) {

                for (SubService service : listService) {
                    predicateList.add(cb.equal(root.get("service"), service));
                }
            }
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
