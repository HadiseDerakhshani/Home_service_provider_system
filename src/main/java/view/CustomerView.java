package view;

import data.model.order.Address;
import data.model.order.Order;
import data.model.user.Customer;
import exception.InValidUserInfoException;
import exception.IsNullObjectException;
import org.springframework.stereotype.Service;
import validation.ValidationInfo;

import java.text.ParseException;


@Service
public class CustomerView extends BaseView {
    public void menuCustomer(String email) {
        Customer customer = customerService.findByEmail(email);
        isContinue = false;
        System.out.println("************ Welcome Customer ************");
        do {
            System.out.println("select Item :\n 1.show Information \n2.register order " +
                    " \n3.change password \n 4.change phoneNumber \n 5.Increase credit \n6.exit");
            info = scanner.next();
            try {
                ValidationInfo.isValidLogin(info);

                switch (info) {
                    case "1":
                        customerService.findCustomer(customer);
                        break;
                    case "2":
                        customer.getOrderList().add(registerOrder(customer));
                        customerService.save(customer);
                        break;
                    case "3":
                        System.out.println(" enter new password :");
                        info = scanner.next();
                        ValidationInfo.isValidPassword(info);
                        customerService.changePassword(customer, info);
                        break;
                    case "4":
                        System.out.println(" enter new phoneNumber :");
                        info = scanner.next();
                        ValidationInfo.isValidPhoneNumber(info);
                        customerService.changePhoneNumber(customer, info);
                        break;
                    case "5":
                        System.out.println(" enter amount for Increase credit :");
                        info = scanner.next();
                        ValidationInfo.isValidNumeric(info);
                        customerService.IncreaseCredit(customer, Double.parseDouble(info));
                        break;
                    case "6":
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
                info = scanner.next();
                ValidationInfo.isValidNumeric(info);
                double price = Double.parseDouble(info);

                System.out.println("enter jobDescription : ");
                info = scanner.next();
                ValidationInfo.isValidCharacter(info);
                String description = info;

                System.out.println("enter date for service like sample: yyyy/mm/dd");
                info = scanner.next();
                ValidationInfo.isValidDate(info);
                String date = info;

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
                info = scanner.next();
                ValidationInfo.isValidNumeric(info);
                int plaque = Integer.parseInt(info);

                System.out.println("enter city : ");
                info = scanner.next();
                ValidationInfo.isValidCharacter(info);
                String city = info;

                System.out.println("enter street : ");
                info = scanner.next();
                ValidationInfo.isValidCharacter(info);
                String street = info;
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
                info = scanner.next();
                ValidationInfo.isValidRequestOrder(info);

                if (info.equals("1"))
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

}
