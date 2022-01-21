package ir.maktab.service;

import ir.maktab.data.dao.ServiceDao;
import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.mapping.ServiceMap;
import ir.maktab.data.model.serviceSystem.Service;
import ir.maktab.exception.ObjectEntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    private final ServiceDao serviceDao;
    private final ServiceMap serviceMap;

    @Override
    public Service save(Service service) {
        return serviceDao.save(service);
    }

    @Override
    public Service createService(String name) {
        if (findByName(name) == null) {
            Service service = Service.builder()
                    .name(name)
                    .build();
            return service;
        } else
            throw new ObjectEntityNotFoundException("--- exit Service ---");
    }

    @Override
    public Service findByName(String name) {
        return serviceDao.finByName(name).get();
    }

    @Override
    public void deleteService(String name) {
        serviceDao.delete(findByName(name));
    }

    @Override
    public void update(ServiceDto serviceDto) {
        Service service = serviceMap.createService(serviceDto);
        serviceDao.updateSubList(service.getId(), service.getSubServiceList());
    }

}
