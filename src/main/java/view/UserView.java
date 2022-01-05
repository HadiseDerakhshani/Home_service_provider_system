package view;

import dto.CustomerDto;
import exception.InValidUserInfoException;
import model.enums.UserStatus;
import model.person.Customer;
import model.serviceSystem.BranchDuty;
import service.BranchDutyService;
import service.CustomerService;
import service.ExpertService;
import service.MasterDutyService;
import validation.ValidationDutyInfo;
import validation.ValidationFilterCustomer;
import validation.ValidationInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private CustomerService customerService = new CustomerService();
    private BranchDutyService branchDutyService = new BranchDutyService();
    private MasterDutyService masterDutyService = new MasterDutyService();
    private ExpertService expertService = new ExpertService();
    private boolean isContinue;
    private String info;
    private Scanner scanner = new Scanner(System.in);

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

    public void loginMemberCustomer() {
        isContinue = false;
        do {
            System.out.println("enter email :");
            info = scanner.next();
            try {
                ValidationInfo.isValidEmail(info);
                Customer customer = customerService.checkEmail(info);
                if (customer != null && customer.getUserStatus().equals(UserStatus.CONFIRMED.name()))
                    checkPasswordCustomer(customer);
                else {
                    System.out.println("customer for this email not exit");
                }
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
            }
        } while (isContinue);
    }

    public void checkPasswordCustomer(Customer customer) {
        isContinue = false;
        do {
            System.out.println("enter password");
            info = scanner.next();
            try {
                ValidationInfo.isValidPassword(info);
                if (!customerService.checkPassword(customer, info))
                    isContinue = false;
                else {
                    ///////
                    System.out.println("************ Welcome Customer ************");
                    isContinue = true;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (isContinue);
    }


    public void addCustomer() {
        System.out.println("********* Customer information entry form ********");
        String info = getInformation();
        if (info != null) {
            customerService.save(customerService.createCustomer(info));
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

    public void updateCustomer() {
        System.out.println("");
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
                    if (!masterDutyService.findByName(info)) {
                        masterDutyService.save(masterDutyService.createMasterDuty(addBranchDuty(info), info));
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

    public List<BranchDuty> addBranchDuty(String nameDuty) {
        List<BranchDuty> branchDutyList = new ArrayList<>();
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
                        branchDutyList.add(branchDutyService.createBranchDuty(getInfoBranchDuty()));
                    }
                } else {
                    System.out.println("Enter Description of service :");
                    info = scanner.next();
                    ValidationDutyInfo.isValidCharacter(info);
                    branchDutyList.add(branchDutyService.createBranchDuty(nameDuty + "," + info));
                }
                isContinue = true;
                break;
            }
        } while (isContinue);
        for (BranchDuty branchDuty : branchDutyList) {
            branchDutyService.save(branchDuty);
        }
        return branchDutyList;
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
                if (!branchDutyService.findByName(info)) {
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


}
