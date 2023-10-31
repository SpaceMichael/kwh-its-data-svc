package hk.org.ha.kcc.eform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Lob;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BedCleansingRequestDto {


    private String id;
    private String hospitalCode;    //e.g KWH
    private String dept;            //e.g M&G
    private String ward;            // e.g 12BMN
    private String cubicle;         // e.g 1
    private Boolean bedChecked;   // e.g all clean in the room  全房大抹
    private String bedNo;       // e.g 01 or 1-02
    private String bedType;    // e.g 普通床 掛牌床
    private Boolean cleanBedChecked;  // e.g 清潔工序 ->床
    private Boolean cleanBedCurtainsChecked; // e.g 清潔工序 ->床簾
    private Boolean cleanEnvChecked; // e.g 清潔工序 ->環境
    private Boolean bleach; // e.g 漂白水
    private Boolean tricel;  // e.g tricel
    private String remarks;
    private String status;  // apply or pending or completed
    private String requestor; // e,g cmw002  // use token check?
    private String requestorName; // e,g chan siu man
    private String requestorContactNo;
    private String Cleaner; // e.g Mobile Device 008?
    private Boolean activeFlag; //
}
