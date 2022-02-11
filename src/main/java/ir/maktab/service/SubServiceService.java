package ir.maktab.service;

import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.entity.serviceSystem.SubService;

import java.util.List;


public interface SubServiceService {


    List<SubServiceDto> findAll();

    SubService find(String name);

    SubServiceDto findByName(String name);

    SubServiceDto save(SubServiceDto subServiceDto);

    void deleteSubService(String name);
}
