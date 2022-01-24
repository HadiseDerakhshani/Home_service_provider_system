package ir.maktab.data.mapping;

import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.model.serviceSystem.Service;
import ir.maktab.data.model.serviceSystem.SubService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ServiceMap {
    private ModelMapper mapper;

    public Service createService(ServiceDto serviceDto) {
        return mapper.map(serviceDto, Service.class);
    }

    public ServiceDto createServiceDto(Service service) {
        ServiceDto serviceDto=ServiceDto.builder()
                .name(service.getName())
                .build();
        SubServiceMap subServiceMap = new SubServiceMap();
        Set<SubServiceDto> collect = service.getSubServiceList().stream().map(subServiceMap::createSubServiceDto).collect(Collectors.toSet());
        serviceDto.setSubServiceList(collect);
        return serviceDto;
        // return mapper.map(service, ServiceDto.class);
    }
}
