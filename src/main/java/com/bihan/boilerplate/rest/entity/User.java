package com.bihan.boilerplate.rest.entity;

import com.bihan.boilerplate.rest.entity.baseEntity.VersionedBaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User extends VersionedBaseEntity {

    @NotNull(message = "User name is required")
    private String name;

    @NotNull(message = "email is required")
    private String email;

    @NotNull(message = "password is required")
    private String password;

    @NotNull(message = "credits is required")
    @Min(value = 0, message = "Credits must be greater than or equal to 0")
    private Integer credits;
}
