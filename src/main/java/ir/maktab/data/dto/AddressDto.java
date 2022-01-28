package ir.maktab.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class AddressDto {
    //  @Pattern(regexp = "^[a-zA-Z]+$", message = "city is not alphabet")
    private String city;
    //  @Pattern(regexp = "^[a-zA-Z]+$", message = "street is not alphabet")
    private String street;
    // @Pattern(regexp = "[0-9]+$", message = "invalid plaque")
    private int plaque;
}
