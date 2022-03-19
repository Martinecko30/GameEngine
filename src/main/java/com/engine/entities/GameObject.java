package com.engine.entities;

import com.engine.renderengine.models.TexturedModel;
import com.engine.renderengine.textures.ModelTexture;
import org.joml.Vector3f;

public class GameObject {

    private String name;
    private TexturedModel model;
    private Transform transform;
    private float scale;

    public GameObject(String name, TexturedModel model, Transform transform, float scale) {
        this.name = name;
        this.model = model;
        this.transform = transform;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.transform.position.x +=dx;
        this.transform.position.y +=dy;
        this.transform.position.z +=dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.transform.rotX +=dx;
        this.transform.rotY +=dy;
        this.transform.rotZ +=dz;
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
        return transform.position;
    }

    public void setPosition(Vector3f position) {
        this.transform.position = position;
    }

    public float getRotX() {
        return transform.rotX;
    }

    public void setRotX(float rotX) {
        this.transform.rotX = rotX;
    }

    public float getRotY() {
        return transform.rotY;
    }

    public void setRotY(float rotY) {
        this.transform.rotY = rotY;
    }

    public float getRotZ() {
        return transform.rotZ;
    }

    public void setRotZ(float rotZ) {
        this.transform.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
