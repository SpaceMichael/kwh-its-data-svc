package hk.org.ha.kcc.its.repository;

import hk.org.ha.kcc.its.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, String> {

    @Query(value = "SELECT @@hostname", nativeQuery = true)
    String gethostname();
}
