package com.engine.camera;

import com.engine.listeners.KeyListener;
import com.engine.listeners.MouseListener;
import com.engine.main.Window;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch, yaw, roll;

    private float multiplier = 2;

    public Camera() {

    }

    public void move() {
        if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            multiplier = 5;
        } else {
            multiplier = 2;
        }

        if(KeyListener.isKeyPressed(GLFW_KEY_W)) {
            position.z -=0.02f * multiplier;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_D)) {
            position.x +=0.02f * multiplier;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_S)) {
            position.z +=0.02f * multiplier;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_A)) {
            position.x -=0.02f * multiplier;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
            position.y +=0.02f * multiplier;
        }
        if(KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            position.y -=0.02f * multiplier;
        }

        if(MouseListener.mouseButtonDown(0)) {
            glfwSetInputMode(Window.get().glfwWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        } else if(MouseListener.mouseButtonDown(1)) {
            glfwSetInputMode(Window.get().glfwWindow, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
