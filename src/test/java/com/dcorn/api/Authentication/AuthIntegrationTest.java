package com.dcorn.api.Authentication;

import com.dcorn.api.user.IUserRepository;
import com.dcorn.api.user.User;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private IUserRepository repository;

    private List<User> testUsers;

    private User testUser;

    @Before
    public void Setup() {
        this.testUsers = new ArrayList();
        testUsers.add(new User(1, "Test@testmail.com", "Test", "van Tester", "12345678", "12345678", "TestLaan",
                "TestStad", "TestLand", new Date(), null, null));
    }
}
