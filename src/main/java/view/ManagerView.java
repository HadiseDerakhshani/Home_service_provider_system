package view;

import data.dto.CustomerDto;
import data.dto.ExpertDto;
import data.dto.OrderDto;
import data.model.serviceSystem.Service;
import data.model.serviceSystem.SubService;
import data.model.user.Expert;
import data.model.user.Manager;
import exception.InValidUserInfoException;
import exception.IsNullObjectException;
import org.springframework.data.querydsl.QPageRequest;
import validation.ValidationInfo;

import java.util.List;

public class ManagerView extends BaseView {
    public void loginManager() {
        isContinue = false;
        String pass;
        do {
            try {
                System.out.println("enter username");
                input = scanner.next();
                ValidationInfo.isValidPassword(input);
                System.out.println("enter password");
                pass = scanner.next();
                ValidationInfo.isValidPassword(pass);
                Manager manager = managerService.createManager(input, pass);
                if (managerService.checkManager(manager)) {
                    isContinue = true;
                    managerMenu(manager);
                    break;
                }
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);

    }

    public void managerMenu(Manager manager) {

        isContinue = false;
        System.out.println("************ Welcome Manager ************");
        do {
            System.out.println("select Item :\n 1.show list Expert \n2.Show list Customer " +
                    " \n3.change userNAme & password \n 4.Confirm customer \n 5.add Service \n" +
                    "6.show order \n7.addExpert \n8.deleted \n9.Get paid\n10.exit");
            /////////////////////////////
            input = scanner.next();
            try {
                ValidationInfo.isValidLogin(input);
                switch (input) {
                    case "1":
                        showExpert();
                        isContinue = true;
                        break;
                    case "2":
                        showCustomer();
                        /////pagination
                        isContinue = true;
                        break;
                    case "3":
                        System.out.println(" enter new username :");
                        input = scanner.next();
                        ValidationInfo.isValidPassword(input);
                        System.out.println(" enter new password :");
                        String pass = scanner.next();
                        ValidationInfo.isValidPassword(pass);
                        managerService.Save(managerService.createManager(input, pass));
                        isContinue = true;
                        break;
                    case "4":
                        managerService.customerConfirmation();
                        isContinue = true;
                        break;
                    case "5":
                        addService();
                        isContinue = true;
                        break;
                    case "6":
                        showOrder();
                        isContinue = true;
                        break;
                    case "7":
                        expertView.addExpert();
                        isContinue = true;
                        break;
                    case "8":
                        deleted();
                        isContinue = true;
                        break;
                    case "9":
                        mainMenu();
                        isContinue = true;
                        break;
                }
                if (isContinue)
                    break;

            } catch (InValidUserInfoException | IsNullObjectException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

    public void showExpert() {
        int first=0,number,pageNumber;
        long total;
       double remaining;
        System.out.println();
        System.out.println("Enter the number of records per page : ");
        input=scanner.next();
        try {
            ValidationInfo.isValidNumeric(input);
            number=Integer.parseInt(input);
            total=expertService.totalRecord();
            if(total > 1 && total >=number && number>1) {
                remaining=total%number;
                pageNumber=remaining==0 ? (int)total/number : (int)(total/number)+1;
                for (int i=0;i<pageNumber;i++){
                List<ExpertDto> list = expertService.findAll(first,number);
                list.forEach(System.out::println);
                System.out.println("select show page \n1.next \n2.previous");
                input=scanner.next();
                ValidationInfo.isValidSelected(input);
                if(input.equals("1") ) {
                    if (i == (pageNumber - 1))
                        System.out.println(" next page is not exit");
                    else
                        first = first + number;
                }else if(input.equals("2") ) {
                         if(i==0)
                        System.out.println("previous page is not exit");
                    }else
                        first=first-number;
                }
            }else
                if(total<=number || number==1){
                List<ExpertDto> list = expertService.findAll(0,(int)total);
            list.forEach(System.out::println);
            }
                isContinue=true;
        } catch (InValidUserInfoException | IsNullObjectException e) {
            System.out.println(e.getMessage());
        }

    }

    public void showCustomer() {
        try {
            List<CustomerDto> list = customerService.findAll();
            list.forEach(System.out::println);
            ////
        } catch (IsNullObjectException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addServiceSystem(String name) {
        isContinue = false;
        do {
            System.out.println(" select Item \n1.addService \n2.addSubService");
            input = scanner.next();
            try {
                ValidationInfo.isValidSelected(input);
                switch (input) {
                    case "1" -> addService();
                    case "2" -> addSubService(name);
                }
                isContinue = true;
                break;
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

    public void addService() {

        System.out.println("********* Service information entry form ********");
        isContinue = false;
        String name;
        do {
            System.out.println("Enter  name of service :");
            name = scanner.next();
            try {
                ValidationInfo.isValidCharacter(name);
                serviceService.save(serviceService.createService(name));
                System.out.println(" do you wanted add SubService for  this service :\n1.yes \n2.no");
                input = scanner.next();
                ValidationInfo.isValidSelected(input);
                if (input.equals("1"))
                    addSubService(name);
                isContinue = true;
                break;

            } catch (InValidUserInfoException | IsNullObjectException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }

    public void addSubService(String name) {
        String description, price;
        System.out.println("********* subService information entry form ********");
        isContinue = false;
        Service service = serviceService.findByName(name);
        if (service != null) {
            do {
                System.out.println("Enter  name of subService :");
                input = scanner.next();
                try {
                    ValidationInfo.isValidCharacter(input);
                    System.out.println("Enter description of subService :");
                    description = scanner.next();
                    ValidationInfo.isValidCharacter(description);

                    System.out.println("Enter price of subService :");
                    price = scanner.next();
                    ValidationInfo.isValidNumeric(price);
                    SubService subService = subServiceService.createSubService(input, description, Double.parseDouble(price));
                    subServiceService.save(subService);
                    service.getSubServiceList().add(subService);
                    serviceService.save(service);
                    isContinue = true;
                    break;

                } catch (InValidUserInfoException | IsNullObjectException e) {
                    System.out.println(e.getMessage());
                }
            } while (isContinue);
        } else
            System.out.println("--- service not exit ---");
    }

    public void showOrder() {
        try {
            List<OrderDto> list = orderService.findAll();
            list.forEach(System.out::println);
        } catch (IsNullObjectException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleted() {
        isContinue = false;
        System.out.println("select item for delete \n1.customer\n2.expert\n3.service \n4.subService ");
        input = scanner.next();
        do {
            try {
                ValidationInfo.isValidNumeric(input);
                switch (input) {
                    case "1":
                        System.out.println("enter email of customer");
                        input = scanner.next();
                        ValidationInfo.isValidEmail(input);
                        customerService.deleteCustomer(input);
                        isContinue = true;
                        break;
                    case "2":
                        System.out.println(" enter email of expert");
                        input = scanner.next();
                        ValidationInfo.isValidEmail(input);
                        expertService.deleteExpert(input);
                        isContinue = true;
                        break;
                    case "3":
                        System.out.println(" enter name of service");
                        input = scanner.next();
                        ValidationInfo.isValidCharacter(input);
                        serviceService.deleteService(input);
                        isContinue = true;
                        break;
                    case "4":
                        System.out.println(" enter name of subService");
                        input = scanner.next();
                        ValidationInfo.isValidCharacter(input);
                        subServiceService.deleteSubService(input);
                        isContinue = true;
                        break;

                }
            } catch (InValidUserInfoException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }
    public void getPaid(){
        isContinue=false;
        do {
            System.out.println("enter receptionNumber of order : ");
            input=scanner.next();
            try {
                ValidationInfo.isValidNumeric(input);
            //   orderService.startAndEndOrder(Integer.parseInt(input),chose,expert);
                isContinue=true;
            } catch (InValidUserInfoException  | IsNullObjectException e) {
                System.out.println(e.getMessage());
            }
        } while (isContinue);
    }
}
