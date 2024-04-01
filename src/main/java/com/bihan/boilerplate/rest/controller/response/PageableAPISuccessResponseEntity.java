package com.bihan.boilerplate.rest.controller.response;


import lombok.*;

import java.util.List;

// This will be used for list response
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageableAPISuccessResponseEntity<T> {

    private List<T> results;
    private int page;
    private int pageSize;
}
