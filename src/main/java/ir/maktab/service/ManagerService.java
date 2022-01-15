package ir.maktab.service;

import ir.maktab.data.dao.ManagerDao;
import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.user.Expert;
import ir.maktab.data.model.user.Manager;
import ir.maktab.exception.ObjectEntityNotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class ManagerService {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final ExpertService expertService;
    private final ManagerDao managerDao;

    @Autowired
    public ManagerService(@Lazy CustomerService customerService, @Lazy OrderService orderService,
                          @Lazy ExpertService expertService, ManagerDao managerDao) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.expertService = expertService;
        this.managerDao = managerDao;
    }

    public Manager createManager(String userName, String pass) {
        Manager manager = Manager.builder()
                .username(userName)
                .password(pass).build();
        if (manager != null)
            throw new ObjectEntityNotFoundException("-- Manager is null --");
        return manager;
    }

    public void Save(Manager manager) {
        if (checkManager(manager) == null) {
            managerDao.save(manager);
        }
        throw new ObjectEntityNotFoundException("--- manager is exit ----");
    }

    public Manager checkManager(Manager manager) {

        return managerDao.findByUsernameAndPassword(manager.getUsername(), manager.getPassword()).get();
    }


    public void customerConfirmation() {

        List<CustomerDto> byStatus = customerService.findByUserStatus(UserStatus.WAITING_CONFIRM);
        List<String> customerListEmail = new ArrayList<>();
        for (CustomerDto customer : byStatus) {
            if (customer.getDateRegister().compareTo(customer.getDateUpdate()) == -1)
                customerListEmail.add(customer.getEmail());
        }
        for (String email : customerListEmail) {
            customerService.updateStatus(email, UserStatus.CONFIRMED);
        }
    }

    public void getPaid(int number, double amount, int score) {

        Order order = orderService.findByReceptionNumber(number);

        if (order != null) {
            Expert expert = order.getExpert();
            orderService.updatePricePaid(order, amount);
            orderService.updateStatus(order, OrderStatus.PAID);
            expertService.updateScore(score, expert);
            expertService.updateCredit((0.80 * amount), expert);

        } else
            throw new ObjectEntityNotFoundException(" order is not exit");
    }
}
