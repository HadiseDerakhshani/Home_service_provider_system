package ir.maktab.service;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.model.serviceSystem.SubService;

import java.util.List;


public interface SubServiceService {


    public List<SubServiceDto> findAll();

    public SubServiceDto findByName(String name);

    public void addExpertToList(ExpertDto expertDto, SubService service);

    public void deleteSubService(String name);
}
