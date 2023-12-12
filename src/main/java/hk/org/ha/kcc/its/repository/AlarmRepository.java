package hk.org.ha.kcc.its.repository;

import hk.org.ha.kcc.its.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
}
