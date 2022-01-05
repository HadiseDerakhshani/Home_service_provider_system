package service;

import dao.BranchDutyDao;
import model.serviceSystem.BranchDuty;

import java.util.List;
import java.util.Map;

public class BranchDutyService {
    BranchDutyDao branchDutyDao = new BranchDutyDao();

    public void save(BranchDuty branchDuty) {
        branchDutyDao.save(branchDuty);
    }

    public BranchDuty createBranchDuty(String info) {
        String[] split = info.split(",");
        BranchDuty branchDuty = BranchDuty.builder()
                .price(Double.parseDouble(split[2]))
                .build();
        branchDuty.getBranchServiceMap().put(split[0], split[1]);
        return branchDuty;
    }

    public boolean findByName(String name) {
        String[] split = name.split(",");
        List<BranchDuty> list = branchDutyDao.findByName();
        Map.Entry<String, String> stringEntry = null;
        for (BranchDuty branchDuty : list) {
            stringEntry = branchDuty.getBranchServiceMap().
                    entrySet().stream().filter(m -> m.getKey().equals(split[0])).findAny().orElse(null);
        }
        if (stringEntry != null)
            return true;
        return false;
    }
}
