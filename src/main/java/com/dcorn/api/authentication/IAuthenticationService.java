package com.dcorn.api.authentication;

import com.dcorn.api.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAuthenticationService extends UserDetailsService {
    void create(User object);

    void changePassword(int id, String oldPassword, String newPassword, String newConfirmPassword);

    void changeEmail(int id, String oldEmail, String newEmail, String password);
}
