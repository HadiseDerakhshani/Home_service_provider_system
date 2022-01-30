package ir.maktab.service.implemention;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.entity.enums.UserRole;
import ir.maktab.data.entity.enums.UserStatus;
import ir.maktab.data.entity.order.Order;
import ir.maktab.data.entity.user.Customer;
import ir.maktab.data.entity.user.Expert;
import ir.maktab.data.mapping.CustomerMap;
import ir.maktab.data.mapping.OrderMap;
import ir.maktab.data.repasitory.CustomerRepository;
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
import java.util.stream.Collectors;

@Getter
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final SuggestionServiceImpl suggestServiceImpl;

    private final OrderServiceImpl orderServiceImpl;

    private final ExpertServiceImpl expertServiceImpl;

    private final CommentServiceImpl commentServiceImpl;

    private final CustomerMap customerMap;
    private final OrderMap orderMap;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, @Lazy SuggestionServiceImpl suggestServiceImpl,
                               @Lazy OrderServiceImpl orderServiceImpl, @Lazy ExpertServiceImpl expertServiceImpl,
                               @Lazy CommentServiceImpl commentServiceImpl, @Lazy OrderMap orderMap, @Lazy CustomerMap customerMap) {
        this.customerRepository = customerRepository;
        this.suggestServiceImpl = suggestServiceImpl;
        this.orderServiceImpl = orderServiceImpl;
        this.expertServiceImpl = expertServiceImpl;
        this.commentServiceImpl = commentServiceImpl;
        this.customerMap = customerMap;
        this.orderMap = orderMap;
    }

    @Override
    public Customer save(CustomerDto customerDto) {
        if (!findByEmail(customerDto.getEmail()).isPresent()) {
            Customer customer = customerMap.createCustomer(customerDto);
            customer.setUserStatus(UserStatus.WAITING_CONFIRM);
            customer.setUserRole(UserRole.CUSTOMER);
            return customerRepository.save(customer);
        } else
            throw new InValidUserInfoException("-- Customer is exit for this email --");
    }


    @Override
    public List<CustomerDto> findByUserStatus(UserStatus status) {

        List<Customer> list = customerRepository.findByUserStatus(status);

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
        customerRepository.delete(customerRepository.findByEmail(email).get());
    }

    @Override
    public void changePassword(CustomerDto customer, String newPass) {
        Customer customerFound = findByEmail(customer.getEmail()).get();
        customerFound.setPassword(newPass);
        customerRepository.save(customerFound);

    }

    @Override
    public void updateOrder(CustomerDto customerDto, OrderDto orderDto) {
        Customer customerFound = findByEmail(customerDto.getEmail()).get();
        customerDto.getOrderList().add(orderDto);
        customerFound.setOrderList(customerDto.getOrderList().stream().map(orderMap::createOrder)
                .collect(Collectors.toList()));
        customerRepository.save(customerFound);
    }

    @Override
    public void updateStatus(String email, UserStatus status) {
        Customer customerFound = findByEmail(email).get();
        customerFound.setUserStatus(status);
        customerRepository.save(customerFound);

    }

    @Override
    public void changePhoneNumber(CustomerDto customer, String newPhoneNumber) {
        Customer customerFound = findByEmail(customer.getEmail()).get();
        customerFound.setPhoneNumber(newPhoneNumber);
        customerRepository.save(customerFound);

    }

    @Override
    public Optional<Customer> findByEmail(String email) {

        return customerRepository.findByEmail(email);

    }

    @Override
    public void increaseCredit(Customer customer, double amount) {
        Customer customerFound = findByEmail(customer.getEmail()).get();
        double credit = customerFound.getCredit();
        customerFound.setCredit(credit + amount);
        customerRepository.save(customerFound);
    }

    @Override
    public void decreaseCredit(CustomerDto customer, double amount) {
        Customer customerFound = findByEmail(customer.getEmail()).get();
        double credit = customerFound.getCredit();
        customerFound.setCredit(credit - amount);
        customerRepository.save(customerFound);
    }

    @Override
    public List<CustomerDto> findAll(int pageNumber, int pageSize) {
        Page<Customer> pageList = customerRepository.findAll(PageRequest.of(pageNumber, pageSize));
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
        //   orderServiceImpl.updateStatus(order, OrderStatus.PAID);
        orderServiceImpl.updatePricePaid(order, amount);

        if (commentText != null)
            commentServiceImpl.createComment(expert, customerMap.createCustomer(customerDto), commentText);
    }

    @Override
    public long totalRecord() {
        return customerRepository.count();
    }

}
