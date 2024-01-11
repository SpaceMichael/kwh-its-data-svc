package hk.org.ha.kcc.its.model;

import lombok.*;

import javax.persistence.*;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// @Audited
@Table(name = "service")
public class Services extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // e.g 1? or use Prefixed?
    @Column(name = "service_code", length = 50)
    private String serviceCode; // PAED_HOUSEMAN_T1,  PAED_HOUSEMAN_RESP_NEUR
    @Column(name = "service_name", length = 50)
    private String serviceName; // Houseman (Team 1), Houseman (Respi + Neuro)
    @Column(name = "alarm_type", length = 50)
    private String alarmType; // Houseman (Team 1), Houseman (Respi + Neuro)


    /*@ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceRequest> serviceRequestList = new ArrayList<>();
    private void addServiceRequest(ServiceRequest serviceRequest) {
        serviceRequestList.add(serviceRequest);
        serviceRequest.setServices(this);
    }

    private void removeServiceRequest(ServiceRequest serviceRequest) {
        serviceRequestList.remove(serviceRequest);
        serviceRequest.setServices(null);
    }*/


}
