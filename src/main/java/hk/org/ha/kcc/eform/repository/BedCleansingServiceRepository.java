package hk.org.ha.kcc.eform.repository;

import hk.org.ha.kcc.eform.model.BedCleansingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BedCleansingServiceRepository extends JpaRepository<BedCleansingRequest, String> {
  <T> Optional<T> findById(String id);

  void deleteById(String id);

}
