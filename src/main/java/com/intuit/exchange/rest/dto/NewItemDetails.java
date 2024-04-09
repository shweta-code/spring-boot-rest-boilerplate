package com.intuit.exchange.rest.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewItemDetails {
    private Long categoryId;
    private String description;
    private boolean isListed;
}