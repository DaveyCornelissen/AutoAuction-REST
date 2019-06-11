package com.dcorn.api.user;

import com.dcorn.api.auction.IAuctionRepository;
import com.dcorn.api.utils.handler.BadRequestHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static abstract class UserServiceContextConfiguration {

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }
        @Bean
        public abstract IUserRepository userRepository();

        @Bean
        public IUserService service() {
            return new UserService(userRepository(), bCryptPasswordEncoder());
        }
    }

    @Autowired
    private IUserService service;

    @MockBean
    private IUserRepository repository;

    private List<User> testUsers;

    private User testUser;

    @Before
    public void Setup() {
        this.testUsers = new ArrayList();
        testUsers.add(new User(1, "Test@testmail.com", "Test", "van Tester", "12345678", "12345678", "TestLaan", "TestStad", "TestLand", new Date(), null, null));
        testUsers.add(new User(1, "Test@testmail.com", "changedName", "van Tester", "12345678", "12345678", "TestLaan", "TestStad", "TestLand", new Date(), null, null));
        testUsers.add(new User(1, "Test@testmail.com", "Test", "van Tester", "1234567890", "1234567890", "TestLaan", "TestStad", "TestLand", new Date(), null, null));

        //read test
        Mockito.when(this.repository.findById(this.testUsers.get(0).getId())).thenReturn(Optional.ofNullable(this.testUsers.get(0)));
        //update test
        Mockito.when(this.repository.save(this.testUsers.get(1))).thenReturn(this.testUsers.get(1));
    }

    @Test
    public void read() {
        this.testUser = testUsers.get(0);
        User user = this.service.read(testUser.getId());
        assertEquals(this.testUser, user);
    }

    @Test
    public void update() {
        this.testUser = testUsers.get(1);
        try {
            this.service.update(testUser);
        } catch(Exception e) {
            fail();
        }
    }

    @Test(expected = BadRequestHandler.class)
    public void deleteByIdFail() {
        this.testUser = testUsers.get(0);
        Mockito.when(this.repository.findById(testUser.getId())).thenReturn(Optional.empty());
        this.service.delete(testUser);
    }

    @Test(expected = BadRequestHandler.class)
    public void deleteWrongPasswordFail() {
        this.testUser = testUsers.get(0);
        Mockito.when(this.repository.findById(testUser.getId())).thenReturn(Optional.ofNullable(this.testUsers.get(2)));
        this.service.delete(testUser);
    }
}
