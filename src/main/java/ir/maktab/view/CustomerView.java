package ir.maktab.view;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.model.order.Address;
import ir.maktab.data.model.order.Order;
import ir.maktab.data.model.user.Customer;
import ir.maktab.exception.InValidUserInfoException;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.*;
import ir.maktab.validation.ValidationInfo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
@Component
public class CustomerView {
    private final Scanner scanner = new Scanner(System.in);
    private final SuggestionService suggestService;
    private final OrderService orderService;
    private final SubServiceService subServiceService;
    private final CustomerService customerService;
    private final MainView mainView;
    private final ServiceService serviceService;
    private final AddressService addressService;
    private boolean isContinue;
    private String input;

    @Autowired
    public CustomerView(@Lazy SuggestionService suggestService, @Lazy OrderService orderService,
                        @Lazy SubServiceService subServiceService, CustomerService customerService,
                        @Lazy ServiceService serviceService, @Lazy AddressService addressService, @Lazy MainView mainView) {
        this.suggestService = suggestService;
        this.orderService = orderService;
        this.subServiceService = subServiceService;
        this.customerService = customerService;
        this.serviceService = serviceService;
        this.addressService = addressService;
        this.mainView = mainView;
    }


    public void menuCustomer(String email) {
        Customer customer = customerService.findByEmail(email);
        isContinue = false;
        System.out.println("************ Welcome Customer ************");
        do {
            System.out.println("""
                    select Item :
                     1.show Information\s
                    2.register order \s
                    3.change password\s
                     4.change phoneNumber\s
                     5.Increase credit\s
                    6.Select expert\s
                    7.payment\s
                    8.exit""");
            input = scanner.next();
            try {
                ValidationInfo.isValidLogin(input);
                switch (input) {
                    case "1" -> customerService.findCustomer(customer);
                    case "2" -> {
                        customer.getOrderList().add(registerOrder(customer));
                        customerService.save(customer);
                    }
                    case "3" -> {
                        System.out.println(" enter new password :");
                        input = scanner.next();
                        ValidationInfo.isValidPassword(input);
                        customerService.changePassword(customer, input);
                    }
                    case "4" -> {
                        System.out.println(" enter new phoneNumber :");
                        input = scanner.next();
                        ValidationInfo.isValidPhoneNumber(input);
                        customerService.changePhoneNumber(customer, input);
                    }
                    case "5" -> {
                        System.out.println(" enter amount for Increase credit :");
                        input = scanner.next();
                        ValidationInfo.isValidNumeric(input);
                        customerService.increaseCredit(customer, Double.parseDouble(input));
                    }
                    case "6" -> selectExport(customer);
                    case "7" -> findOrderToPayment(customer);
                    case "8" -> mainView.mainMenu();
                }
                isContinue = true;
                break;
            } catch (InValidUserInfoException | ObjectEntityNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

    public Order registerOrder(Customer customer) {
        Order order = null;
        System.out.println("********* Order information entry form ********");
        isContinue = false;
        do {
            try {
                SubServiceDto service = selectService();
                System.out.println("enter ProposedPrice : ");
                input = scanner.next();
                ValidationInfo.isValidNumeric(input);
                double price = Double.parseDouble(input);
                ValidationInfo.isValidProposedPrice(price, service.getPrice());

                System.out.println("enter jobDescription : ");
                input = scanner.next();
                ValidationInfo.isValidCharacter(input);
                String description = input;

                System.out.println("enter date for ir.maktab.service like sample: yyyy/mm/dd");
                input = scanner.next();
                ValidationInfo.isValidDate(input);
                String date = input;

                order = orderService.createOrder(price, description, date, customer, addAddress(), service);
                isContinue = true;
                break;
            } catch (InValidUserInfoException | ParseException | ObjectEntityNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
        return order;
    }

    public Address addAddress() {
        Address address = new Address();
        isContinue = false;
        do {
            try {
                System.out.println("enter Plaque : ");
                input = scanner.next();
                ValidationInfo.isValidNumeric(input);
                int plaque = Integer.parseInt(input);

                System.out.println("enter city : ");
                input = scanner.next();
                ValidationInfo.isValidCharacter(input);
                String city = input;

                System.out.println("enter street : ");
                input = scanner.next();
                ValidationInfo.isValidCharacter(input);
                String street = input;
                address = addressService.createAddress(city, street, plaque);
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
        return address;
    }

    public void addCustomer() {
        String name, family, email, pass, phone, credit;
        System.out.println("********* Customer information entry form ********");
        Customer customer = null;
        isContinue = false;

        do {
            try {
                System.out.println("Enter firstName :");
                name = scanner.next();
                ValidationInfo.isValidCharacter(name);
                System.out.println("Enter lastName :");
                family = scanner.next();
                ValidationInfo.isValidCharacter(family);
                System.out.println("Enter email :");
                email = scanner.next();
                ValidationInfo.isValidEmail(email);
                System.out.println("Enter password :");
                pass = scanner.next();
                ValidationInfo.isValidPassword(pass);
                System.out.println("Enter phoneNumber :");
                phone = scanner.next();
                ValidationInfo.isValidPhoneNumber(phone);
                System.out.println("Enter amount for wallet :");
                credit = scanner.next();
                ValidationInfo.isValidNumeric(credit);
                customer = customerService.createCustomer(name, family, email, pass, phone, Double.parseDouble(credit));
                System.out.println("Do you want to register an order? \n1.yes \n2.no");
                input = scanner.next();
                ValidationInfo.isValidSelected(input);

                if (input.equals("1"))
                    registerOrder(customer);
                customer.getOrderList().add(registerOrder(customer));

                isContinue = true;
                break;
            } catch (InValidUserInfoException | ObjectEntityNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);

        customerService.save(customer);
    }

    public void selectExport(Customer customer) {
        List<OrderDto> orderList = new ArrayList<>();
        int count = 1;
        isContinue = false;
        try {
            orderList = orderService.findOrderToSelectExpert(customer);
            orderList.forEach(System.out::println);
        } catch (ObjectEntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
        do {
            try {
                System.out.println("enter receptionNumber");
                input = scanner.next();
                ValidationInfo.isValidNumeric(input);
                OrderDto orderDto = orderList.stream().filter(o -> o.getReceptionNumber() == Long.parseLong(input)).findAny().orElse(null);
                if (orderDto != null) {
                    List<SuggestionDto> list = customerService.selectSuggestion(orderDto);
                    for (SuggestionDto suggest : list) {
                        System.out.println(count++ + " : " + suggest);
                    }
                    System.out.println("number Of Suggest :");
                    input = scanner.next();
                    ValidationInfo.isValidNumeric(input);
                    ValidationInfo.isValidSelect(count, Integer.parseInt(input));
                    suggestService.update(Integer.parseInt(input), list);
                } else
                    System.out.println("-- order is not for receptionNumber --");
                isContinue = true;
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

    public void findOrderToPayment(Customer customer) {
        List<OrderDto> orderList = new ArrayList<>();

        isContinue = false;
        try {
            orderList = orderService.findOrderToPayment(customer);
            orderList.forEach(System.out::println);
        } catch (ObjectEntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
        do {
            try {
                System.out.println("enter receptionNumber");
                input = scanner.next();
                ValidationInfo.isValidNumeric(input);

                OrderDto orderDto = orderList.stream().filter(o -> o.getReceptionNumber() == Long.parseLong(input))
                        .findAny().orElse(null);

                payment(customer, orderDto);

            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

    public void payment(Customer customer, OrderDto orderDto) {
        int score;
        isContinue = false;
        do {
            System.out.println("enter amount for payment");
            input = scanner.next();
            try {
                ValidationInfo.isValidNumeric(input);
                if (customer.getCredit() >= Double.parseDouble(input)) {
                    score = getScore();
                    String commentText = getComment();
                    customerService.payment(customer, orderDto, Double.parseDouble(input), score, commentText);
                } else
                    System.out.println(" credit is not enough :");
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

    public int getScore() {
        isContinue = true;
        do {
            try {
                System.out.println("enter score to expert 1-10 :");
                input = scanner.next();
                ValidationInfo.isValidScore(input);
                isContinue = true;
                break;

            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
        return Integer.parseInt(input);
    }

    public String getComment() {
        isContinue = true;
        do {
            System.out.println("Do you want to comment ? \n1.yes \n2.no");
            input = scanner.next();
            try {
                ValidationInfo.isValidSelected(input);
                if (input.equals("1")) {
                    System.out.println("enter comment text :");
                    input = scanner.next();
                    ValidationInfo.isValidCharacter(input);
                } else
                    input = null;
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
        return input;
    }

    public SubServiceDto selectService() {
        isContinue = false;
        SubServiceDto subServiceDto = new SubServiceDto();
        List<SubServiceDto> list = subServiceService.findAll();
        list.forEach(System.out::println);
        do {
            System.out.println("enter name of ir.maktab.service :");
            input = scanner.next();
            try {

                ValidationInfo.isValidCharacter(input);
                subServiceDto = list.stream().filter(s -> s.getName().equals(input.toLowerCase()))
                        .findAny().orElse(null);
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);

        return subServiceDto;
    }
}