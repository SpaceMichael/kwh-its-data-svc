package hk.org.ha.kcc.its.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BedCleansingDashBoardDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer total;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer Pending;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer Process;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer Completed;
    private List<BedCleansingRequestDto> bedCleansingRequestList;
}
