package ir.maktab.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ServiceDto {
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Service is not alphabet")
    private String name;
    private Set<SubServiceDto> subServiceList = new HashSet<>();

}
