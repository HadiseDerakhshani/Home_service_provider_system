package ir.maktab.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubServiceDto {

    private double price;
    @Pattern(regexp = "^[a-zA-Z]+$", message = "SubService is not alphabet")
    private String name;
    private String description;
  private List<ExpertDto> expertList = new ArrayList<>();
}
