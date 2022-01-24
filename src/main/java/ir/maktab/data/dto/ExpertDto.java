package ir.maktab.data.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
@Data
public class ExpertDto extends UserDto {
    @Lob
    private byte[] image;
    private int score;
    private List<SubServiceDto> serviceList = new ArrayList<>();
    private List<OrderDto> orderList = new ArrayList<>();
    private List<SuggestionDto> suggestList = new ArrayList<>();
    private List<CommentDto> commentList = new ArrayList<>();
}
