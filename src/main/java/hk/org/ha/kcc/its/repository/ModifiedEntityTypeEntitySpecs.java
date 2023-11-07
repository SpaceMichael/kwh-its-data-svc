package hk.org.ha.kcc.its.repository;

import hk.org.ha.kcc.its.model.ModifiedEntityTypeEntity;
import hk.org.ha.kcc.its.model.ModifiedEntityTypeEntity_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

public class ModifiedEntityTypeEntitySpecs {
    public static Specification<ModifiedEntityTypeEntity> matchFilters(
            String user, LocalDateTime startEventDatetime, LocalDateTime endEventDatetime
    ) {
        return (modifiedEntity, cq, cb) -> {
            Predicate predicate = cb.conjunction();
            if (user != null) {
                predicate = cb.and(predicate, cb.equal(modifiedEntity.get(ModifiedEntityTypeEntity_.USERNAME), user));
            }
            if (startEventDatetime != null && endEventDatetime == null) {
                Predicate predicateForGte = cb.greaterThanOrEqualTo(modifiedEntity.get(ModifiedEntityTypeEntity_.ACTION_DATE_TIME), startEventDatetime);
                // Predicate: OR [ot_date] IS NULL
                Predicate predicateForNull = cb.isNull(modifiedEntity.get(ModifiedEntityTypeEntity_.ACTION_DATE_TIME));
                predicate = cb.and(predicate, cb.or(predicateForGte, predicateForNull));
            } else if (startEventDatetime == null && endEventDatetime != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(modifiedEntity.get(ModifiedEntityTypeEntity_.ACTION_DATE_TIME), endEventDatetime));
            } else if (startEventDatetime != null && endEventDatetime != null) {
                predicate = cb.and(predicate, cb.between(modifiedEntity.get(ModifiedEntityTypeEntity_.ACTION_DATE_TIME), startEventDatetime, endEventDatetime));
            }
            return predicate;
        };
    }
}
