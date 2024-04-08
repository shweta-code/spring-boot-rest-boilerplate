package com.bihan.boilerplate.rest.entity.baseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class VersionedBaseEntity extends BaseEntity implements Serializable {

    @Version
    @Column(name = "version")
    private Long version;
}
