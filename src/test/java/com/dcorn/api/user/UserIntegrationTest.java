package com.dcorn.api.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private IUserRepository repository;

    private List<User> testUsers;

    private User testUser;

    @Before
    public void Setup() {
        this.testUsers = new ArrayList();
        testUsers.add(new User(1, "Test@testmail.com", "Test", "van Tester", "12345678",
                "12345678", "TestLaan", "TestStad", "TestLand", new Date(), null, null));
    }

    @Test
    public void findByEmail() {
        this.testUser = this.testUsers.get(0);
        //save the test account in the test database
        entityManager.merge(testUser);
        entityManager.flush();

        Optional<User> existence = repository.findByEmail(this.testUser.getEmail());
        assertNotNull(existence.get());
    }

    @Test
    public void findByIdAndEmail() {
        this.testUser = this.testUsers.get(0);
        //save the test account in the test database
        entityManager.merge(testUser);
        entityManager.flush();

        Optional<User> existence = repository.findByIdAndEmail(this.testUser.getId(), this.testUser.getEmail());
        assertNotNull(existence.get());
    }
}
