package com.dcorn.api.authentication;

import com.dcorn.api.user.User;
import com.dcorn.api.utils.handler.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final IAuthenticationService AUTHENTICATION_SERVICE;

    @Autowired
    public AuthenticationController(IAuthenticationService authentication_service) {
        this.AUTHENTICATION_SERVICE = authentication_service;
    }

    @PostMapping("/signUp")
    public ResponseHandler signUp(@Valid @RequestBody User user){
        AUTHENTICATION_SERVICE.create(user);
        return new ResponseHandler(HttpStatus.OK, "User registration successful!");
    }

    @PutMapping("/{id}/changePassword")
    public ResponseHandler changePassword(@PathVariable("id") int id, @Valid @RequestBody PasswordViewModel passwordViewModel) {
        AUTHENTICATION_SERVICE.changePassword(id, passwordViewModel.getOldPassword(), passwordViewModel.getNewPassword(), passwordViewModel.getNewConfirmPassword());
        return new ResponseHandler(HttpStatus.OK, "Password successfully changed!");
    }

    @PutMapping("/{id}/changeEmail")
    public ResponseHandler changeEmail(@PathVariable("id") int id, @Valid @RequestBody EmailViewModel emailViewModel) {
        AUTHENTICATION_SERVICE.changeEmail(id, emailViewModel.getOldEmail(), emailViewModel.getNewEmail(), emailViewModel.getPassword());
        return new ResponseHandler(HttpStatus.OK, "Email successfully changed!");
    }
}
