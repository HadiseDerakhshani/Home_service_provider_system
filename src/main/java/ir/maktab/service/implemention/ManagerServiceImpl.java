package ir.maktab.service.implemention;

import ir.maktab.data.dao.ManagerDao;
import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.mapping.ManagerMap;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.user.Expert;
import ir.maktab.data.model.user.Manager;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.ManagerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service

public class ManagerServiceImpl implements ManagerService {

    private final CustomerServiceImpl customerServiceImpl;

    private final OrderServiceImpl orderServiceImpl;

    private final ExpertServiceImpl expertServiceImpl;
    private final ManagerDao managerDao;

    private final ManagerMap managerMap;
@Autowired
    public ManagerServiceImpl(@Lazy CustomerServiceImpl customerServiceImpl,@Lazy OrderServiceImpl orderServiceImpl,
                              @Lazy ExpertServiceImpl expertServiceImpl, ManagerDao managerDao,@Lazy ManagerMap managerMap) {
        this.customerServiceImpl = customerServiceImpl;
        this.orderServiceImpl = orderServiceImpl;
        this.expertServiceImpl = expertServiceImpl;
        this.managerDao = managerDao;
        this.managerMap = managerMap;
    }

    @Override
    public Manager createManager(String userName, String pass) {
        Manager manager = Manager.builder()
                .username(userName)
                .password(pass).build();
        if (manager != null)
            throw new ObjectEntityNotFoundException("-- Manager is null --");
        return manager;
    }

    @Override
    public void Save(Manager manager) {
        if (checkManager(manager) == null) {
            managerDao.save(manager);
        }
        throw new ObjectEntityNotFoundException("--- manager is exit ----");
    }

    @Override
    public Manager checkManager(Manager manager) {

        return managerDao.findByUsernameAndPassword(manager.getUsername(), manager.getPassword()).get();
    }

    @Override
    public void customerConfirmation() {

        List<CustomerDto> byStatus = customerServiceImpl.findByUserStatus(UserStatus.WAITING_CONFIRM);
        List<String> customerListEmail = new ArrayList<>();
        for (CustomerDto customer : byStatus) {
            if (customer.getDateRegister().compareTo(customer.getDateUpdate()) == -1)
                customerListEmail.add(customer.getEmail());
        }
        for (String email : customerListEmail) {
            customerServiceImpl.updateStatus(email, UserStatus.CONFIRMED);
        }
    }

    @Override
    public void payment(int number, double amount, int score) {

        Order order = orderServiceImpl.findByReceptionNumber(number);

        if (order != null) {
            Expert expert = order.getExpert();
            orderServiceImpl.updatePricePaid(order, amount);
            orderServiceImpl.updateStatus(order, OrderStatus.PAID);
            expertServiceImpl.updateScore(score, expert);
            expertServiceImpl.updateCredit((0.80 * amount), expert);

        } else
            throw new ObjectEntityNotFoundException(" order is not exit");
    }
}
