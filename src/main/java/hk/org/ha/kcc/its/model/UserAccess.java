package hk.org.ha.kcc.its.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_access")
public class UserAccess {
    @Id
    @Column(name = "corp_id", length = 10)
    private String corpId;

    @Column(length = 50)
    private String name;

    @Column(length = 10)
    private String role;

    @NotNull
    @CreatedBy
    @Column(name = "created_by", length = 20, updatable = false)
    private String createdBy;

    @NotNull
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @NotNull
    @LastModifiedBy
    @Column(name = "modified_by", length = 20)
    private String modifiedBy;

    @NotNull
    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

}
