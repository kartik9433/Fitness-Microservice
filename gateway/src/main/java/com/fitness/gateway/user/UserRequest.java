package com.fitness.gateway.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email format")
    private String email;

    private String keycloakId;

    @NotBlank(message = "password is required")
    @Size(min = 6,message = "Password must have atleast 6 character")
    private String password;

    @NotBlank(message = "firstname should not be blank")
    private String firstname;

    @NotBlank(message = "lastname should not be blank")
    private String lastname;

}
