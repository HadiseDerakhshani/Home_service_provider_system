package data.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class SubServiceDto {
    private double price;
    private Map<String, String> subServiceMap = new HashMap<>();
}
