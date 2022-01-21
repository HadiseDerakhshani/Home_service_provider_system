package ir.maktab.data.mapping;

import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.model.serviceSystem.SubService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubServiceMap {
    private ModelMapper mapper;

    public SubService createSubService(SubServiceDto subServiceDto) {
        return mapper.map(subServiceDto, SubService.class);
    }

    public SubServiceDto createSubServiceDto(SubService subService) {
        return mapper.map(subService, SubServiceDto.class);
    }
}
