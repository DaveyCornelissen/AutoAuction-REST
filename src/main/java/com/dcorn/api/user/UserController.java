package com.dcorn.api.user;

import com.dcorn.api.utils.handler.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final IUserService USER_SERVICE;

    @Autowired
    public UserController(IUserService user_service) {
        this.USER_SERVICE = user_service;
    }

    @GetMapping(path="/{id}")
    public ResponseHandler get(@PathVariable("id") int id) {
         return new ResponseHandler(HttpStatus.OK, USER_SERVICE.read(id));
    }

    @PutMapping(path="/{id}/update")
    public ResponseHandler update(@PathVariable("id") int id, @Valid @RequestBody UserViewModel model) {
        User user = new User();
        user.setId(id);
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setAddress(model.getAddress());
        user.setState(model.getState());
        user.setCountry(model.getCountry());

        USER_SERVICE.update(user);
        return new ResponseHandler(HttpStatus.OK, "User updated successfully!");
    }

    @DeleteMapping(path="{id}/delete")
    public ResponseHandler delete(@PathVariable("id") int id, @NotEmpty String password) {
        User model = new User();
        model.setId(id);
        model.setPassword(password);
        USER_SERVICE.delete(model);
        return new ResponseHandler(HttpStatus.OK, "User deleted successfully");
    }

}
