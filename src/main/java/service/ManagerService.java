package service;

import config.SpringConfig;
import data.dao.ManagerDao;
import data.dto.CustomerDto;
import data.model.enums.OrderStatus;
import data.model.enums.UserStatus;
import data.model.order.Order;
import data.model.user.Customer;
import data.model.user.Expert;
import data.model.user.Manager;
import exception.IsNullObjectException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService extends BaseService{
    ManagerDao managerDao;


    public ManagerService(ManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    public Manager createManager(String userName, String pass) {
        Manager manager = Manager.builder()
                .username(userName)
                .password(pass).build();
        if (manager != null)
            throw new IsNullObjectException("-- Manager is null --");
        return manager;
    }

    public void Save(Manager manager) {
        if (!checkManager(manager)) {
            managerDao.save(manager);
        }
        throw new IsNullObjectException("--- manager is exit ----");
    }

    public boolean checkManager(Manager manager) {
        return managerDao.exists(manager);
    }


    public void customerConfirmation() {
        List<CustomerDto> byStatus = customerService.findByUserStatus(UserStatus.WAITING_CONFIRM);
        List<String> customerListEmail = new ArrayList<>();
        for (CustomerDto customer : byStatus) {
            if (customer.getDateRegister().compareTo(customer.getDateUpdate()) == -1)
                customerListEmail.add(customer.getEmail());
        }
        for (String email : customerListEmail) {
            Customer customer = customerService.findByEmail(email);
            customer.setUserStatus(UserStatus.CONFIRMED);
            customerService.save(customer);
        }
    }

    public void getPaid(int number,double amount) {
        Order order = orderService.findByReceptionNumber(number);

        if(order!=null){
            Expert expert = order.getExpert();
                order.setStatus(OrderStatus.PAID);
              order.setPricePaid(amount);
              expert.setCredit(expert.getCredit()+(0.80*amount));
                expertService.save(expert);
            orderService.save(order);
        }else
            throw new IsNullObjectException(" order is not exit");
    }
}
