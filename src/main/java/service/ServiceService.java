package service;

import data.dao.ServiceDao;
import data.model.serviceSystem.Service;
import exception.IsNullObjectException;

@org.springframework.stereotype.Service
public class ServiceService {
    ServiceDao serviceDao;

    public ServiceService(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    public Service save(Service service) {
        return serviceDao.save(service);
    }

    public Service createService(String name) {
        if (findByName(name) == null) {
            Service service = Service.builder()
                    .name(name)
                    .build();
            return service;
        } else
            throw new IsNullObjectException("--- exit service ---");

    }

    public Service findByName(String name) {
        return serviceDao.finByName(name);
    }

    public void deleteService(String name) {
        serviceDao.delete(findByName(name));
    }

}
