package hk.org.ha.kcc.its.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import hk.org.ha.kcc.its.model.BedCleansingRequest;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BedCleansingServiceRepository extends JpaRepository<BedCleansingRequest, String> {

   /* @Query(value = "select bed_cleansing_request_audit_log.id as bc_id,\n" +
            "        bed_cleansing_request_audit_log.rev_id,\n" +
            "        bed_cleansing_request_audit_log.rev_type,\n" +
            "        bed_cleansing_request_audit_log.cleaner,\n" +
            "        bed_cleansing_request_audit_log.active_flag,\n" +
            "        bed_cleansing_request_audit_log.bed_no,\n" +
            "        bed_cleansing_request_audit_log.bed_type,\n" +
            "        bed_cleansing_request_audit_log.cleaning_process,\n" +
            "        bed_cleansing_request_audit_log.cubicle_no,\n" +
            "        bed_cleansing_request_audit_log.dept_code,\n" +
            "        bed_cleansing_request_audit_log.detergent,\n" +
            "        bed_cleansing_request_audit_log.hospital_code,\n" +
            "        bed_cleansing_request_audit_log.eform_id,\n" +
            "        bed_cleansing_request_audit_log.remarks,\n" +
            "        bed_cleansing_request_audit_log.requestor_contact_no,\n" +
            "        bed_cleansing_request_audit_log.status,\n" +
            "        bed_cleansing_request_audit_log.ward_code,\n" +
            "        bed_cleansing_request_audit_log.whole_bed_cleansing,\n" +
            "        bed_cleansing_request_audit_log.requestor_id,\n" +
            "        rev_details.id as rev_detail_id, " +
            "        rev_details.action,\n" +
            "        rev_details.action_date_time,\n" +
            "        rev_details.record_id,\n" +
            "        rev_details.table_name,\n" +
            "        rev_details.username,\n" +
            "        rev_details.revision_id  FROM bed_cleansing_request_audit_log  JOIN  rev_details On bed_cleansing_request_audit_log.rev_id =rev_details.revision_id " +
            "        where bed_cleansing_request_audit_log.id=?1 order by rev_detail_id", nativeQuery = true)
    List<Object[]> getAllDtoByBCId(String Id);*/

}
