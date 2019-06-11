package com.dcorn.api.user;

import com.dcorn.api.utils.handler.BadRequestHandler;
import com.dcorn.api.utils.handler.InternalServerErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final IUserRepository USER_REPOSITORY;
    private final BCryptPasswordEncoder BCRYPT_PASSWORD_ENCODER;

    @Autowired
    public UserService(IUserRepository user_repository, BCryptPasswordEncoder bCrypt_password_encoder) {
        this.USER_REPOSITORY = user_repository;
        this.BCRYPT_PASSWORD_ENCODER = bCrypt_password_encoder;
    }

    @Override
    public User create(User object) {
        //No created needed the package "Authentication" creates a user on signUp!
        return null;
    }

    @Override
    public User read(Integer id) {
        if (id == 0)
            throw new BadRequestHandler("No valid user id provided!");

        User user = USER_REPOSITORY.findById(id).orElseThrow(() -> new BadRequestHandler("No user found with id:" + id));
        //set password to null
        user.setPassword(null);

        return user;
    }

    @Override
    public void update(User object) {
        if (USER_REPOSITORY.save(object) == null)
            throw new InternalServerErrorHandler("Something went wrong with updating the user!");
    }

    @Override
    public void delete(User object) {
        User removeObject = USER_REPOSITORY.findById(object.getId()).orElseThrow(() ->
                new BadRequestHandler("No user was found with that id!"));

        if (!BCRYPT_PASSWORD_ENCODER.matches(object.getPassword(), removeObject.getPassword()))
            throw new BadRequestHandler("The confirm payload does not match with any user!");

        USER_REPOSITORY.delete(removeObject);
    }
}
