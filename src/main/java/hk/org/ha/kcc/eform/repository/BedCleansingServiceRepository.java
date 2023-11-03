package hk.org.ha.kcc.eform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import hk.org.ha.kcc.eform.model.BedCleansingRequest;

public interface BedCleansingServiceRepository extends JpaRepository<BedCleansingRequest, String> {

}
