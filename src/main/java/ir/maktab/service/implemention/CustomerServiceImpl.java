package ir.maktab.service.implemention;

import ir.maktab.data.dao.CustomerDao;
import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.mapping.CustomerMap;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.user.Customer;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.InValidUserInfoException;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.CustomerService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    private final SuggestionServiceImpl suggestServiceImpl;

    private final OrderServiceImpl orderServiceImpl;

    private final ExpertServiceImpl expertServiceImpl;

    private final CommentServiceImpl commentServiceImpl;

    private final CustomerMap customerMap;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao, @Lazy SuggestionServiceImpl suggestServiceImpl,
                               @Lazy OrderServiceImpl orderServiceImpl, @Lazy ExpertServiceImpl expertServiceImpl,
                               @Lazy CommentServiceImpl commentServiceImpl, @Lazy CustomerMap customerMap) {
        this.customerDao = customerDao;
        this.suggestServiceImpl = suggestServiceImpl;
        this.orderServiceImpl = orderServiceImpl;
        this.expertServiceImpl = expertServiceImpl;
        this.commentServiceImpl = commentServiceImpl;
        this.customerMap = customerMap;
    }

    @Override
    public Customer save(CustomerDto customerDto) {
      if(!findByEmail(customerDto.getEmail()).isPresent() ){
            Customer customer = customerMap.createCustomer(customerDto);
            customer.setUserStatus(UserStatus.WAITING_CONFIRM);
            customer.setUserRole(UserRole.CUSTOMER);
           // customer.setOrderList(customerDto.getOrderList());
            return customerDao.save(customer);
        } else
            throw new InValidUserInfoException("-- Customer is exit for this email --");
    }

   /* @Override
    public Customer createCustomer(String name, String family, String email, String pass, String phone, double credit) {
        if (email == null || email.equals(""))
            throw new ObjectEntityNotFoundException("-- email is empty --");
        else {
            Customer customer = Customer.builder()
                    .firstName(name)
                    .lastName(family)
                    .email(email)
                    .password(pass)
                    .phoneNumber(phone)
                    .userStatus(UserStatus.NEW)
                    .userRole(UserRole.CUSTOMER)
                    .credit(credit)
                    .build();
            return customer;
        }

    }*/

    @Override
    public List<CustomerDto> findByUserStatus(UserStatus status) {

        List<Customer> list = customerDao.findByUserStatus(status);

        String nameStatus = status.name();
        List<CustomerDto> listDto = new ArrayList<>();
        if (list.size() != 0) {
            for (Customer customer : list) {
                listDto.add(customerMap.createCustomerDto(customer));
            }
            return listDto;
        } else
            throw new ObjectEntityNotFoundException(" --- customer is not by userStatus " + nameStatus + " ---");
    }

    @Override
    public void deleteCustomer(String email) {
        customerDao.delete(customerDao.findByEmail(email).get());
    }

    @Override
    public void changePassword(CustomerDto customer, String newPass) {
        Customer customerFound = findByEmail(customer.getEmail()).get();
        customerFound.setPassword(newPass);
        customerDao.save(customerFound);

    }

    @Override
    public void updateStatus(String email, UserStatus status) {
        Customer customerFound = findByEmail(email).get();
        customerFound.setUserStatus(status);
        customerDao.save(customerFound);

    }

    @Override
    public void changePhoneNumber(CustomerDto customer, String newPhoneNumber) {
        Customer customerFound = findByEmail(customer.getEmail()).get();
        customerFound.setPhoneNumber(newPhoneNumber);
        customerDao.save(customerFound);

    }

    @Override
    public Optional<Customer> findByEmail(String email) {

        return customerDao.findByEmail(email);

    }

    @Override
    public void increaseCredit(Customer customer, double amount) {
        Customer customerFound = findByEmail(customer.getEmail()).get();
        double credit = customerFound.getCredit();
        customerFound.setCredit(credit + amount);
        customerDao.save(customerFound);
    }

    @Override
    public void decreaseCredit(CustomerDto customer, double amount) {
        Customer customerFound = findByEmail(customer.getEmail()).get();
        double credit = customerFound.getCredit();
        customerFound.setCredit(credit - amount);
        customerDao.save(customerFound);
    }

    @Override
    public List<CustomerDto> findAll(int pageNumber, int pageSize) {
        Page<Customer> pageList = customerDao.findAll(PageRequest.of(pageNumber, pageSize));
        List<Customer> list = pageList.toList();
        List<CustomerDto> listDto = new ArrayList<>();
        if (list != null) {
            for (Customer customer : list) {
                listDto.add(customerMap.createCustomerDto(customer));
            }
            return listDto;
        } else
            throw new ObjectEntityNotFoundException(" --- list of customer is null ---");
    }

    @Override
    public List<SuggestionDto> selectSuggestion(OrderDto orderDto) {

      /*  List<Suggestion> suggestion = orderDto.getSuggestion().stream()
                .filter(s -> s.getStatus().equals(SuggestionStatus.NEW)).collect(Collectors.toList());
        suggestion.stream().sorted(Comparator.comparing(Suggestion::getProposedPrice))
                .sorted(Comparator.comparing(i -> i.getExpert().getScore())).collect(Collectors.toList());
      */

      /*  List<SuggestionDto> suggestDtoList = new ArrayList<>();
        if (suggestion != null) {
            for (Suggestion suggest : suggestion) {
                SuggestionDto suggestDto = suggestService.createSuggestDto(suggest);
                suggestDtoList.add(suggestDto);
            }
            return suggestDtoList;
        } else
            throw new ObjectEntityNotFoundException("-- list suggest null --");*/
        return null;//
    }

    @Override
    public void payment(CustomerDto customerDto, OrderDto orderDto, double amount, int score, String commentText) {

        Order order = orderServiceImpl.findByReceptionNumber(orderDto.getReceptionNumber());
        decreaseCredit(customerDto, amount);
        Expert expert = order.getExpert();
        expertServiceImpl.updateCredit((0.80 * amount), expert);
        expertServiceImpl.updateScore(score, expert);
        orderServiceImpl.updateStatus(order, OrderStatus.PAID);
        orderServiceImpl.updatePricePaid(order, amount);

        if (commentText != null)
            commentServiceImpl.createComment(expert, customerMap.createCustomer(customerDto), commentText);
    }

    @Override
    public long totalRecord() {
        return customerDao.count();
    }

}
