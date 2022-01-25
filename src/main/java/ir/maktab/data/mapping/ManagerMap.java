package ir.maktab.data.mapping;

import ir.maktab.data.dto.ManagerDto;
import ir.maktab.data.model.user.Manager;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
@Data
@Component
@RequiredArgsConstructor
public class ManagerMap {
    private ModelMapper mapper;

    private Manager createManager(ManagerDto managerDto) {
        return mapper.map(managerDto, Manager.class);
    }

    private ManagerDto createManagerDto(Manager manager) {
        return mapper.map(manager, ManagerDto.class);
    }
}
