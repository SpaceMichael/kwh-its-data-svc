package hk.org.ha.kcc.its.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@Audited
@Table(name = "bed_cleansing_detail")
public class BedCleansingRequestDetail extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "bed_cleansing_request_id")
    private Integer bedCleansingRequestId; //  can use for case_no?
    @Column(name = "status")
    private String status; // e.g pending, overtime , overload, completed?
    /*@Column(name = "menu_id")
    private String menuId; // use id to check menu table to find request_item: bed cleansing?
    or use bedCleansingRequestId to find menu id to find the menu.title  */
    @Column(name = "active_flag")
    private Boolean activeFlag; // for De Active?
    @Column(name = "cleaner", length = 50)
    private String cleaner; // e.g Device 008? common ID for cleaner? one person one decive??
    @Lob
    @Column(name = "remarks", length = Integer.MAX_VALUE)
    private String remarks;

    // handler can refer to created by of this txt e.g Admin take order
}
