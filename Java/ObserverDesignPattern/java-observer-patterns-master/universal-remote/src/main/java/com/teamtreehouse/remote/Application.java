package com.teamtreehouse.remote;

import com.teamtreehouse.remote.devices.AppleTV;
import com.teamtreehouse.remote.devices.BluRayPlayer;
import com.teamtreehouse.remote.devices.Stereo;

public class Application {
    public static void main(String[] args) {
        RemoteControl rc = new RemoteControl();
        BluRayPlayer bluRay = new BluRayPlayer();
        Stereo stereo = new Stereo();
        AppleTV appleTV = new AppleTV();
        rc.addDevice(bluRay);
        rc.addDevice(stereo);
        rc.addDevice(appleTV);
        rc.pressPowerButton();
        rc.pressPowerButton();
    }
}
