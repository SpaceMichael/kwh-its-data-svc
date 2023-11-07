package hk.org.ha.kcc.its.repository;

import hk.org.ha.kcc.its.model.ModifiedEntityTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ModifiedEntityTypeEntityRepository extends JpaRepository<ModifiedEntityTypeEntity, Integer>, JpaSpecificationExecutor<ModifiedEntityTypeEntity> {
}
