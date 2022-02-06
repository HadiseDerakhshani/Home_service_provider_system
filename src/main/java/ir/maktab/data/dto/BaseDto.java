package ir.maktab.data.dto;


import lombok.Data;



@Data
public class BaseDto {
    private int pageNumber = 0;

    private int pageSize = 10;
}
