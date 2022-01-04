package service;

import dao.MasterDutyDao;
import model.serviceSystem.BranchDuty;
import model.serviceSystem.MasterDuty;

import java.util.List;

public class MasterDutyService {
   MasterDutyDao masterDutyDao=new MasterDutyDao();
    public void save(MasterDuty masterDuty) {
       masterDutyDao.save(masterDuty);
    }

    public MasterDuty createMasterDuty(List<BranchDuty> list, String info) {
        String[] split = info.split(",");
        MasterDuty masterDuty = MasterDuty.builder()
                .name(info)
                .branchDutyList(list)
                .build();
        return masterDuty;
    }
}
