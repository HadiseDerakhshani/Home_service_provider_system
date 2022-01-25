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
        return mapper.map(subServiceDto, SubService.class);
    }

    public SubServiceDto createSubServiceDto(SubService subService) {
        SubServiceDto serviceDto = SubServiceDto.builder()
                .name(subService.getName())
                .description(subService.getDescription())
                .build();
        if (subService.getExpertList().size() != 0) {
            serviceDto.setExpertList(subService.getExpertList().stream().map(expertMap::createExpertDto)
                    .collect(Collectors.toList()));
        }
        return serviceDto;

    }
}
