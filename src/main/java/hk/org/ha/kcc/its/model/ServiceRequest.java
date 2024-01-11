package hk.org.ha.kcc.its.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import hk.org.ha.kcc.common.data.PrefixedSequenceIdGenerator;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// @Audited
@Table(name = "service_request")
public class ServiceRequest extends Auditable {
    @Id
    @Column(length = 12)
    @GeneratedValue(generator = "equip_usage_request_generator")
    @GenericGenerator(name = "equip_usage_request_generator", strategy = "hk.org.ha.kcc.common.data.PrefixedSequenceIdGenerator",
            parameters = {@Parameter(name = PrefixedSequenceIdGenerator.SEQUENCE_PARAM, value = "equip_usage_request_seq"), @Parameter(name = PrefixedSequenceIdGenerator.PREFIX_PARAM, value = "SR-")})
    private String id;
    @Column(name = "case_no", length = 12)
    private String caseNo; // e.g. HN123454677 patient strap
    @Column(name = "location", length = 20)
    private String location; // e.g. KWH
    @Column(name = "cubicle_no", length = 10)
    private String cubicleNo; // e.g. 1
    @Column(name = "bed_no", length = 5)
    private String bedNo; // e.g. 1
    @Column(name = "service_name", length = 50)
    private String serviceName; // e.g. 1
    @Column(name = "service_id")
    private Integer serviceId;
    @Column(name = "remarks", length = 1000)
    private String remarks; // e.g. 1
    @Column(name = "alarm_id")
    private String alarmId; // from HKT api alarm id
    @Column(name = "success")
    private Boolean success;
    @Column(name = "escalation_Id")
    private Integer escalationId;
    @Lob
    @Column(name = "error_message")
    private String errorMessage;
    /*
    success
     */

}
