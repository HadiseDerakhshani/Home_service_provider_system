package service;

import dao.BranchDutyDao;
import model.enums.UserStatus;
import model.person.Customer;
import model.serviceSystem.BranchDuty;

public class BranchDutyService {
  BranchDutyDao branchDutyDao=new BranchDutyDao();
    public void save( BranchDuty branchDuty) {
        branchDutyDao.save(branchDuty);
    }

    public BranchDuty createBranchDuty(String info) {
        String[] split = info.split(",");
       BranchDuty branchDuty = BranchDuty.builder()
               .price(Double.parseDouble(split[2]))
               .build();
       branchDuty.getBranchServiceMap().put(split[0],split[1]);
        return branchDuty;
    }
}
