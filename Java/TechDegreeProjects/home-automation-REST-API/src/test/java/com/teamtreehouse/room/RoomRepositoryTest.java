package com.teamtreehouse.room;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest
public class RoomRepositoryTest {

    @Autowired
    RoomRepository rooms;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void saveRequiresAuthTest() throws Exception {
        rooms.save(newRoomTest());
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void saveIterableRequiresAuthTest() throws Exception {
        rooms.save(newRoomsTest());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser
    public void saveRequiresAdminTest() throws Exception {
        rooms.save(newRoomTest());

    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser
    public void saveAllRequiresAdminTest() throws Exception {
        rooms.save(newRoomsTest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void saveWorksAsAdmin() throws Exception {
        rooms.save(newRoomTest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void saveAllWorksAsAdmin() throws Exception {
        rooms.save(newRoomsTest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void findByNameContainingTest() throws Exception {
        rooms.deleteAll();
        Room room = newRoomTest();

        String roomNameSubstring = room.getName().substring(0,3);
        rooms.save(room);

        assertTrue(
                rooms.findByNameContaining(roomNameSubstring, new PageRequest(0,10)).getContent().get(0).getName().equals(room.getName()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void findByAreaLessThanTest() throws Exception {
        rooms.deleteAll();
        List<Room> testRooms = newRoomsTest();

        int maxAreaSearch;
        if (testRooms.get(0).getArea() < testRooms.get(1).getArea()) {
            maxAreaSearch = testRooms.get(0).getArea();
        } else {
            maxAreaSearch = testRooms.get(1).getArea();
        }

        rooms.save(testRooms);

        assertEquals(1, rooms.findByAreaLessThan(maxAreaSearch + 1, new PageRequest(0, 10)).getContent().size());
    }

    private Room newRoomTest() {
        return new Room("A Room", 100);
    }

    private List<Room> newRoomsTest() throws Exception {
        return Arrays.asList(
                new Room("Some Room", 200),
                new Room("Some Other Room", 300)
        );
    }
}