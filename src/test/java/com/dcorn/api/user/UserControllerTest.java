package com.dcorn.api.user;

import com.dcorn.api.utils.security.JwtAuthenticationFilter;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IUserService service;

    private List<User> testUsers;

    private User testUser;

    private String token;


    @Before
    public void setUp() throws Exception {
        token = JwtAuthenticationFilter.MakeFakeAuthentication();

        testUsers = new ArrayList<>();
        //User model
        testUsers.add(new User(1, "Test@testmail.com", "Test", "van Tester", "12345678", "12345678", "TestLaan", "TestStad", "TestLand", new Date(), null, null));
        //updated model
        testUsers.add(new User(1, "Test@testmail.com", "Test", "van der Tester", "12345678", "12345678", "Testpad", "TestDorp", "TestLand", new Date(), null, null));
        //User with No password
        testUsers.add(new User(1, "Test@testmail.com", "Test", "van Tester", "", "", "TestLaan", "TestStad", "TestLand", new Date(), null, null));
        //account with no email
        testUsers.add(new User(1, "", "Test", "van Tester", "12345678", "12345678", "TestLaan", "TestStad", "TestLand", new Date(), null, null));
    }

    @Test
    public void getSuccess() throws Exception {
        this.testUser = this.testUsers.get(0);

        given(service.read(this.testUser.getId())).willReturn(this.testUser);

        mvc.perform(get(String.format("/api/user/%o", this.testUser.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void updateSuccess() throws Exception {
        this.testUser = this.testUsers.get(1);

        UserViewModel viewModel = new UserViewModel(this.testUser.getFirstName(), this.testUser.getLastName(),
                this.testUser.getAddress(), this.testUser.getState(), this.testUser.getCountry(), this.testUser.getBirthDay());

        mvc.perform(put(String.format("/api/user/%o/update", this.testUser.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new Gson().toJson(viewModel)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteSuccess() throws Exception {
        this.testUser = this.testUsers.get(0);

        mvc.perform(delete(String.format("/api/user/%o/delete", this.testUser.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("password", this.testUser.getPassword()))
                .andExpect(status().isOk());
    }



}
