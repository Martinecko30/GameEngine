package com.engine.entities;

import com.engine.renderengine.models.TexturedModel;
import com.engine.renderengine.textures.ModelTexture;
import org.joml.Vector3f;

public class GameObject {

    private String name;
    private TexturedModel model;
    private Vector3f position;
    private float rotX;
    private float rotY;
    private float rotZ;
    private float scale;

    public GameObject(String name, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.name = name;
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.position.x +=dx;
        this.position.y +=dy;
        this.position.z +=dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX +=dx;
        this.rotY +=dy;
        this.rotZ +=dz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public ModelTexture getModelTexture() {
        return this.model.getModelTexture();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
