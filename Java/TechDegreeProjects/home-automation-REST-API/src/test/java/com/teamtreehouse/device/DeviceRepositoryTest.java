package com.teamtreehouse.device;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest
public class DeviceRepositoryTest {
    
    @Autowired
    private DeviceRepository devices;

    private Device newDeviceTest() {
        return new Device("TestDevice");
    }

    private List<Device> newDevicesTest() {
        return Arrays.asList(
                new Device("1TestDevice"),
                new Device("2TestDevice")
        );
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser
    public void saveRequiresAdminTest() throws Exception {
        devices.save(newDeviceTest());

    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser
    public void saveAllRequiresAdminTest() throws Exception {
        devices.save(newDevicesTest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void saveWorksAsAdmin() throws Exception {
        devices.save(newDeviceTest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void saveAllWorksAsAdmin() throws Exception {
        devices.save(newDevicesTest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void findByNameContainingTest() throws Exception {
        devices.deleteAll();
        Device device = newDeviceTest();

        String deviceNameSubstring = device.getName().substring(0,3);
        devices.save(device);

        assertTrue(
                devices.findByNameContaining(deviceNameSubstring, new PageRequest(0,10)).getContent().get(0).getName().equals(device.getName()));
    }
}