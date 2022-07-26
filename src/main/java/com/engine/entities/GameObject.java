package com.engine.entities;

import com.engine.game.components.Transform;
import com.engine.renderengine.models.TexturedModel;

public class GameObject {
    public String name;
    public TexturedModel model;
    public Transform transform;

    public GameObject(String name, TexturedModel model, Transform transform) {
        this.name = name;
        this.model = model;
        this.transform = transform;
    }

    @Deprecated
    public void increasePosition(float dx, float dy, float dz) {
        this.transform.getPosition().x +=dx;
        this.transform.getPosition().y +=dy;
        this.transform.getPosition().z +=dz;
    }
}
