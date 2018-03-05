package com.teamtreehouse.control;

import com.teamtreehouse.user.DetailsService;
import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest
public class ControlEventHandlerTest {

    @Autowired
    private ControlRepository controls;

    @Autowired
    private UserRepository users;

    @Before
    @WithMockUser(roles = {"ADMIN"})
    public void setup() {
        users.save(new User("someDifferentUser","password", new String[] {"ROLE_USER","ROLE_ADMIN"}));
        controls.save(new Control("testControl", 0));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void addLastModifiedByBasedOnLoggedInUserReturnsCorrectUserTest() throws Exception {
        Control control = controls.findOne(1L);
        control.setLastModifiedBy(users.findByUsername("someDifferentUser"));
        controls.save(control);

        assertTrue(controls.findOne(1L).getLastModifiedBy().getUsername().equals("someDifferentUser"));
    }
}