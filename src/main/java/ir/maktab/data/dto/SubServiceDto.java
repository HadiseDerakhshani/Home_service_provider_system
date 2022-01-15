package ir.maktab.data.dto;

import ir.maktab.data.model.user.Expert;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Data
public class SubServiceDto {
    private double price;
    private String name;
    private String description;
    private List<ExpertDto> expertList = new ArrayList<>();
}
