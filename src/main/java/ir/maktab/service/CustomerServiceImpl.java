package ir.maktab.service;

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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    @Lazy
    private final SuggestionService suggestService;
    @Lazy
    private final OrderService orderService;
    @Lazy
    private final ExpertService expertService;
    @Lazy
    private final CommentService commentService;
    private final CustomerMap customerMap;

    @Override
    public Customer save(Customer customer) {
        if (findByEmail(customer.getEmail()) == null) {
            customer.setUserStatus(UserStatus.WAITING_CONFIRM);
            return customerDao.save(customer);
        } else
            throw new InValidUserInfoException("-- Customer is exit for this email --");
    }

    @Override
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

    }

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
        customerDao.updatePassword(customer.getEmail(), newPass);
    }

    @Override
    public void updateStatus(String email, UserStatus status) {
        customerDao.updateStatus(email, status);
    }

    @Override
    public void changePhoneNumber(CustomerDto customer, String newPhoneNumber) {

        customerDao.updatePhoneNumber(customer.getEmail(), newPhoneNumber);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerDao.findByEmail(email).get();
    }

    @Override
    public void increaseCredit(Customer customer, double amount) {
        double credit = findByEmail(customer.getEmail()).getCredit();
        customerDao.updateCredit(customer.getEmail(), credit + amount);
    }

    @Override
    public void decreaseCredit(CustomerDto customer, double amount) {
        double credit = findByEmail(customer.getEmail()).getCredit();
        customerDao.updateCredit(customer.getEmail(), credit - amount);
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

        Order order = orderService.findByReceptionNumber(orderDto.getReceptionNumber());
        decreaseCredit(customerDto, amount);
        Expert expert = order.getExpert();
        expertService.updateCredit((0.80 * amount), expert);
        expertService.updateScore(score, expert);
        orderService.updateStatus(order, OrderStatus.PAID);
        orderService.updatePricePaid(order, amount);

        if (commentText != null)
            commentService.createComment(expert, customerMap.createCustomer(customerDto), commentText);
    }

    @Override
    public long totalRecord() {
        return customerDao.count();
    }

}
