package ir.maktab.data.dto;

import ir.maktab.data.model.serviceSystem.SubService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ServiceDto {
    private String name;
    private Set<SubServiceDto> subServiceList = new HashSet<>();

}
