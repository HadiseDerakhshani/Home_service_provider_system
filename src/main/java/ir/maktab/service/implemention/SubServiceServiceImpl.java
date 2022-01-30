package ir.maktab.service.implemention;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.entity.serviceSystem.SubService;
import ir.maktab.data.mapping.ExpertMap;
import ir.maktab.data.mapping.SubServiceMap;
import ir.maktab.data.repasitory.SubServiceRepository;
import ir.maktab.exception.ObjectEntityNotFoundException;
import ir.maktab.service.SubServiceService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service

public class SubServiceServiceImpl implements SubServiceService {

    private final SubServiceMap subServiceMap;

    private final ExpertMap expertMap;
    private final SubServiceRepository subServiceRepository;

    @Autowired
    public SubServiceServiceImpl(@Lazy SubServiceMap subServiceMap, @Lazy ExpertMap expertMap,
                                 SubServiceRepository subServiceRepository) {
        this.subServiceMap = subServiceMap;
        this.expertMap = expertMap;
        this.subServiceRepository = subServiceRepository;
    }


    @Override
    public List<SubServiceDto> findAll() {
        List<SubService> listSubService = subServiceRepository.findAll();
        if (listSubService != null) {
            List<SubServiceDto> listSubServiceDto = listSubService.stream().map(subServiceMap::createSubServiceDto)
                    .collect(Collectors.toList());
            return listSubServiceDto;
        } else
            throw new ObjectEntityNotFoundException(" SubService is null");
    }

    @Override
    public SubServiceDto findByName(String name) {
        return subServiceMap.createSubServiceDto(subServiceRepository.findByName(name).get());
    }

    @Override
    public SubService find(String name) {
        return subServiceRepository.findByName(name).get();
    }

    @Override
    public void addExpertToList(ExpertDto expertDto, SubServiceDto service) {
        SubService subService = find(service.getName());
        ///  subService.getExpertList().add(expertMap.createExpert(expertDto));
        subServiceRepository.save(subService);
    }

    @Override
    public void deleteSubService(String name) {
        subServiceRepository.delete(find(name));
    }
}
