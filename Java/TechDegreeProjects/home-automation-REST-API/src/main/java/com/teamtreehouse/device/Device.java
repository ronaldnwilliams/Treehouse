package com.teamtreehouse.device;

import com.teamtreehouse.control.Control;
import com.teamtreehouse.room.Room;
import com.teamtreehouse.core.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Device extends BaseEntity {
    @NotNull
    private String name;
    @ManyToOne
    private Room room;
    @OneToMany(mappedBy = "device")
    private List<Control> controls;

    protected Device() {
        super();
        controls = new ArrayList<>();
    }

    public Device(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Control> getControls() {
        return controls;
    }

    public void setControls(List<Control> controls) {
        this.controls = controls;
    }
}
