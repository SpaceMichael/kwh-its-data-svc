package hk.org.ha.kcc.its.repository;

import hk.org.ha.kcc.its.model.ServiceAlarmSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceAlarmSenderRepository extends JpaRepository<ServiceAlarmSender, Integer> {

    @Query("SELECT s FROM ServiceAlarmSender s WHERE ?1 LIKE s.serviceCode AND s.locationCode =?2 AND s.senderId IS NOT NULL")
    List<ServiceAlarmSender> findByServiceCodeLike(String serviceCode , String locationCode);
}
