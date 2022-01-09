package service;

import data.dao.SubServiceDao;
import data.model.serviceSystem.SubService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SubServiceService {
    SubServiceDao subServiceDao = new SubServiceDao();

    public void save(SubService subService) {
        subServiceDao.save(subService);
    }

    public SubService createBranchDuty(String info) {
        String[] split = info.split(",");
        SubService subService = SubService.builder()
                .price(Double.parseDouble(split[2]))
                .build();
        subService.getSubServiceMap().put(split[0], split[1]);
        return subService;
    }

    public boolean findByName(String name) {
        String[] split = name.split(",");
        List<SubService> list = subServiceDao.findByName();
        Map.Entry<String, String> stringEntry = null;
        for (SubService subService : list) {
            stringEntry = subService.getSubServiceMap().
                    entrySet().stream().filter(m -> m.getKey().equals(split[0])).findAny().orElse(null);
        }
        if (stringEntry != null)
            return true;
        return false;
    }
}
