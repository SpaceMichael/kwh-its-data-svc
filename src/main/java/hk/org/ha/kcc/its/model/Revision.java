package hk.org.ha.kcc.its.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, exclude = "modifiedEntity")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@RevisionEntity(CustomRevisionListener.class)
@Table(name = "rev_info")
public class Revision extends DefaultRevisionEntity{

    @Builder.Default
    @OneToMany(mappedBy="revision", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<ModifiedEntityTypeEntity> modifiedEntity = new HashSet<ModifiedEntityTypeEntity>();

    public void addModifiedEntityType(ModifiedEntityTypeEntity modifiedEntity) {
        modifiedEntity.setRevision(this);
        this.modifiedEntity.add(modifiedEntity);
    }
}