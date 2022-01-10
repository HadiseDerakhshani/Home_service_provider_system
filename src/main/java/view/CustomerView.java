package view;

import data.dto.OrderDto;
import data.dto.SuggestionDto;
import data.model.order.Address;
import data.model.order.Order;
import data.model.user.Customer;
import exception.InValidUserInfoException;
import exception.IsNullObjectException;
import org.springframework.stereotype.Service;
import validation.ValidationInfo;

import java.text.ParseException;
import java.util.List;


@Service
public class CustomerView extends BaseView {
    public void menuCustomer(String email) {
        Customer customer = customerService.findByEmail(email);
        isContinue = false;
        System.out.println("************ Welcome Customer ************");
        do {
            System.out.println("select Item :\n 1.show Information \n2.register order " +
                    " \n3.change password \n 4.change phoneNumber \n 5.Increase credit \n6. Select expert \n" +
                    "7.payment \n8.show suggestion list \n 9.exit");
            input = scanner.next();
            try {
                ValidationInfo.isValidLogin(input);
                switch (input) {
                    case "1":
                        customerService.findCustomer(customer);
                        break;
                    case "2":
                        customer.getOrderList().add(registerOrder(customer));
                        customerService.save(customer);
                        break;
                    case "3":
                        System.out.println(" enter new password :");
                        input = scanner.next();
                        ValidationInfo.isValidPassword(input);
                        customerService.changePassword(customer, input);
                        break;
                    case "4":
                        System.out.println(" enter new phoneNumber :");
                        input = scanner.next();
                        ValidationInfo.isValidPhoneNumber(input);
                        customerService.changePhoneNumber(customer, input);
                        break;
                    case "5":
                        System.out.println(" enter amount for Increase credit :");
                        input = scanner.next();
                        ValidationInfo.isValidNumeric(input);
                        customerService.IncreaseCredit(customer, Double.parseDouble(input));
                        break;
                    case "6":
                        selectExport(customer);
                        break;
                    case "7":
                        break;
                    case "8":
                        break;
                    case "9":
                        mainMenu();
                        break;
                }
                isContinue = true;
                break;
            } catch (InValidUserInfoException | IsNullObjectException e) {
                e.getMessage();
            }
        } while (isContinue);
    }

    public Order registerOrder(Customer customer) {
        Order order = null;
        System.out.println("********* Order information entry form ********");
        isContinue = false;
        do {
            try {
                System.out.println("enter ProposedPrice : ");
                input = scanner.next();
                ValidationInfo.isValidNumeric(input);
                double price = Double.parseDouble(input);

                System.out.println("enter jobDescription : ");
                input = scanner.next();
                ValidationInfo.isValidCharacter(input);
                String description = input;

                System.out.println("enter date for service like sample: yyyy/mm/dd");
                input = scanner.next();
                ValidationInfo.isValidDate(input);
                String date = input;

                order = orderService.createOrder(price, description, date, customer, addAddress());
                isContinue = true;
                break;
            } catch (InValidUserInfoException | ParseException e) {
                e.getMessage();
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
                e.getMessage();
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
            } catch (InValidUserInfoException e) {
                e.getMessage();
            }
        } while (isContinue);

        customerService.save(customer);
    }

    public void selectExport(Customer customer) {
        List<OrderDto> orderList = null;
        int count = 1;
        isContinue = false;
        try {
            orderList = orderService.findOrderToSelectExpert(customer);
            orderList.forEach(System.out::println);
        } catch (IsNullObjectException e) {
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
                    suggestionService.updateStatus(Integer.parseInt(input), list);
                } else
                    System.out.println("-- order is not for receptionNumber --");
                isContinue = true;
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }
}
