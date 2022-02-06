package ir.maktab.data.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor

public class BaseDto {
    private int pageNumber = 0;

    private int pageSize = 10;
}
