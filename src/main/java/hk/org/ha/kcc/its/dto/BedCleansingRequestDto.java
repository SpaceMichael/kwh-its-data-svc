package hk.org.ha.kcc.its.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BedCleansingRequestDto {

    private String id;
    private String hospitalCode; // e.g KWH
    private String dept; // e.g M&G
    private String ward; // e.g 12BMN
    private String cubicle; // e.g 1
    private Boolean wholeBed; // e.g all clean in the room 全房大抹
    private String bedNo; // e.g 01 or 1-02
    private String bedType; // e.g 普通床 掛牌床
    private String cleaningProcess; // e.g 清潔工序 ->床,床簾,環境  bed, curtain, env
    private String detergent; // e.g 清潔劑(detergent)->漂白水, tricel  =  real name : detergent,tricel
    private String status; // apply or pending or completed
    private Integer requestorContactNo;
    private String cleaner; // e.g Mobile Device 008?
    private String remarks;
    private Long eformId;
    private String requestor;
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;

}
