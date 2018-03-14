package com.example.accessory;

import com.teamtreehouse.techdegree.hardware.Camera;

public class CameraAdapter implements Accessory {
    private Camera adaptee;
    private int numberOfPhotos;

    public CameraAdapter(Camera camera, int numberOfPhotos) {
        this.adaptee = camera;
        this.numberOfPhotos = numberOfPhotos;
    }

    public void setNumberOfPhotos(int numberOfPhotos) {
        this.numberOfPhotos = numberOfPhotos;
    }

    @Override
    public void activate() {
        adaptee.snapPhotos(numberOfPhotos);
    }
}
