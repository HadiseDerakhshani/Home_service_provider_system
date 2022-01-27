package ir.maktab.service.implemention;

import ir.maktab.data.dao.SubServiceDao;
import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.mapping.ExpertMap;
import ir.maktab.data.mapping.SubServiceMap;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.SubServiceService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service

public class SubServiceServiceImpl implements SubServiceService {

    private final SubServiceMap subServiceMap;

    private final ExpertMap expertMap;
    private final SubServiceDao subServiceDao;

    @Autowired
    public SubServiceServiceImpl(@Lazy SubServiceMap subServiceMap, @Lazy ExpertMap expertMap,
                                 SubServiceDao subServiceDao) {
        this.subServiceMap = subServiceMap;
        this.expertMap = expertMap;
        this.subServiceDao = subServiceDao;
    }



    @Override
    public List<SubServiceDto> findAll() {
        List<SubService> listSubService = subServiceDao.findAll();
        if (listSubService != null) {
            List<SubServiceDto> listSubServiceDto = listSubService.stream().map(subServiceMap::createSubServiceDto)
                    .collect(Collectors.toList());
            return listSubServiceDto;
        } else
            throw new ObjectEntityNotFoundException(" SubService is null");
    }

    @Override
    public SubServiceDto findByName(String name) {
        return subServiceMap.createSubServiceDto(subServiceDao.findByName(name).get());
    }

    @Override
    public SubService find(String name) {
        return subServiceDao.findByName(name).get();
    }

    @Override
    public void addExpertToList(ExpertDto expertDto, SubServiceDto service) {
        SubService subService = find(service.getName());
        subService.getExpertList().add(expertMap.createExpert(expertDto));
        subServiceDao.save(subService);
    }

    @Override
    public void deleteSubService(String name) {
        subServiceDao.delete(find(name));
    }
}
