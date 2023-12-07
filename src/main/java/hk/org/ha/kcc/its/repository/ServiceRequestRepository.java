package hk.org.ha.kcc.its.repository;

import hk.org.ha.kcc.its.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, String> {
}
