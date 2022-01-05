package view;

import exception.InValidUserInfoException;
import model.enums.UserStatus;
import model.person.Expert;
import model.serviceSystem.MasterDuty;
import service.BranchDutyService;
import service.CustomerService;
import service.ExpertService;
import service.MasterDutyService;
import validation.ValidationDutyInfo;
import validation.ValidationInfo;
import validation.ValidationInfoExpert;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpertView {
    private CustomerService customerService = new CustomerService();
    private BranchDutyService branchDutyService = new BranchDutyService();
    private MasterDutyService masterDutyService = new MasterDutyService();
    private ExpertService expertService = new ExpertService();
    private boolean isContinue;
    private String info;
    private Scanner scanner = new Scanner(System.in);


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

    public void loginByExpert() {
        isContinue = false;

        do {
            System.out.println("select Item :\n 1.new Expert \n2.member Expert \n3.exit");
            info = scanner.next();
            try {
                ValidationInfo.isValidLogin(info);

                switch (info) {
                    case "1":
                        addExpert();
                        break;
                    case "2":
                        loginMemberExpert();
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
                Expert expert = expertService.checkEmail(info);
                if (expert != null && expert.getUserStatus().equals(UserStatus.CONFIRMED.name()))
                    checkPasswordExpert(expert);
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
                    ///////menu filter,delete,update credit
                    System.out.println("************ Welcome Expert ************");
                    isContinue = true;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (isContinue);
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
