package ir.maktab.data.dto;

import ir.maktab.data.model.enums.UserRole;
import ir.maktab.data.model.enums.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
@Data
public class ExpertDto extends UserDto{

    private byte[] image;
    @Pattern(regexp ="^[1-9]?$|10", message = "Score is not between 1-10")
    private int score;
    private List<SubServiceDto> serviceList = new ArrayList<>();
    private List<OrderDto> orderList = new ArrayList<>();
    private List<SuggestionDto> suggestList = new ArrayList<>();
    private List<CommentDto> commentList = new ArrayList<>();
}
