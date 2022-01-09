package service;

import data.dao.SubServiceDao;
import data.dto.SubServiceDto;
import data.model.serviceSystem.SubService;
import data.model.user.Expert;
import exception.IsNullObjectException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, String> subMap = new HashMap<>();
        subMap.put(name, description);
        if (findByName(subMap) != null) {
            SubService subService = SubService.builder()
                    .price(price)
                    .build();
            subService.getSubServiceMap().put(name, description);
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

    public SubService findByName(Map<String, String> subServiceMap) {
        return subServiceDao.findBySubServiceMap(subServiceMap);
    }

    public void addExpertToList(Expert expert, SubService service) {
        service.getExpertList().add(expert);
        subServiceDao.updateExpertList(service.getExpertList(), service.getId());
    }
  /*  public boolean findByName(String name) {
        String[] split = name.split(",");
        List<SubService> list = subServiceDao.findByName();
        Map.Entry<String, String> stringEntry = null;
        for (SubService subService : list) {
            stringEntry = subService.getSubServiceMap().
                    entrySet().stream().filter(m -> m.getKey().equals(split[0])).findAny().orElse(null);
        }
        if (stringEntry != null)
            return true;
        return false;
    }*/
}
