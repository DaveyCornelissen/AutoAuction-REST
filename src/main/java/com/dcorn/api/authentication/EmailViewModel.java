package com.dcorn.api.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailViewModel {

    @Email
    private String oldEmail;

    @Email
    private String newEmail;

    @Size(min=8, message = "Your password it to short! It needs to be at least 8 characters long!")
    private String password;

}
