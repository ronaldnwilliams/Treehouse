package com.teamtreehouse.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest
public class ControlRepositoryTest {

    @Autowired
    private ControlRepository controls;

    private Control newControlTest() {
        return new Control("TestControl", 0);
    }

    private List<Control> newControlsTest() {
        return Arrays.asList(
                new Control("1TestControl", 1),
                new Control("2TestControl", 2)
        );
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void saveRequiresAuthTest() throws Exception {
        controls.save(newControlTest());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void saveIterableRequiresAuthTest() throws Exception {
        controls.save(newControlsTest());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser
    public void saveRequiresAdminTest() throws Exception {
        controls.save(newControlTest());

    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser
    public void saveAllRequiresAdminTest() throws Exception {
        controls.save(newControlsTest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void saveWorksAsAdmin() throws Exception {
        controls.save(newControlTest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void saveAllWorksAsAdmin() throws Exception {
        controls.save(newControlsTest());
    }
}