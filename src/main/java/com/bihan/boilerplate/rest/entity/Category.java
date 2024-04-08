package com.bihan.boilerplate.rest.entity;

import com.bihan.boilerplate.rest.entity.baseEntity.VersionedBaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "category")
public class Category extends VersionedBaseEntity {

    @NotNull(message = "Category name is required")
    private String name;

    @NotNull(message = "description is required")
    private String description;

    // TODO - we can check hibernate entity validation as there is in course entity class
}

// TODO -
// Remove TODOs
// 1. Look at the int lengths of ids of all tables, if it is possible to enforce small int and all in category
// 2. Correct lengths of varchar. - Enforce length of varchars
// 3. Enum for category
// Transaction Log Request
// Image urls for item from s3, created_at, modified_at, created_by, modified_by
// Create aTO which will be , Entities are never rendered, DTO which will be modeled by service from entity
// 5. Ask chatgpt for hibernate entities example