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

  private String ID;
  private String NAME;
  private String CODE;
  private String MODULE_ID;
  private String ENTITY_TYPE;
  private String ENTITY_ID;
  private String ROOT_OBJECT_ID;
}
