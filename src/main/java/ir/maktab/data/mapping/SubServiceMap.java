package ir.maktab.data.mapping;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.model.serviceSystem.SubService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubServiceMap {
    private ModelMapper mapper;

    public SubService createSubService(SubServiceDto subServiceDto) {
        return mapper.map(subServiceDto, SubService.class);
    }

    public SubServiceDto createSubServiceDto(SubService subService) {
        SubServiceDto serviceDto=SubServiceDto.builder()
                .name(subService.getName())
                .description(subService.getDescription())
                .build();
        ExpertMap expertMap=new ExpertMap();
        List<ExpertDto> collect = subService.getExpertList().stream().map(expertMap::createExpertDto).collect(Collectors.toList());
       serviceDto.setExpertList(collect);
       return serviceDto;
        // return mapper.map(subService, SubServiceDto.class);
    }
}
