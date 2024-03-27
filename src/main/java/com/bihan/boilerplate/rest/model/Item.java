package com.bihan.boilerplate.rest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Category name is required")
    private Category category;

    @NotNull(message = "description is required")
    private String description;

    @NotNull
    @Column(name = "is_listed")
    private boolean isListed;

    // To add timestamp fields here
}
