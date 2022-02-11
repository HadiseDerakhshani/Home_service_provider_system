package ir.maktab.service.implemention;

import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.entity.serviceSystem.SubService;
import ir.maktab.data.mapping.ExpertMap;
import ir.maktab.data.mapping.SubServiceMap;
import ir.maktab.data.repasitory.SubServiceRepository;
import ir.maktab.exception.DuplicateServiceException;
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
            throw new ObjectEntityNotFoundException(" --- SubService is null ---");
    }

    @Override
    public SubServiceDto findByName(String name) {

        return subServiceMap.createSubServiceDto(find(name));
    }

    @Override
    public SubService find(String name) {
        if (subServiceRepository.findByName(name).isPresent())
            return subServiceRepository.findByName(name).get();
        throw new ObjectEntityNotFoundException(" --- SubService is null ---");
    }

    @Override
    public SubServiceDto save(SubServiceDto subServiceDto) {
        if (find(subServiceDto.getName()) != null) {
            SubService subService = subServiceMap.createSubService(subServiceDto);
            SubService saveSubService = subServiceRepository.save(subService);
            return subServiceMap.createSubServiceDto(saveSubService);
        }
        throw new DuplicateServiceException(" --- SubService is exit for name ---");
    }

    @Override
    public void deleteSubService(String name) {
        subServiceRepository.delete(find(name));
    }


}
