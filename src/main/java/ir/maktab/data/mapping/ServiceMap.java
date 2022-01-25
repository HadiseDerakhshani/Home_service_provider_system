package ir.maktab.data.mapping;

import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.model.serviceSystem.Service;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
@Data
@Component

public class ServiceMap {
    private final ModelMapper mapper;
    private  final SubServiceMap subServiceMap;
@Autowired
    public ServiceMap(ModelMapper mapper,@Lazy SubServiceMap subServiceMap) {
        this.mapper = mapper;
        this.subServiceMap = subServiceMap;
    }

    public Service createService(ServiceDto serviceDto) {
        return mapper.map(serviceDto, Service.class);
    }

    public ServiceDto createServiceDto(Service service) {
        ServiceDto serviceDto = ServiceDto.builder()
                .name(service.getName())
                .build();
      if(service.getSubServiceList().size()!=0){
          serviceDto.setSubServiceList( service.getSubServiceList().stream().map(subServiceMap::createSubServiceDto)
                .collect(Collectors.toSet()));
    }
        return serviceDto;
    }
}
