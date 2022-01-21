package ir.maktab.data.mapping;

import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.model.serviceSystem.Service;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceMap {
    private ModelMapper mapper;

    public Service createService(ServiceDto serviceDto) {
        return mapper.map(serviceDto, Service.class);
    }

    public ServiceDto createServiceDto(Service service) {
        return mapper.map(service, ServiceDto.class);
    }
}
