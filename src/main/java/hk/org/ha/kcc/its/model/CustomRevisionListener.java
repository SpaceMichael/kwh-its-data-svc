package hk.org.ha.kcc.its.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;
import org.springframework.data.domain.AuditorAware;


public class CustomRevisionListener implements EntityTrackingRevisionListener {

    private final AuditorAware<String> auditorAware;

    public CustomRevisionListener(AuditorAware<String> auditorAware) {
        this.auditorAware = auditorAware;
    }

    @Override
    public void newRevision(Object revisionEntity) {
    }

    @Override
    public void entityChanged(Class entityClass, String entityName, Serializable entityId, RevisionType revisionType, Object revisionEntity) {
        ((Revision) revisionEntity).addModifiedEntityType(
                ModifiedEntityTypeEntity.builder()
                        .username(auditorAware.getCurrentAuditor().isEmpty() ? "SYSTEM" : auditorAware.getCurrentAuditor().get())
                        .actionDateTime(LocalDateTime.now())
                        .action(revisionType.name())
                        .tableName(entityName.replace("hk.org.ha.kcc.its.data.model.", ""))
                        .recordId(entityId.toString())
                        .build()
        );
    }
}
