package hk.org.ha.kcc.its.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "service_name", length = 50)
    private String serviceName; //
    @Column(name = "active_flag")
    private Boolean activeFlag;

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
