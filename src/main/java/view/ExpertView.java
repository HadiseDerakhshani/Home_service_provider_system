package view;

import data.dto.OrderDto;
import data.dto.SubServiceDto;
import data.model.order.Order;
import data.model.order.Suggestion;
import data.model.serviceSystem.SubService;
import data.model.user.Expert;
import exception.InValidUserInfoException;
import exception.IsNullObjectException;
import validation.ValidationInfo;

import java.util.List;

@org.springframework.stereotype.Service
public class ExpertView extends BaseView {

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
                e.getMessage();
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
            System.out.println("select Item :\n 1.show Expert Information \n2.register suggestion " +
                    " \n3.change password \n 4.change phoneNumber \n 5.add subService \n6.start order\n7.end order\n8.exit");
            input = scanner.next();
            try {
                ValidationInfo.isValidLogin(input);

                switch (input) {
                    case "1":
                        expertService.findExpert(expert);
                        break;
                    case "2":
                        giveSuggestion(expert);
                        break;
                    case "3":
                        System.out.println(" enter new password :");
                        input = scanner.next();
                        ValidationInfo.isValidPassword(input);
                        expertService.changePassword(expert, input);
                        break;
                    case "4":
                        System.out.println(" enter new phoneNumber :");
                        input = scanner.next();
                        ValidationInfo.isValidPhoneNumber(input);
                        expertService.changePhoneNumber(expert, input);
                        break;
                    case "5":
                        List<SubService> list = addSubService(expert);
                        expertService.updateServiceList(list, expert);
                        break;
                    case "6":
                     startAndEndOrder(6,expert);
                        break;
                    case "7":
                  startAndEndOrder(7,expert);
                        break;
                    case "8":
                        mainMenu();
                        break;
                }
                isContinue = true;
                break;
            } catch (InValidUserInfoException | IsNullObjectException e) {
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
                System.out.println("enter the number of service for work :");
                input = scanner.next();
                try {
                    ValidationInfo.isValidNumeric(input);
                    int index = Integer.parseInt(input);
                    ValidationInfo.isValidSelect(count, index);
                    list = expertService.addSubServiceExpert(expert, subServiceDtoList, index);
                    subServiceService.addExpertToList(expert, list.get(list.size() - 1));
                } catch (InValidUserInfoException | IsNullObjectException e) {
                    e.getMessage();
                    isContinue = false;
                }
            } while (isContinue);
        } catch (IsNullObjectException e) {
            e.getMessage();
        }
        return list;
    }

   /* public void filterExpert() {
        isContinue = false;
        String info;
        do {
            System.out.println("enter filter case name,family,emil:if not wanted case enter 0");

            info = scanner.next();
            try {
                info = ValidationFilterExpert.isValidInfo(info);
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);
        // List<ExpertDto> filter = expertService.filter(info);
        //   filter.forEach(System.out::println);
    }*/


    public void giveSuggestion(Expert expert) {

        List<OrderDto> list = orderService.findToGetSuggest();
        isContinue = false;
        int count = 1;
        for (OrderDto orderDto : list) {
            System.out.println(count++ + " : " + orderDto);
        }

        do {
            System.out.println("enter number of order for given suggest");
            input = scanner.next();
            try {
                ValidationInfo.isValidNumeric(input);
                int index = Integer.parseInt(input);
                ValidationInfo.isValidIndex(count, index);
                OrderDto orderDto = list.get(index - 1);
                Order order = orderService.findByReceptionNumber(orderDto.getReceptionNumber());
                expertService.addSuggest(order, getSuggest(expert), expert);
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
                suggest = suggestionService.createSuggest(price, timeSpan, timeStart, expert);
                isContinue = true;
                break;
            } catch (InValidUserInfoException | IsNullObjectException e) {
                e.getMessage();
            }
        } while (isContinue);

        return suggest;
    }
    public void startAndEndOrder(int chose,Expert expert){
        isContinue=false;
        do {
            System.out.println("enter receptionNumber of order : ");
            input=scanner.next();
            try {
                ValidationInfo.isValidNumeric(input);
                orderService.startAndEndOrder(Integer.parseInt(input),chose,expert);
                isContinue=true;
            } catch (InValidUserInfoException  | IsNullObjectException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

}
