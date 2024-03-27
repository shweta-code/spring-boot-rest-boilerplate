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
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Category name is required")
    private String name;

    @NotNull(message = "description is required")
    private String description;

    // Todo add timestamp fields here
}

// TODO -
// 1. Look at the int lengths of ids of all tables, if it is possible to enforce small int and all in category
// 2. Correct lengths of varchar. - Enforce length of varchars
// 3. Enum for category
// Transaction Log Request
// 3. Create a request interface - for Exchange Request, Borrow Request, BuyRequest