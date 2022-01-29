package ir.maktab.service.implemention;

import ir.maktab.data.repasitory.ServiceRepository;
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
    private final ServiceRepository serviceRepository;

    private final ServiceMap serviceMap;

    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository, @Lazy ServiceMap serviceMap) {
        this.serviceRepository = serviceRepository;
        this.serviceMap = serviceMap;
    }

    @Override
    public Service save(Service service) {
        return serviceRepository.save(service);
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
    public ServiceDto findByName(String name) {
        if (serviceRepository.findByName(name).isPresent())
            return serviceMap.createServiceDto(serviceRepository.findByName(name).get());
        else
            throw new ObjectEntityNotFoundException("Service not found");
    }

    @Override
    public void deleteService(String name) {

        serviceRepository.delete(serviceMap.createService(findByName(name)));
    }

    @Override
    public void update(ServiceDto serviceDto) {
        Service service = serviceMap.createService(serviceDto);
        serviceRepository.save(service);
    }

    @Override
    public List<ServiceDto> findAll() {
        List<Service> serviceList = serviceRepository.findAll();
        return serviceList.stream().map(serviceMap::createServiceDto).collect(Collectors.toList());
    }
}
