package service;

import data.dao.SubServiceDao;
import data.dto.SubServiceDto;
import data.model.serviceSystem.SubService;
import data.model.user.Expert;
import exception.IsNullObjectException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubServiceService {
    SubServiceDao subServiceDao;

    public SubServiceService(SubServiceDao subServiceDao) {
        this.subServiceDao = subServiceDao;
    }

    public void save(SubService subService) {
        subServiceDao.save(subService);
    }

    public SubServiceDto createSubServiceDto(SubService subService) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(subService, SubServiceDto.class);
    }

    public SubService createSubService(String name, String description, double price) {


        if (findByName(name) != null) {
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
        subServiceDao.save(service);
        //  subServiceDao.updateExpertList(service.getExpertList(), service.getId());
    }

    public void deleteSubService(String name) {
        subServiceDao.delete(findByName(name));
    }
}
