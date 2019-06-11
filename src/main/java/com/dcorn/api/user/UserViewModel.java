package com.dcorn.api.user;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserViewModel {

    @Size(min=2, max = 20, message = "Your first name it to short! It needs to be at least 2 characters long!")
    private String firstName;

    @Size(min=2, max = 20, message = "Your last name it to short! It needs to be at least 2 characters long!")
    private String lastName;

    @NotBlank
    @Size(min=3, message = "Your address it to short!")
    private String address;

    @NotBlank
    @Size(min=3, message = "Your state it to short!")
    private String state;

    @NotBlank
    @Size(min=3, message = "Your country it to short!")
    private String country;

    //@Past
    private Date birthDay;
}
