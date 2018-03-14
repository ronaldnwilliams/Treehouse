package com.teamtreehouse.techdegree;

import com.example.accessory.Horn;
import com.example.accessory.Strobe;
import com.teamtreehouse.techdegree.hardware.Camera;
import com.example.accessory.CameraAdapter;
import com.teamtreehouse.techdegree.hardware.FrightMachine;

public class Main {

    public static void main(String[] args) {
        Horn horn = new Horn();
        Strobe stobe = new Strobe();
        Camera camera = new Camera();
        CameraAdapter cameraAdapter = new CameraAdapter(camera, 5);
        FrightMachine machine = new FrightMachine();
        machine.addAccessory(horn);
        machine.addAccessory(stobe);
        machine.addAccessory(cameraAdapter);
        machine.simulateMotion();
    }
}
