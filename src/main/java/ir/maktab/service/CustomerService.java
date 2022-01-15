package ir.maktab.service;

import ir.maktab.data.dao.CustomerDao;
import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.model.enums.SuggestionStatus;
import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.enums.UserStatus;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.order.Suggestion;
import ir.maktab.data.model.user.Customer;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.InValidUserInfoException;
import ir.maktab.exception.ObjectEntityNotFoundException;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service
public class CustomerService {

    private final ModelMapper mapper = new ModelMapper();
    private final CustomerDao customerDao;
    private final SuggestionService suggestService;
    private final OrderService orderService;
    private final ExpertService expertService;
    private final CommentService commentService;

    @Autowired
    public CustomerService(CustomerDao customerDao, @Lazy SuggestionService suggestService,
                           @Lazy OrderService orderService, @Lazy ExpertService expertService,
                           @Lazy CommentService commentService) {

        this.customerDao = customerDao;
        this.suggestService = suggestService;
        this.orderService = orderService;
        this.expertService = expertService;
        this.commentService = commentService;
    }

    public Customer save(Customer customer) {
        if (findByEmail(customer.getEmail()) == null) {
            customer.setUserStatus(UserStatus.WAITING_CONFIRM);
            return customerDao.save(customer);
        } else
            throw new InValidUserInfoException("-- Customer is exit for this email --");
    }

    public CustomerDto createCustomerDto(Customer customer) {

        return mapper.map(customer, CustomerDto.class);
    }

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

    public CustomerDto findCustomer(Customer customer) {
        return createCustomerDto(findByEmail(customer.getEmail()));
    }

    public List<CustomerDto> findByUserStatus(UserStatus status) {

        List<Customer> list = customerDao.findByUserStatus(status);

        String nameStatus = status.name();
        List<CustomerDto> listDto = new ArrayList<>();
        if (list.size() != 0) {
            for (Customer customer : list) {
                listDto.add(createCustomerDto(customer));
            }
            return listDto;
        } else
            throw new ObjectEntityNotFoundException(" --- customer is not by userStatus " + nameStatus + " ---");
    }


    public void deleteCustomer(String email) {
        customerDao.delete(customerDao.findByEmail(email));
    }

    public void changePassword(Customer customer, String newPass) {
        customerDao.updatePassword(customer.getEmail(), newPass);
    }

    public void updateStatus(String email, UserStatus status) {
        customerDao.updateStatus(email, status);
    }

    public void changePhoneNumber(Customer customer, String newPhoneNumber) {

        customerDao.updatePhoneNumber(customer.getEmail(), newPhoneNumber);
    }


    public Customer findByEmail(String email) {
        return customerDao.findByEmail(email);
    }

    public void increaseCredit(Customer customer, double amount) {
        double credit = findByEmail(customer.getEmail()).getCredit();
        customerDao.updateCredit(customer.getEmail(), credit + amount);
    }

    public void decreaseCredit(Customer customer, double amount) {
        double credit = findByEmail(customer.getEmail()).getCredit();
        customerDao.updateCredit(customer.getEmail(), credit - amount);
    }

    public List<CustomerDto> findAll(int first, int count) {
        Page<Customer> pageList = customerDao.findAll(PageRequest.of(first, count));
        List<Customer> list = pageList.toList();
        List<CustomerDto> listDto = new ArrayList<>();
        if (list != null) {
            for (Customer customer : list) {
                listDto.add(createCustomerDto(customer));
            }
            return listDto;
        } else
            throw new ObjectEntityNotFoundException(" --- list of customer is null ---");
    }

    public List<SuggestionDto> selectSuggestion(OrderDto orderDto) {

        List<Suggestion> suggestion = orderDto.getSuggestion().stream()
                .filter(s -> s.getStatus().equals(SuggestionStatus.NEW)).collect(Collectors.toList());
        suggestion.stream().sorted(Comparator.comparing(Suggestion::getProposedPrice))
                .sorted(Comparator.comparing(i -> i.getExpert().getScore())).collect(Collectors.toList());
        List<SuggestionDto> suggestDtoList = new ArrayList<>();
        if (suggestion != null) {
            for (Suggestion suggest : suggestion) {

                SuggestionDto suggestDto = suggestService.createSuggestDto(suggest);
                suggestDtoList.add(suggestDto);
            }
            return suggestDtoList;
        } else
            throw new ObjectEntityNotFoundException("-- list suggest null --");
    }

    public void payment(Customer customer, OrderDto orderDto, double amount, int score, String commentText) {

        Order order = orderService.findByReceptionNumber(orderDto.getReceptionNumber());
        decreaseCredit(customer, amount);
        Expert expert = order.getExpert();
        expertService.updateCredit((0.80 * amount), expert);
        expertService.updateScore(score, expert);
        orderService.updateStatus(order, OrderStatus.PAID);
        orderService.updatePricePaid(order, amount);

        if (commentText != null)
            commentService.createComment(expert, customer, commentText);
    }

    public long totalRecord() {
        return customerDao.count();
    }

}
