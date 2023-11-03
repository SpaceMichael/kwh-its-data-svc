package hk.org.ha.kcc.its.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "constant")
public class Constant extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "service_id")
  private Integer service; // form service table id

  @Column(name = "constant_code", length = 50)
  private String constantType; // e.g 清潔工序(Cleaning process) -清潔劑(detergent)

  @Column(name = "constant_value", length = 50)
  private String constantValue; // e.g 清潔工序(Cleaning process) ->床,床簾,環境 -清潔劑(detergent)>漂白水
}
