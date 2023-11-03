package hk.org.ha.kcc.its.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailDataDto {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String dept;

  private String ward;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String cubicle;

  private String bedNo;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean bedChecked;
}
