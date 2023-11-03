package hk.org.ha.kcc.its.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {

  /*
   * { "ID": "95691", "NAME": "23-08-2023 Britix24 Testing", "CODE": null, "MODULE_ID": "disk",
   * "ENTITY_TYPE": "group", "ENTITY_ID": "2917", "ROOT_OBJECT_ID": "472279" },
   */
  private String ID;
  private String NAME;
  private String CODE;
  private String MODULE_ID;
  private String ENTITY_TYPE;
  private String ENTITY_ID;
  private String ROOT_OBJECT_ID;
}
