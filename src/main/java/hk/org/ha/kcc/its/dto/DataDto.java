package hk.org.ha.kcc.its.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataDto {

  private List<FormDto> forms;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private DetailDto details;
}
