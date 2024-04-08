package com.bihan.boilerplate.rest.entity.baseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class VersionedBaseEntity extends BaseEntity implements Serializable {

    @Version
    @Column(name = "version")
    private Long version;
}
