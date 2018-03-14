package com.example.motion;


import com.example.accessory.Accessory;

import java.util.ArrayList;
import java.util.List;

public abstract class MotionDetector {
    public List<Accessory> accessories = new ArrayList<>();

    public void addAccessory(Accessory accessory) {
        accessories.add(accessory);
    }

    public void removeAccessory(Accessory accessory) {
        accessories.remove(accessory);
    }

    public abstract void onMotionDetected();

    public void simulateMotion() {
        onMotionDetected();
    }
}
