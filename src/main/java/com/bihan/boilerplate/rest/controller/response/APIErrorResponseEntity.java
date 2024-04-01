package com.bihan.boilerplate.rest.controller.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class APIErrorResponseEntity {

    private boolean success = false;
    private String message;
    private List<String> errors;

}
