package hk.org.ha.kcc.its.repository;

import hk.org.ha.kcc.its.model.ServiceAckReceiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceAckReceiverRepository extends JpaRepository<ServiceAckReceiver, Integer> {

    @Query("SELECT s FROM ServiceAckReceiver s WHERE ?1 LIKE s.serviceCode AND s.locationCode =?2 AND s.escalationId IS NOT NULL")
    List<ServiceAckReceiver> findByServiceCodeLike(String serviceCode , String locationCode);
}
