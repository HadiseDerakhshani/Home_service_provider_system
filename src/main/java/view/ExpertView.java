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
                    " \n3.change password \n 4.change phoneNumber \n 5.add subService \n6.exit");
            info = scanner.next();
            try {
                ValidationInfo.isValidLogin(info);

                switch (info) {
                    case "1":
                        expertService.findExpert(expert);
                        break;
                    case "2":
                        giveSuggestion(expert);
                        break;
                    case "3":
                        System.out.println(" enter new password :");
                        info = scanner.next();
                        ValidationInfo.isValidPassword(info);
                        expertService.changePassword(expert, info);
                        break;
                    case "4":
                        System.out.println(" enter new phoneNumber :");
                        info = scanner.next();
                        ValidationInfo.isValidPhoneNumber(info);
                        expertService.changePhoneNumber(expert, info);
                        break;
                    case "5":
                        List<SubService> list = addSubService(expert);
                        expertService.updateServiceList(list, expert.getEmail());
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
                info = scanner.next();
                try {
                    ValidationInfo.isValidNumeric(info);
                    int index = Integer.parseInt(info);
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

   /* public void update(String email) {
        isContinue = false;
        String value;
        System.out.println(" select item for update : \n1.firstname \n2.lastname \n3.email \n4.password " +
                "\n5.phoneNumber \n6.credit \n7.Score \n8.image \n9.listService");
        info = scanner.next();
        try {

            ValidationUpdate.isValidUpdateExpert(info);
            System.out.println("enter new value for update");
            value = scanner.next();
            ValidationUpdate.isValidInfo(info, value);
            // expertService.update(info, value, email);
            isContinue = true;
        } catch (InValidUserInfoException e) {
            e.getMessage();
        }
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
            info = scanner.next();
            try {
                ValidationInfo.isValidNumeric(info);
                int index = Integer.parseInt(info);
                ValidationInfo.isValidIndex(count, index);
                OrderDto orderDto = list.get(index - 1);
                Order order = orderService.findByRegisterDate(orderDto.getRegisterDate());
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
                info = scanner.next();
                ValidationInfo.isValidNumeric(info);
                double price = Double.parseDouble(info);

                System.out.println("enter durationOfWork : ");
                info = scanner.next();
                ValidationInfo.isValidNumeric(info);
                int timeSpan = Integer.parseInt(info);

                System.out.println("enter startTime : ");
                info = scanner.next();
                ValidationInfo.isValidNumeric(info);
                int timeStart = Integer.parseInt(info);
                suggest = suggestionService.createSuggest(price, timeSpan, timeStart, expert);
                isContinue = true;
                break;
            } catch (InValidUserInfoException | IsNullObjectException e) {
                e.getMessage();
            }
        } while (isContinue);

        return suggest;
    }
}
