package hk.org.ha.kcc.its.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageListRespDto {

  private List<ResultDto> results;
  private Integer total;
  private TimeDto time;

  /*
   * "result": [ { "ID": "95691", "NAME": "23-08-2023 Britix24 Testing", "CODE": null, "MODULE_ID":
   * "disk", "ENTITY_TYPE": "group", "ENTITY_ID": "2917", "ROOT_OBJECT_ID": "472279" }, { "ID":
   * "95572", "NAME": "KWH @Work Britix24 API Testing", "CODE": null, "MODULE_ID": "disk",
   * "ENTITY_TYPE": "group", "ENTITY_ID": "2823", "ROOT_OBJECT_ID": "471066" } ], "total": 2,
   * "time": { "start": 1692758526.195635, "finish": 1692758526.320605, "duration":
   * 0.1249699592590332, "processing": 0.10502386093139648, "date_start":
   * "2023-08-23T10:42:06+08:00", "date_finish": "2023-08-23T10:42:06+08:00" }
   */
}
