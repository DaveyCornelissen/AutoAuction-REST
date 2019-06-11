package com.dcorn.api.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordViewModel {

    @Size(min=8, message = "Your old password it to short! It needs to be at least 8 characters long!")
    private String oldPassword;

    @Size(min=8, message = "Your new password it to short! It needs to be at least 8 characters long!")
    private String newPassword;

    @Size(min=8, message = "Your new confirm password it to short! It needs to be at least 8 characters long!")
    private String newConfirmPassword;

}
