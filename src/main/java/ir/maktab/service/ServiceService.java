package ir.maktab.service;

import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.model.serviceSystem.Service;

import java.util.List;


public interface ServiceService {


    public Service save(Service service);

    public Service createService(String name);

    public ServiceDto findByName(String name);

    public void deleteService(String name);

    public void update(ServiceDto serviceDto);

    public List<ServiceDto> findAll();
}
