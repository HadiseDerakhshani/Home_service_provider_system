package ir.maktab.service;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.entity.serviceSystem.SubService;

import java.util.List;


public interface SubServiceService {


    public List<SubServiceDto> findAll();

    public SubService find(String name);

    public SubServiceDto findByName(String name);

 public SubServiceDto save(SubServiceDto subServiceDto);

    public void deleteSubService(String name);
}
