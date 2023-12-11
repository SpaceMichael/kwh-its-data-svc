package hk.org.ha.kcc.its.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EamDto {

    private Long eamNo; // 1824496
    private String SerNo;// E09-123567890123
    private String Model; // ELITDESK PU 8000G5 SFF
    private String BelongTo;// KWH Ward 8A
    private String Type; // MED
    private String CaseNo; // e.g. HN123454677 patient strap

}
