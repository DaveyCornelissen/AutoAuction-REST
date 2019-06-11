package com.dcorn.api.user;

import com.dcorn.api.auction.Auction;
import com.dcorn.api.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @NotEmpty
    @Email
    private String email;

    @Size(min=2, max = 20, message = "Your first name it to short! It needs to be at least 2 characters long!")
    private String firstName;

    @Size(min=2, max = 20, message = "Your last name it to short! It needs to be at least 2 characters long!")
    private String lastName;

    @Size(min=8, message = "Your password it to short! It needs to be at least 8 characters long!")
    private String password;

    @Transient
    @Size(min=8, message = "Your confirm-password is to short! It needs to be at least 8 characters long!")
    private String confirmPassword;

    @NotBlank
    @Size(min=8, message = "Your address it to short!")
    private String address;

    @NotBlank
    @Size(min=3, message = "Your state it to short!")
    private String state;

    @NotBlank
    @Size(min=3, message = "Your country it to short!")
    private String country;

    @Past
    private Date birthDay;

    @OneToMany
    @JsonIgnore
    private List<Auction> auctionList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

}
