package ir.maktab.view;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.model.order.Suggestion;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.InValidUserInfoException;
import ir.maktab.exception.ObjectEntityNotFoundException;
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

public class ExpertView {
    private final Scanner scanner = new Scanner(System.in);
    private final SuggestionService suggestService;
    private final OrderService orderService;
    private final ExpertService expertService;
    private final MainView mainView;
    private final SubServiceService subServiceService;
    private final AddressService addressService;
    private boolean isContinue;
    private String input;

    @Autowired
    public ExpertView(SuggestionService suggestService, @Lazy OrderService orderService, @Lazy ExpertService expertService,
                      @Lazy SubServiceService subServiceService, @Lazy AddressService addressService,
                      @Lazy MainView mainView) {
        this.suggestService = suggestService;
        this.orderService = orderService;
        this.expertService = expertService;
        this.subServiceService = subServiceService;
        this.addressService = addressService;
        this.mainView = mainView;
    }


    public void addExpert() {
        String name, family, email, pass, phone, credit, score, image;
        System.out.println("********* Expert information entry form ********");
        Expert expert = null;
        isContinue = false;
        String info;
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
                System.out.println("Enter score:");
                score = scanner.next();
                ValidationInfo.isValidNumeric(score);
                System.out.println("Enter image file address :");
                image = scanner.next();
                ValidationInfo.isValidNumeric(image);
                expert = expertService.createExpert(name, family, email, pass, phone, Double.parseDouble(credit),
                        Integer.parseInt(score), image);
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);

        expert.setServiceList(addSubService(expert));
        expertService.save(expert);
    }

    public void menuExpert(String email) {
        Expert expert = expertService.findByEmail(email);
        isContinue = false;
        System.out.println("************ Welcome Expert ************");
        do {
            System.out.println("""
                    select Item :
                     1.show Expert Information\s
                    2.register suggestion \s
                    3.change password\s
                     4.change phoneNumber\s
                     5.add subService\s
                    6.start order
                    7.end order
                    8.exit""");
            input = scanner.next();
            try {
                ValidationInfo.isValidLogin(input);

                switch (input) {
                    case "1" -> expertService.findExpert(expert);
                    case "2" -> giveSuggestion(expert);
                    case "3" -> {
                        System.out.println(" enter new password :");
                        input = scanner.next();
                        ValidationInfo.isValidPassword(input);
                        expertService.changePassword(expert, input);
                    }
                    case "4" -> {
                        System.out.println(" enter new phoneNumber :");
                        input = scanner.next();
                        ValidationInfo.isValidPhoneNumber(input);
                        expertService.changePhoneNumber(expert, input);
                    }
                    case "5" -> {
                        List<SubService> list = addSubService(expert);
                        expertService.updateServiceList(list, expert);
                    }
                    case "6" -> startAndEndOrder(6, expert);
                    case "7" -> startAndEndOrder(7, expert);
                    case "8" -> mainView.mainMenu();
                }
                isContinue = true;
                break;
            } catch (InValidUserInfoException | ObjectEntityNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }


    public List<SubService> addSubService(Expert expert) {
        isContinue = false;
        List<SubService> list = null;
        try {
            List<SubServiceDto> subServiceDtoList = subServiceService.findAll();
            int count = 1;
            for (SubServiceDto subService : subServiceDtoList) {
                System.out.print(count++ + " : " + subService);
            }
            do {
                System.out.println("enter the number of ir.maktab.service for work :");
                input = scanner.next();
                try {
                    ValidationInfo.isValidNumeric(input);
                    int index = Integer.parseInt(input);
                    ValidationInfo.isValidSelect(count, index);
                    list = expertService.addSubServiceExpert(expert, subServiceDtoList, index);
                    subServiceService.addExpertToList(expert, list.get(list.size() - 1));
                } catch (InValidUserInfoException | ObjectEntityNotFoundException e) {
                    e.getMessage();
                    isContinue = false;
                }
            } while (isContinue);
        } catch (ObjectEntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }


    public void giveSuggestion(Expert expert) {

        List<OrderDto> list = orderService.findToGetSuggest();
        isContinue = false;
        int count = 1;
        for (OrderDto orderDto : list) {
            System.out.println(count++ + " : " + orderDto);
        }

        do {
            System.out.println("enter receptionNumber of order :");
            input = scanner.next();
            try {
                ValidationInfo.isValidNumeric(input);
                int receptionNumber = Integer.parseInt(input);
                expertService.addSuggest(receptionNumber, getSuggest(expert), expert);
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
            }
        } while (isContinue);

    }

    public Suggestion getSuggest(Expert expert) {
        System.out.println("********* Suggestion information entry form ********");
        isContinue = false;
        Suggestion suggest = null;
        do {

            try {
                System.out.println("enter ProposedPrice : ");
                input = scanner.next();
                ValidationInfo.isValidNumeric(input);
                double price = Double.parseDouble(input);

                System.out.println("enter durationOfWork : ");
                input = scanner.next();
                ValidationInfo.isValidNumeric(input);
                int timeSpan = Integer.parseInt(input);

                System.out.println("enter startTime : ");
                input = scanner.next();
                ValidationInfo.isValidNumeric(input);
                int timeStart = Integer.parseInt(input);
                suggest = suggestService.createSuggest(price, timeSpan, timeStart, expert);
                isContinue = true;
                break;
            } catch (InValidUserInfoException | ObjectEntityNotFoundException e) {
                e.getMessage();
            }
        } while (isContinue);

        return suggest;
    }

    public void startAndEndOrder(int chose, Expert expert) {
        isContinue = false;
        do {
            System.out.println("enter receptionNumber of order : ");
            input = scanner.next();
            try {
                ValidationInfo.isValidNumeric(input);
                orderService.startAndEndOrder(Integer.parseInt(input), chose, expert);
                isContinue = true;
            } catch (InValidUserInfoException | ObjectEntityNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

}
