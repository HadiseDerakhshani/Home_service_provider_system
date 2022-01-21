package ir.maktab.service;

import ir.maktab.data.dao.SubServiceDao;
import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.mapping.ExpertMap;
import ir.maktab.data.mapping.SubServiceMap;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.exception.ObjectEntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class SubServiceServiceImpl implements SubServiceService {
    private final SubServiceMap subServiceMap;
    private final ExpertMap expertMap;
    private final SubServiceDao subServiceDao;

    @Override
    public void save(SubService subService) {
        subServiceDao.save(subService);
    }

    @Override
    public SubService createSubService(String name, String description, double price) {

        if (findByName(name) == null) {
            SubService subService = SubService.builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .build();
            return subService;
        }
        throw new ObjectEntityNotFoundException("------- SubService is exited --------");
    }

    @Override
    public List<SubServiceDto> findAll() {
        List<SubService> listSubService = subServiceDao.findAll();
        if (listSubService != null) {
            List<SubServiceDto> listSubServiceDto = new ArrayList<>();
            for (SubService subService : listSubService) {
                listSubServiceDto.add(subServiceMap.createSubServiceDto(subService));
            }
            return listSubServiceDto;
        } else
            throw new ObjectEntityNotFoundException(" SubService is null");
    }

    @Override
    public SubService findByName(String name) {
        return subServiceDao.findByName(name).get();
    }

    @Override
    public void addExpertToList(ExpertDto expertDto, SubService service) {
        service.getExpertList().add(expertMap.createExpert(expertDto));
        subServiceDao.updateExpertList(service.getId(), service.getExpertList());
    }

    @Override
    public void deleteSubService(String name) {
        subServiceDao.delete(findByName(name));
    }
}
