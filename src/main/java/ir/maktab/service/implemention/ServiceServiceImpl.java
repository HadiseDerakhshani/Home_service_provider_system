package ir.maktab.service.implemention;

import ir.maktab.data.dao.ServiceDao;
import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.mapping.ServiceMap;
import ir.maktab.data.model.serviceSystem.Service;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.ServiceService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@org.springframework.stereotype.Service

public class ServiceServiceImpl implements ServiceService {
    private final ServiceDao serviceDao;

    private final ServiceMap serviceMap;

    @Autowired
    public ServiceServiceImpl(ServiceDao serviceDao, @Lazy ServiceMap serviceMap) {
        this.serviceDao = serviceDao;
        this.serviceMap = serviceMap;
    }

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
        if (serviceDao.findByName(name).isPresent())
            return serviceDao.findByName(name).get();
        else
            throw new ObjectEntityNotFoundException("Service not found");
    }

    @Override
    public void deleteService(String name) {
        serviceDao.delete(findByName(name));
    }

    @Override
    public void update(ServiceDto serviceDto) {
        Service service = serviceMap.createService(serviceDto);
        serviceDao.save(service);
    }

    @Override
    public List<ServiceDto> findAll() {
        List<Service> serviceList = serviceDao.findAll();
       return serviceList.stream().map(serviceMap::createServiceDto).collect(Collectors.toList());
    }
}
