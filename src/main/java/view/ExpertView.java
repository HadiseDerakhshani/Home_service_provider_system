package view;

import data.dto.OrderDto;
import data.model.serviceSystem.Service;
import data.model.user.Expert;
import exception.InValidUserInfoException;
import validation.*;

import java.util.ArrayList;
import java.util.List;

public class ExpertView extends BaseView {
    /*private ExpertService expertService = context.getBean(ExpertService.class);
    private Scanner scanner = new Scanner(System.in);
    private boolean isContinue;
    private String info;*/
/*
    //  private CustomerService customerService = new CustomerService();
    //  private BranchDutyService branchDutyService = new BranchDutyService();
    private ServiceService serviceService = new ServiceService();
    private OrderService orderService = new OrderService();
    private ExpertService expertService = new ExpertService();*/


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

        //  expert.setServiceList(showMasterDuty());
        expertService.save(expert);
    }

    public void MenuExpert(Expert expert) {
        isContinue = false;
        System.out.println("************ Welcome Expert ************");
        do {

            System.out.println("select Item :\n 1.show Expert Information \n2.register suggestion \n3.exit");
            info = scanner.next();
            try {
                ValidationInfo.isValidLogin(info);

                switch (info) {
                    case "1":
                        expertService.findExpert(expert);
                        break;
                    case "2":
                        ////////
                        break;
                    case "3":
                        break;
                }
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
            }
        } while (isContinue);
    }

    public void loginMemberExpert() {
        isContinue = false;
        do {
            System.out.println("enter email :");
            info = scanner.next();
            try {
                ValidationInfo.isValidEmail(info);
                Expert expert = expertService.findByEmail(info);
                if (expert != null)
                    checkPasswordExpert(expert);
                else {
                    System.out.println("Expert for this email not exit");
                }
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
            }
        } while (isContinue);
    }

    public void checkPasswordExpert(Expert expert) {
        isContinue = false;
        do {
            System.out.println("enter password");
            info = scanner.next();
            try {
                ValidationInfo.isValidPassword(info);
                if (!expertService.checkPassword(expert, info))
                    isContinue = false;
                else {

                    System.out.println("************ Welcome Expert ************");
                    //      System.out.println(expertService.showExpert(expert.getEmail()));
                    isContinue = true;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (isContinue);
    }

    public List<Service> showMasterDuty() {
        isContinue = false;
        List<Service> list = new ArrayList<>();
        List<Service> dutyList = serviceService.showAll();
        int count = 0;
        for (Service service : dutyList) {
            System.out.print((++count) + " : ");
            System.out.print(service);
        }
        do {
            System.out.println("enter the number of service for work :");
            String select = scanner.next();
            String[] split = select.split(",");
            count = 0;
            for (int i = 0; i < split.length; i++) {
                try {
                    ValidationDutyInfo.isValidNumeric(split[i]);
                    count++;
                    isContinue = true;
                } catch (InValidUserInfoException e) {
                    e.getMessage();
                    isContinue = false;
                }
                list.add(dutyList.get(i));
            }
        } while (isContinue);
        return list;
    }

    public void filterExpert() {

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
    }

    public void update(String email) {
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
    }

    public void giveSuggestion(Expert expert) {
        List<OrderDto> list = orderService.findToGetSuggest();
        isContinue = false;
        int count = 0;
        for (OrderDto orderDto : list) {

            System.out.println(count + " ==> " + orderDto);
        }

        do {
            System.out.println("enter number of order for given suggest");
            info = scanner.next();
            try {
                ValidationInfoExpert.isValidNumeric(info);

                //    expertService.addSuggest(Integer.parseInt(info), list, getInfoSuggest(), expert);
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
            }
        } while (isContinue);
    }

    public String getInfoSuggest() {
        System.out.println("********* Suggestion information entry form ********");
        isContinue = false;
        do {
            System.out.println("enter info like sample : ProposedPrice,durationOfWork,startTime");
            info = scanner.next();
            try {
                ValidationInfoSuggestion.isValidInfo(info);
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
            }
        } while (isContinue);

        return info;
    }
}
*/
        }