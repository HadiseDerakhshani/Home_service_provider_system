package ir.maktab.data.mapping;

import ir.maktab.data.dto.ExpertDto;
import ir.maktab.data.model.user.Expert;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpertMap {
    private ModelMapper mapper;

    public Expert createExpert(ExpertDto expertDto) {
        return mapper.map(expertDto, Expert.class);
    }

    public ExpertDto createExpertDto(Expert expert) {
        return mapper.map(expert, ExpertDto.class);
    }
}
