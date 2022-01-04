package view;

import dto.CustomerDto;
import exception.InValidUserInfoException;
import model.serviceSystem.BranchDuty;
import service.BranchDutyService;
import service.CustomerService;
import service.MasterDutyService;
import validation.ValidationDutyInfo;
import validation.ValidationFilterCustomer;
import validation.ValidationInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private CustomerService customerService = new CustomerService();
    private BranchDutyService branchDutyService=new BranchDutyService();
    private MasterDutyService masterDutyService=new MasterDutyService();
    private boolean isContinue;
    private Scanner scanner = new Scanner(System.in);

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

    public void filterCustomer() {

        isContinue = false;
        String info;
        do {
            System.out.println("enter filter case name,family,emil:if not wanted case enter 0");

            info = scanner.next();
            try {
                 info= ValidationFilterCustomer.isValidInfo(info);
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
        String info,number;
        do {
            System.out.println("Enter  name of service :");
            info = scanner.next();

            try {
             if(ValidationInfo.isValidCharacter(info)){
               masterDutyService.save(masterDutyService.createMasterDuty(addBranchDuty(info),info));
                isContinue = true;
                break;
               }
            } catch (InValidUserInfoException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);


        if (info != null) {
          //  customerService.save(customerService.createCustomer(info));
        }
    }
    public List<BranchDuty> addBranchDuty(String nameDuty) {
        List<BranchDuty> branchDutyList=new ArrayList<>();
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
                }else{
                    System.out.println("Enter Description of service :");
                    info = scanner.next();
                    ValidationDutyInfo.isValidCharacter(info);
                    branchDutyList.add(branchDutyService.createBranchDuty(nameDuty+","+info));
                }
                isContinue=true;
                break;
            }
        }while (isContinue);
        for (BranchDuty branchDuty:branchDutyList){
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
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);
        return info;
    }
}
