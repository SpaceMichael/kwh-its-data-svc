package hk.org.ha.kcc.its.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import hk.org.ha.kcc.its.model.BedCleansingRequest;

public interface BedCleansingServiceRepository extends JpaRepository<BedCleansingRequest, String> {

}
