package view;

import data.model.user.User;
import exception.InValidUserInfoException;
import exception.IsNullObjectException;
import validation.ValidationInfo;

public class UserView extends BaseView {

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
                    showUser(user);//menu
                    isContinue = true;
                    break;
                } else
                    System.out.println(" password is not correct");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (isContinue);
    }

    public void showUser(User user) {

        switch (user.getUserRole().name()) {
            case "EXPERT":
                user = expertService.findByEmail(user.getEmail());
                break;
            case "CUSTOMER":
                user = customerService.findByEmail(user.getEmail());
                break;
            default:
                user = userService.findByEmail(user.getEmail());
                break;
        }
        System.out.println(user);
    }


}
/*

    public void loginByCustomer() {
        isContinue = false;

        do {
            System.out.println("select Item :\n 1.new customer \n2.member customer \n3.exit");
            info = scanner.next();
            try {
                ValidationInfo.isValidLogin(info);

                switch (info) {
                    case "1":
                        addCustomer();
                        break;
                    case "2":
                        loginMemberCustomer();
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

       public void addCustomer() {
        System.out.println("********* Customer information entry form ********");
        String info = getInformation();
        if (info != null) {
           // customerService.save(customerService.createCustomer(info));
        }
    }

    public String getInformation() {
        isContinue = false;
        String info;
        do {
            System.out.println("Enter Information Like Sample: firstName,lastName,email,password,phoneNumber,credit");
            info = scanner.next();
            try {
                ValidationInfo.isValidInfo(info);
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);
        return info;
    }

    public void update(String email) {
        isContinue = false;
        String value;
        System.out.println(" select item for update : \n1.firstname \n2.lastname \n3.email \n4.password " +
                "\n5.phoneNumber \n6.credit ");
        info = scanner.next();
        try {
            ValidationUpdate.isValidUpdateCustomer(info);
            System.out.println("enter new value for update");
            value = scanner.next();
            ValidationUpdate.isValidInfo(info, value);
            customerService.update(info, value, email);
            isContinue = true;
        } catch (InValidUserInfoException e) {
            e.getMessage();
        }
    }

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
