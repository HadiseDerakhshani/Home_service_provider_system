package service;

import data.dao.ServiceDao;
import data.serviceSystem.Service;
import data.serviceSystem.SubService;

import java.util.List;

public class ServiceService {
    ServiceDao serviceDao = new ServiceDao();

    public void save(Service service) {
        serviceDao.save(service);
    }

    public Service createMasterDuty(List<SubService> list, String info) {
        //   String[] split = info.split(",");
        Service service = Service.builder()
                .name(info)//
                .subServiceList(list)
                .build();
        return service;
    }

    public boolean findByName(String name) {
        return serviceDao.findByName(name);
    }

    public List<Service> showAll() {
        return serviceDao.selectAll();
    }
}
