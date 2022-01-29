package ir.maktab.data.mapping;

import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.model.serviceSystem.SubService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Data
@Component

public class SubServiceMap {
    private final ModelMapper mapper;
    private final ExpertMap expertMap;

    @Autowired
    public SubServiceMap(ModelMapper mapper, @Lazy ExpertMap expertMap) {
        this.mapper = mapper;
        this.expertMap = expertMap;
    }

    public SubService createSubService(SubServiceDto subServiceDto) {
        SubService service = SubService.builder()
                .name(subServiceDto.getName())
                .price(subServiceDto.getPrice())
                .description(subServiceDto.getDescription())
                .build();
       /* if (subServiceDto.getExpertList() != null) {
            service.setExpertList(subServiceDto.getExpertList().stream().map(expertMap::createExpert)
                    .collect(Collectors.toList()));
        }*/
        return service;

    }

    public SubServiceDto createSubServiceDto(SubService subService) {
        SubServiceDto serviceDto = SubServiceDto.builder()
                .name(subService.getName())
                .price(subService.getPrice())
                .description(subService.getDescription())
                .build();
       /* if (subService.getExpertList() != null) {
            serviceDto.setExpertList(subService.getExpertList().stream().map(expertMap::createExpertDto)
                    .collect(Collectors.toList()));
        }*/
        return serviceDto;

    }
}
