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
  /*
   * "start":1692758526.195635, "finish":1692758526.320605, "duration":0.1249699592590332,
   * "processing":0.10502386093139648, "date_start":"2023-08-23T10:42:06+08:00",
   * "date_finish":"2023-08-23T10:42:06+08:00"
   */
  private Double start;
  private Double finish;
  private Double duration;
  private Double processing;
  private String date_start;
  private String date_finish;
}
