package ir.maktab.view;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.model.serviceSystem.Service;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.Manager;
import ir.maktab.exception.InValidUserInfoException;
import ir.maktab.exception.IsNullObjectException;
import ir.maktab.service.*;
import ir.maktab.validation.ValidationInfo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Getter
@Component
public class ManagerView {
    private final Scanner scanner = new Scanner(System.in);
    private final SuggestionService suggestService;
    private final OrderService orderService;
    private final ExpertView expertView;
    private final SubServiceService subServiceService;
    private final CustomerService customerService;
    private final MainView mainView;
    private final ManagerService managerService;
    private final ServiceService serviceService;
    private final AddressService addressService;
    private final ExpertService expertService;
    private final UserService userService;
    private boolean isContinue;
    private String input;

    @Autowired
    public ManagerView(@Lazy SuggestionService suggestService, @Lazy OrderService orderService,
                       @Lazy SubServiceService subServiceService, @Lazy CustomerService customerService,
                       ManagerService managerService, @Lazy ServiceService serviceService, @Lazy AddressService addressService,
                       @Lazy ExpertService expertService, @Lazy ExpertView expertView, @Lazy MainView mainView, @Lazy UserService userService) {
        this.suggestService = suggestService;
        this.orderService = orderService;
        this.subServiceService = subServiceService;
        this.customerService = customerService;
        this.managerService = managerService;
        this.serviceService = serviceService;
        this.addressService = addressService;
        this.expertService = expertService;
        this.mainView = mainView;
        this.userService = userService;
        this.expertView = expertView;
    }


