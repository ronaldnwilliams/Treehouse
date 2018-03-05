package com.teamtreehouse.control;

import com.teamtreehouse.device.Device;
import com.teamtreehouse.core.BaseEntity;
import com.teamtreehouse.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Control extends BaseEntity {
    @NotNull
    private String name;
    @ManyToOne
    private Device device;
    private int value;
    @ManyToOne
    private User lastModifiedBy;

    protected Control() {
        super();
    }

    public Control(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
