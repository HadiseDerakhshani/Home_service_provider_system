package ir.maktab.data.dto;

import jdk.jfr.ContentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
@Data
public class ExpertDto extends UserDto {

    private byte[] photo;
    private int score;
    private List<SubServiceDto> serviceList = new ArrayList<>();
    private List<OrderDto> orderList = new ArrayList<>();
    private List<SuggestionDto> suggestList = new ArrayList<>();
    private List<CommentDto> commentList = new ArrayList<>();
}