    public void loginManager() {
        isContinue = false;
        String pass;
        do {
            try {
                System.out.println("enter username");
                input = scanner.next();
                ValidationInfo.isValidPassword(input);
                System.out.println("enter password");
                pass = scanner.next();
                ValidationInfo.isValidPassword(pass);
                Manager manager = managerService.createManager(input, pass);
                if (managerService.checkManager(manager) != null) {
                    isContinue = true;
                    managerMenu();
                    break;
                }
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);

    }

    public void managerMenu() {

        isContinue = false;
        System.out.println("************ Welcome Manager ************");
        do {
            System.out.println("""
                    select Item :
                     1.show list Expert\s
                    2.Show list Customer \s
                    3.change userNAme & password\s
                     4.Confirm customer\s
                     5.add Service\s
                    6.show order\s
                    7.addExpert\s
                    8.deleted\s
                    9.Get paid
                    10.exit""");
            input = scanner.next();
            try {
                ValidationInfo.isValidLogin(input);
                switch (input) {
                    case "1" -> showExpert();
                    case "2" -> showCustomer();
                    case "3" -> {
                        System.out.println(" enter new username :");
                        input = scanner.next();
                        ValidationInfo.isValidPassword(input);
                        System.out.println(" enter new password :");
                        String pass = scanner.next();
                        ValidationInfo.isValidPassword(pass);
                        managerService.Save(managerService.createManager(input, pass));
                    }
                    case "4" -> managerService.customerConfirmation();
                    case "5" -> addServiceSystem();
                    case "6" -> showOrder();
                    case "7" -> expertView.addExpert();
                    case "8" -> deleted();
                    case "9" -> getPaid();
                    case "10" -> mainView.mainMenu();
                }
                isContinue = true;
                break;

            } catch (InValidUserInfoException | IsNullObjectException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

    public void showExpert() {
        int first = 0, number, pageNumber;
        long total;
        double remaining;
        System.out.println("Enter the number of records per page : ");
        input = scanner.next();
        try {
            ValidationInfo.isValidNumeric(input);
            number = Integer.parseInt(input);
            total = expertService.totalRecord();
            if (total >= number && number > 1) {
                remaining = total % number;
                pageNumber = remaining == 0 ? (int) total / number : (int) (total / number) + 1;
                for (int i = 0; i < pageNumber; i++) {
                    List<ExpertDto> list = expertService.findAll(first, number);
                    list.forEach(System.out::println);
                    System.out.println("select show page \n1.next \n2.previous");
                    input = scanner.next();
                    ValidationInfo.isValidSelected(input);
                    if (input.equals("1")) {
                        if (i == (pageNumber - 1))
                            System.out.println(" next page is not exit");
                        else
                            first = first + number;
                    } else if (input.equals("2")) {
                        if (i == 0)
                            System.out.println("previous page is not exit");
                    } else
                        first = first - number;
                }
            } else if (total <= number || number == 1) {
                List<ExpertDto> list = expertService.findAll(0, (int) total);
                list.forEach(System.out::println);
            }
            isContinue = true;
        } catch (InValidUserInfoException | IsNullObjectException e) {
            System.out.println(e.getMessage());
        }

    }

    public void showCustomer() {
        int first = 0, number, pageNumber;
        long total;
        double remaining;

        System.out.println("Enter the number of records per page : ");
        input = scanner.next();
        try {
            ValidationInfo.isValidNumeric(input);
            number = Integer.parseInt(input);
            total = customerService.totalRecord();
            if (total >= number && number > 1) {
                remaining = total % number;
                pageNumber = remaining == 0 ? (int) total / number : (int) (total / number) + 1;
                for (int i = 0; i < pageNumber; i++) {
                    List<CustomerDto> list = customerService.findAll(first, number);
                    list.forEach(System.out::println);
                    System.out.println("select show page \n1.next \n2.previous");
                    input = scanner.next();
                    ValidationInfo.isValidSelected(input);
                    if (input.equals("1")) {
                        if (i == (pageNumber - 1))
                            System.out.println(" next page is not exit");
                        else
                            first = first + number;
                    } else if (input.equals("2")) {
                        if (i == 0)
                            System.out.println("previous page is not exit");
                    } else
                        first = first - number;
                }
            } else if (total <= number || number == 1) {
                List<CustomerDto> list = customerService.findAll(0, (int) total);
                list.forEach(System.out::println);
            }
            isContinue = true;
        } catch (IsNullObjectException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addServiceSystem() {
        isContinue = false;
        do {
            System.out.println(" select Item \n1.addService \n2.addSubService");
            input = scanner.next();
            try {
                ValidationInfo.isValidSelected(input);
                switch (input) {
                    case "1" -> addService();
                    case "2" -> {
                        System.out.println("enter name of Service");
                        input = scanner.next();
                        ValidationInfo.isValidCharacter(input);
                        addSubService(input);
                    }
                }
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

    public void addService() {

        System.out.println("********* Service information entry form ********");
        isContinue = false;
        String name;
        do {
            System.out.println("Enter  name of ir.maktab.service :");
            name = scanner.next();
            try {
                ValidationInfo.isValidCharacter(name);
                serviceService.save(serviceService.createService(name));
                System.out.println(" do you wanted add SubService for  this ir.maktab.service :\n1.yes \n2.no");
                input = scanner.next();
                ValidationInfo.isValidSelected(input);
                if (input.equals("1"))
                    addSubService(name);
                isContinue = true;
                break;

            } catch (InValidUserInfoException | IsNullObjectException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

    public void addSubService(String name) {
        String description, price;
        System.out.println("********* subService information entry form ********");
        isContinue = false;
        Service service = serviceService.findByName(name);
        if (service != null) {
            do {
                System.out.println("Enter  name of subService :");
                input = scanner.next();
                try {
                    ValidationInfo.isValidCharacter(input);
                    System.out.println("Enter description of subService :");
                    description = scanner.next();
                    ValidationInfo.isValidCharacter(description);

                    System.out.println("Enter price of subService :");
                    price = scanner.next();
                    ValidationInfo.isValidNumeric(price);

                    SubService subService = subServiceService.createSubService(input, description, Double.parseDouble(price));
                    subServiceService.save(subService);
                    service.getSubServiceList().add(subService);
                    serviceService.update(service);

                    isContinue = true;
                    break;

                } catch (InValidUserInfoException | IsNullObjectException e) {
                    System.out.println(e.getMessage());
                }
            } while (isContinue);
        } else
            System.out.println("--- ir.maktab.service not exit ---");
    }

    public void showOrder() {
        try {
            List<OrderDto> list = orderService.findAll();
            list.forEach(System.out::println);
        } catch (IsNullObjectException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleted() {
        isContinue = false;
        System.out.println("select item for delete \n1.customer\n2.expert\n3.service \n4.subService ");
        input = scanner.next();
        do {
            try {
                ValidationInfo.isValidNumeric(input);
                switch (input) {
                    case "1" -> {
                        System.out.println("enter email of customer");
                        input = scanner.next();
                        ValidationInfo.isValidEmail(input);
                        customerService.deleteCustomer(input);
                    }
                    case "2" -> {
                        System.out.println(" enter email of expert");
                        input = scanner.next();
                        ValidationInfo.isValidEmail(input);
                        expertService.deleteExpert(input);
                    }
                    case "3" -> {
                        System.out.println(" enter name of ir.maktab.service");
                        input = scanner.next();
                        ValidationInfo.isValidCharacter(input);
                        serviceService.deleteService(input);
                    }
                    case "4" -> {
                        System.out.println(" enter name of subService");
                        input = scanner.next();
                        ValidationInfo.isValidCharacter(input);
                        subServiceService.deleteSubService(input);
                    }
                }
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

    public void getPaid() {

        isContinue = false;
        do {
            System.out.println("enter receptionNumber of order : ");
            input = scanner.next();
            try {
                ValidationInfo.isValidNumeric(input);
                int receptionNumber = Integer.parseInt(input);
                System.out.println("enter score to expert 1-10:");
                input = scanner.next();
                ValidationInfo.isValidScore(input);
                int score = Integer.parseInt(input);
                System.out.println("enter amount to pay:");
                input = scanner.next();
                ValidationInfo.isValidNumeric(input);
                double amount = Double.parseDouble(input);
                managerService.getPaid(receptionNumber, amount, score);
                isContinue = true;
                break;
            } catch (InValidUserInfoException | IsNullObjectException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

}
