package hk.org.ha.kcc.its.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeDto {

  private Double start;
  private Double finish;
  private Double duration;
  private Double processing;
  private String date_start;
  private String date_finish;
}
