package hk.org.ha.kcc.its.model;

import hk.org.ha.kcc.common.data.PrefixedSequenceIdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Audited
@Table(name = "service_request")
public class ServiceRequest extends Auditable {

    @Id
    @Column(length = 12)
    @GeneratedValue(generator = "service_request_generator")
    @GenericGenerator(name = "service_request_generator", strategy = "hk.org.ha.kcc.common.data.PrefixedSequenceIdGenerator",
            parameters = {@Parameter(name = PrefixedSequenceIdGenerator.SEQUENCE_PARAM, value = "service_request_seq"), @Parameter(name = PrefixedSequenceIdGenerator.PREFIX_PARAM, value = "SR-")})
    private String id;
    @Column(name = "case_no", length = 12)
    private String caseNo; // e.g. HN123454677 patient strap
    @Column(name = "location", length = 20)
    private String location; // e.g. KWH
    @Column(name = "cubicle_no", length = 10)
    private String cubicleNo; // e.g. 1
    @Column(name = "bed_no", length = 5)
    private String bedNo; // e.g. 1
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
    @Column(name = "sender_Id")
    private String senderId;
    @Lob
    @Column(name = "error_message")
    private String errorMessage;
    @Column(name = "ack_by")
    private String ack_by;
    @Column(name = "ack_date")
    private LocalDateTime ack_date;



}
