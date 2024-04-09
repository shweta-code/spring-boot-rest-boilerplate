package com.intuit.exchange.rest.controller.response;


import com.intuit.exchange.rest.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@Builder
@ToString
public class UserDetailsResponse {

    private String name;

    private String email;

    private Integer credits;

    private Long id;

    public static UserDetailsResponse buildFromUserEntity(User user) {
        return UserDetailsResponse.builder()
                .credits(user.getCredits())
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

}