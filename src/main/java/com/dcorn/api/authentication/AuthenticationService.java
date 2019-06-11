package com.dcorn.api.authentication;

import com.dcorn.api.role.Role;
import com.dcorn.api.role.RoleType;
import com.dcorn.api.user.User;
import com.dcorn.api.role.IRoleRepository;
import com.dcorn.api.user.IUserRepository;
import com.dcorn.api.utils.handler.BadRequestHandler;
import com.dcorn.api.utils.handler.InternalServerErrorHandler;
import com.dcorn.api.utils.security.CustomPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final IUserRepository USER_REPOSITORY;
    private final IRoleRepository ROLE_REPOSITORY;
    private final BCryptPasswordEncoder BCRYPT_PASSWORD_ENCODER;

    @Autowired
    public AuthenticationService(IUserRepository user_repository, IRoleRepository role_repository, BCryptPasswordEncoder bCrypt_Password_Encoder) {
        this.USER_REPOSITORY = user_repository;
        this.ROLE_REPOSITORY = role_repository;
        this.BCRYPT_PASSWORD_ENCODER = bCrypt_Password_Encoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = USER_REPOSITORY.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email));

        return CustomPrincipal.create(user);
    }

    @Override
    public void create(User object) {
        //confirm if new passwords match
        PasswordMatcher(object.getPassword(), object.getConfirmPassword());

        if (this.USER_REPOSITORY.findByEmail(object.getEmail()).isPresent())
            new BadRequestHandler("Cant create account! Email already exist!");

        //encrypt the password
        object.setPassword(BCRYPT_PASSWORD_ENCODER.encode(object.getConfirmPassword()));

        Role role = this.ROLE_REPOSITORY.findRoleByType(RoleType.USER).orElseThrow(() ->
                new InternalServerErrorHandler("Error with accessing the Roles! please try again or contact the system management!"));

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        object.setRoles(roles);

        if (this.USER_REPOSITORY.save(object) == null)
            throw new InternalServerErrorHandler("Oops! something went wrong, User is not created please try again!");
    }

    @Override
    public void changePassword(int id, String oldPassword, String newPassword, String newConfirmPassword) {
        User user = USER_REPOSITORY.findById(id).orElseThrow(() -> new BadRequestHandler("No valid user id provided!"));

        //confirm if old passwords match
        PasswordMatcher(oldPassword, user.getPassword());
        //confirm if new passwords match
        PasswordMatcher(newPassword, newConfirmPassword);

        //encrypt the new passwordViewModel
        user.setPassword(BCRYPT_PASSWORD_ENCODER.encode(newPassword));

        if (this.USER_REPOSITORY.save(user) == null)
            throw new InternalServerErrorHandler("Oops! something went wrong, Password is not changed please try again!");
    }

    @Override
    public void changeEmail(int id, String oldEmail, String newEmail, String password) {
        User user = USER_REPOSITORY.findByIdAndEmail(id, oldEmail).orElseThrow(() -> new BadRequestHandler("No user found with provided email/id!"));

        //confirm if provided password matches with stored one
        PasswordMatcher(user.getPassword(), password);

        //check if new email is available
        if (USER_REPOSITORY.findByEmail(newEmail) != null)
            throw new BadRequestHandler("New email already exist!");

        user.setEmail(newEmail);
        if (USER_REPOSITORY.save(user) == null)
            throw new InternalServerErrorHandler("Oops! something went wrong, Email is not changed please try again!");
    }

    /**
     * This protected method is to check if passwords are equal
     * @param rawOrOldPwd encrypt password or the current/old one
     * @param EncryptOrCurrentPwd no-encrypted password or new provided password
     */
    protected void PasswordMatcher(String rawOrOldPwd, String EncryptOrCurrentPwd) {
        if (!BCRYPT_PASSWORD_ENCODER.matches(rawOrOldPwd, EncryptOrCurrentPwd) == !rawOrOldPwd.equals(EncryptOrCurrentPwd))
            throw new BadRequestHandler("passwords are not the same!");
    }
}
