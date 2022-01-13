package ir.maktab.service;

import ir.maktab.data.dao.SubServiceDao;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.model.serviceSystem.SubService;
import ir.maktab.data.model.user.Expert;
import ir.maktab.exception.IsNullObjectException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class SubServiceService {
    private final ModelMapper mapper = new ModelMapper();
    private final SubServiceDao subServiceDao;


    public void save(SubService subService) {
        subServiceDao.save(subService);
    }

    public SubServiceDto createSubServiceDto(SubService subService) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(subService, SubServiceDto.class);
    }

    public SubService createSubService(String name, String description, double price) {

        if (findByName(name) == null) {
            SubService subService = SubService.builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .build();
            return subService;
        }
        throw new IsNullObjectException("------- SubService is exited --------");
    }

    public List<SubServiceDto> findAll() {
        List<SubService> listSubService = subServiceDao.findAll();
        if (listSubService != null) {
            List<SubServiceDto> listSubServiceDto = new ArrayList<>();
            for (SubService subService : listSubService) {
                listSubServiceDto.add(createSubServiceDto(subService));
            }
            return listSubServiceDto;
        } else
            throw new IsNullObjectException(" SubService is null");
    }

    public SubService findByName(String name) {
        return subServiceDao.findByName(name);
    }

    public void addExpertToList(Expert expert, SubService service) {
        service.getExpertList().add(expert);
        subServiceDao.updateExpertList(service.getId(), service.getExpertList());
    }

    public void deleteSubService(String name) {
        subServiceDao.delete(findByName(name));
    }
}
