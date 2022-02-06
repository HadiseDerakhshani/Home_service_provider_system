package ir.maktab.data.mapping;

import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.entity.serviceSystem.SubService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component

public class SubServiceMap {
    private final ModelMapper mapper;

    @Autowired
    public SubServiceMap(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public SubService createSubService(SubServiceDto subServiceDto) {
        SubService service = SubService.builder()
                .name(subServiceDto.getName())
                .price(subServiceDto.getPrice())
                .description(subServiceDto.getDescription())
                .build();

        return service;

    }

    public SubServiceDto createSubServiceDto(SubService subService) {
        SubServiceDto serviceDto = SubServiceDto.builder()
                .name(subService.getName())
                .price(subService.getPrice())
                .description(subService.getDescription())
                .build();
        return serviceDto;

    }
}
