package hk.org.ha.kcc.its.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BedCleansingRequestAuditDto {

    private String bcId;
    private String revId;
    private String revType;
    private String cleaner;
    private Boolean activeFlag;
    private String bedNo;
    private String bedType;
    private String cleaningProcess;
    private String cubicleNo;
    private String deptCode;
    private String detergent;
    private String hospitalCode;
    private Integer eformId;
    private String remarks;
    private Integer requestorContactNo;
    private String status;
    private String wardCode;
    private Boolean wholeBedCleansing;
    private String requestorId;
    private String revDetailId;
    private String action;
    private String actionDateTime;
    private String recordId;
    private String tableName;
    private String username;
    private String revisionId;

    /*
     bed_cleansing_request_audit_log.id as bc_id,
        bed_cleansing_request_audit_log.rev_id,
        bed_cleansing_request_audit_log.rev_type,
        bed_cleansing_request_audit_log.cleaner,
        bed_cleansing_request_audit_log.active_flag,
        bed_cleansing_request_audit_log.bed_no,
        bed_cleansing_request_audit_log.bed_type,
        bed_cleansing_request_audit_log.cleaning_process,
        bed_cleansing_request_audit_log.cubicle_no,
        bed_cleansing_request_audit_log.dept_code,
        bed_cleansing_request_audit_log.detergent,
        bed_cleansing_request_audit_log.hospital_code,
        bed_cleansing_request_audit_log.menu_id,
        bed_cleansing_request_audit_log.remarks,
        bed_cleansing_request_audit_log.requestor_contact_no,
        bed_cleansing_request_audit_log.status,
        bed_cleansing_request_audit_log.ward_code,
        bed_cleansing_request_audit_log.whole_bed_cleansing,
        bed_cleansing_request_audit_log.requestor_id,
        rev_details.id as rev_detail_id,
        rev_details.action,
        rev_details.action_date_time,
        rev_details.record_id,
        rev_details.table_name,
        rev_details.username,
        rev_details.revision_id

     */

}
