package com.engine.game.player;

import com.engine.entities.GameObject;
import com.engine.entities.Transform;
import com.engine.listeners.KeyListener;
import com.engine.listeners.MouseListener;
import com.engine.main.Window;
import com.engine.renderengine.models.TexturedModel;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;

public class Player extends GameObject {

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;

    public Player(String name, TexturedModel model, Transform transform, float scale) {
        super(name, model, transform, scale);
    }

    public void move(float dt) {
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * dt, 0);
        float distance = currentSpeed * dt;
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, upwardsSpeed * dt, dz);
        upwardsSpeed += GRAVITY * dt;
        if(super.getPosition().y <= TERRAIN_HEIGHT) {
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = TERRAIN_HEIGHT;
        }
    }

    private void checkInputs() {
        if(KeyListener.isKeyPressed(GLFW_KEY_W)) {
            this.currentSpeed = RUN_SPEED;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
            this.currentSpeed = -RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if(KeyListener.isKeyPressed(GLFW_KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        if(KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
            jump();
        }
    }

    private void jump() {
        if(!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }
}
