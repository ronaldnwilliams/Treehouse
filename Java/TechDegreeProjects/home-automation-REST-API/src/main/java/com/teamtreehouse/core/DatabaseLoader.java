package com.teamtreehouse.core;

import com.teamtreehouse.user.User;
import com.teamtreehouse.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements ApplicationRunner {
    private final UserRepository users;

    @Autowired
    public DatabaseLoader(UserRepository users) {
        this.users = users;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = new User("ronaldW", "password", new String[] {"ROLE_USER", "ROLE_ADMIN"});
        users.save(user);
        users.save(new User("notAdmin", "password", new String[] {"ROLE_USER"}));
    }
}
