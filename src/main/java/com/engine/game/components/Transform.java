package com.engine.game.components;

import org.joml.Vector3f;

public class Transform {
    private Vector3f position = new Vector3f(0, 0, 0);
    private Vector3f rotation = new Vector3f(0, 0, 0);
    private Vector3f scale = new Vector3f(1, 1, 1);

     @Deprecated
    public Transform(float X, float Y, float Z) {
        this.getPosition().x = X;
        this.getPosition().y = Y;
        this.getPosition().z = Z;
    }

    @Deprecated
    public Transform(float posX, float posY, float posZ, float rotX, float rotY, float rotZ) {
        this.getPosition().x = posX;
        this.getPosition().y = posY;
        this.getPosition().z = posZ;
        this.getRotation().x = rotX;
        this.getRotation().y = rotY;
        this.getRotation().z = rotZ;
    }

    @Deprecated
    public Transform(float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ) {
        this.getPosition().x = posX;
        this.getPosition().y = posY;
        this.getPosition().z = posZ;
        this.getRotation().x = rotX;
        this.getRotation().y = rotY;
        this.getRotation().z = rotZ;
        this.getScale().x = scaleX;
        this.getScale().y = scaleY;
        this.getScale().z = scaleZ;
    }

    public Transform(Vector3f pos, Vector3f rot, Vector3f scale) {
        this.getPosition().x = pos.x;
        this.getPosition().y = pos.y;
        this.getPosition().z = pos.z;
        this.getRotation().x = rot.x;
        this.getRotation().y = rot.y;
        this.getRotation().z = rot.z;
        this.getScale().x = scale.x;
        this.getScale().y = scale.y;
        this.getScale().z = scale.z;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        getRotation().x +=dx;
        getRotation().y +=dy;
        getRotation().z +=dz;
    }

    public void increasePosition(float dx, float dy, float dz) {
        position.x +=dx;
        position.y +=dy;
        position.z +=dz;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}