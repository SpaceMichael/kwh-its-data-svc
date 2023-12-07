package hk.org.ha.kcc.its.repository;

import hk.org.ha.kcc.its.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Services, String> {
}
