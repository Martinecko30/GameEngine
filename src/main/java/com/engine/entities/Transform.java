package com.engine.entities;

import org.joml.Vector3f;

public class Transform {
    public Vector3f position = new Vector3f(0, 0, 0);
    public float rotX = 0, rotY = 0, rotZ = 0;

    public Transform(float X, float Y, float Z) {
        this.position.x = X;
        this.position.y = Y;
        this.position.z = Z;
    }

    public Transform(float posX, float posY, float posZ, float rotX, float rotY, float rotZ) {
        this.position.x = posX;
        this.position.y = posY;
        this.position.z = posZ;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
    }

    public float getPosX() {
        return position.x;
    }

    public float getPosY() {
        return position.y;
    }

    public float getPosZ() {
        return position.z;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }
}