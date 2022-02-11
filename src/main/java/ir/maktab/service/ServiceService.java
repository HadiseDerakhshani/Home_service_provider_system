package ir.maktab.service;

import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.entity.serviceSystem.Service;

import java.util.List;
import java.util.Optional;


public interface ServiceService {


    ServiceDto save(String name);

    Optional<Service> find(String name);

    ServiceDto findByName(String name);

    void deleteService(String name);

    void update(ServiceDto serviceDto);

    ServiceDto addSubService(ServiceDto serviceDto, SubServiceDto subServiceDto);

    List<ServiceDto> findAll();
}
