package com.teamtreehouse.room;

import com.teamtreehouse.device.Device;
import com.teamtreehouse.core.BaseEntity;
import com.teamtreehouse.user.User;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room extends BaseEntity {
    @NotNull
    private String name;
    @Max(1000)
    private int area;
    @OneToMany(mappedBy = "room")
    private List<Device> devices;
    @OneToMany
    private List<User> administrators;

    protected Room() {
        super();
        devices = new ArrayList<>();
        administrators = new ArrayList<>();
    }

    public Room(String name, int area) {
        this();
        this.name = name;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public List<User> getAdministrators() {
        return administrators;
    }

    public void addAdministrator(User administrator) {
        administrators.add(administrator);
    }
}
