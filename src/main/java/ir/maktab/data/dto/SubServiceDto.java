package ir.maktab.data.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubServiceDto {
    private double price;
    private String name;
    private String description;
    private List<ExpertDto> expertList = new ArrayList<>();
}
