package hk.org.ha.kcc.eform.model;


import lombok.*;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "constant")
public class Constant extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="service_id")
    private Integer service; // form service table id

    @Column(name="constant_code", length = 50)
    private String constantType; // e.g 清潔工序(Cleaning process) -清潔劑(detergent)

    @Column(name="constant_value", length = 50)
    private String constantValue; // e.g 清潔工序(Cleaning process) ->床,床簾,環境 -清潔劑(detergent)>漂白水
}
