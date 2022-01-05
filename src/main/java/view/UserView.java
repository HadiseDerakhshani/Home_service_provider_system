package view;

import dto.CustomerDto;
import dto.ExpertDto;
import exception.InValidUserInfoException;
import model.person.Expert;
import model.serviceSystem.BranchDuty;
import model.serviceSystem.MasterDuty;
import service.BranchDutyService;
import service.CustomerService;
import service.ExpertService;
import service.MasterDutyService;
import validation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private CustomerService customerService = new CustomerService();
    private BranchDutyService branchDutyService = new BranchDutyService();
    private MasterDutyService masterDutyService = new MasterDutyService();
    private ExpertService expertService = new ExpertService();
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
        List<ExpertDto> filter = expertService.filter(info);
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

    public void addExpert() {
        System.out.println("********* Expert information entry form ********");
        Expert expert = null;
        isContinue = false;
        String info;
        do {
            System.out.println("Enter Information Like Sample: firstName,lastName,email," +
                    "password,phoneNumber,credit,score,image file address");
            info = scanner.next();
            try {
                ValidationInfoExpert.isValidInfo(info);
                String[] split = info.split(",");
                expert = expertService.createExpert(info);
                expert = expertService.addPicture(expert, split[7]);
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                e.getMessage();
                isContinue = false;
            }
        } while (isContinue);
        expert.setServiceList(showMasterDuty());
        expertService.save(expert);
    }

    public List<MasterDuty> showMasterDuty() {
        isContinue = false;
        List<MasterDuty> list = new ArrayList<>();
        List<MasterDuty> dutyList = masterDutyService.showAll();
        int count = 0;
        for (MasterDuty masterDuty : dutyList) {
            System.out.print((++count) + " : ");
            System.out.print(masterDuty);
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
}
