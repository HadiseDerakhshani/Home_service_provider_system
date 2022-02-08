package ir.maktab.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
public class OrderFilterDto extends BaseDto {
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date endDate;
    private String service;
    private String subService;
    private String status;
}
