package ir.maktab.service;

import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.entity.serviceSystem.Service;

import java.util.List;
import java.util.Optional;


public interface ServiceService {


    public ServiceDto save(String name);

    public Optional<Service> find(String name);

    public ServiceDto findByName(String name);

    public void deleteService(String name);

    public void update(ServiceDto serviceDto);

    public ServiceDto addSubService(ServiceDto serviceDto, SubServiceDto subServiceDto);

    public List<ServiceDto> findAll();
}
