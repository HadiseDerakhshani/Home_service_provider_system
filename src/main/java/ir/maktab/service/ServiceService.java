package ir.maktab.service;

import ir.maktab.data.dao.ServiceDao;
import ir.maktab.data.model.serviceSystem.Service;
import ir.maktab.exception.ObjectEntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceDao serviceDao;

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
            throw new ObjectEntityNotFoundException("--- exit ir.maktab.service ---");
    }

    public Service findByName(String name) {
        return serviceDao.finByName(name).get();
    }

    public void deleteService(String name) {
        serviceDao.delete(findByName(name));
    }

    public void update(Service service) {

        serviceDao.updateSubList(service.getId(), service.getSubServiceList());
    }

}
