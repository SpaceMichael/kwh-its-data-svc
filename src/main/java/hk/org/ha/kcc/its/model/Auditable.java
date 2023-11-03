package hk.org.ha.kcc.its.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

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
