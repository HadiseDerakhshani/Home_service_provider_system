package view;

import data.model.user.User;
import exception.InValidUserInfoException;
import exception.IsNullObjectException;
import validation.ValidationInfo;

public class UserView extends BaseView {
////manager & user

    public void loginMemberUser() {
        isContinue = false;
        do {
            System.out.println("enter email :");
            info = scanner.next();
            try {
                ValidationInfo.isValidEmail(info);
                checkPasswordUser(userService.findByEmail(info));
                isContinue = true;
                break;
            } catch (InValidUserInfoException | IsNullObjectException e) {
                e.getMessage();
            }
        } while (isContinue);
    }

    public void checkPasswordUser(User user) {
        isContinue = false;
        do {
            System.out.println("enter password");
            info = scanner.next();
            try {
                ValidationInfo.isValidPassword(info);
                if (userService.checkPassword(user, info)) {
                    showMenu(user);//menu
                    isContinue = true;
                    break;
                } else
                    System.out.println(" password is not correct");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (isContinue);
    }

    public void showMenu(User user) {

        switch (user.getUserRole().name()) {
            case "EXPERT":
                expertView.menuExpert(user.getEmail());
                break;
            case "CUSTOMER":
                customerView.menuCustomer(user.getEmail());
                break;
            default:
                //  user = userService.findByEmail(user.getEmail());
                break;
        }
        System.out.println(user);
    }


}
/*







    public void filterCustomer() {
        isContinue = false;
        String info;
        do {
            System.out.println("enter filter case name,family,emil:if not wanted case enter 0");
            info = scanner.next();
            try {
                info = ValidationFilterCustomer.isValidInfo(info);
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);
        List<CustomerDto> filter = customerService.filter(info);
        filter.forEach(System.out::println);
    }


    public void addMasterDuty() {
        System.out.println("********* MasterDuty information entry form ********");
        isContinue = false;
        String info, number;
        do {
            System.out.println("Enter  name of service :");
            info = scanner.next();

            try {
                if (ValidationInfo.isValidCharacter(info)) {
                    if (!serviceService.findByName(info)) {
                        serviceService.save(serviceService.createMasterDuty(addBranchDuty(info), info));
                        isContinue = true;
                        break;
                    } else {
                        System.out.println("this service is exited");
                        isContinue = false;
                    }
                }
            } catch (InValidUserInfoException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);
    }

    public List<SubService> addBranchDuty(String nameDuty) {
        List<SubService> subServiceList = new ArrayList<>();
        int number;
        String info;
        isContinue = false;
        do {
            System.out.println("Enter number of branch :");
            info = scanner.next();
            if (ValidationInfo.isValidNumeric(info)) {
                number = Integer.parseInt(info);
                if (number > 0) {
                    for (int i = 0; i < number; i++) {
                        subServiceList.add(subServiceService.createBranchDuty(getInfoBranchDuty()));
                    }
                } else {
                    System.out.println("Enter Description of service :");
                    info = scanner.next();
                    ValidationDutyInfo.isValidCharacter(info);
                    subServiceList.add(subServiceService.createBranchDuty(nameDuty + "," + info));
                }
                isContinue = true;
                break;
            }
        } while (isContinue);
        for (SubService subService : subServiceList) {
            subServiceService.save(subService);
        }
        return subServiceList;
    }

    public String getInfoBranchDuty() {
        System.out.println("********* BranchDuty information entry form ********");
        isContinue = false;
        String info;
        do {
            System.out.println("Enter Information Like Sample: Name of service,description,price");
            info = scanner.next();
            try {
                ValidationDutyInfo.isValidInfo(info);
                if (!subServiceService.findByName(info)) {
                    isContinue = true;
                    break;
                } else {
                    System.out.println("this branch service is exited");
                    isContinue = false;
                }
            } catch (InValidUserInfoException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);
        return info;
    }

}*/
