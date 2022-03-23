package com.engine.camera;

import com.engine.game.player.Player;
import com.engine.listeners.KeyListener;
import com.engine.listeners.MouseListener;
import com.engine.main.Window;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0, 10, 0);
    private float pitch = 20, yaw, roll;

    private static final float MAX_ZOOM = 10;

    private Player player;

    public Camera(Player player) {
        this.player = player;
    }

    public void move() {
        calculateZoom();
        calculateCameraMovement();
        //calculatePitch();
        //calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);

        /*
        if(MouseListener.mouseButtonDown(0)) {
            glfwSetInputMode(Window.get().glfwWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        } else if(MouseListener.mouseButtonDown(1)) {
            glfwSetInputMode(Window.get().glfwWindow, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }
         */
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

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticalDistance;
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        float zoomLevel = MouseListener.getScrollY() * 0.1f;
        distanceFromPlayer -= zoomLevel;
        if(distanceFromPlayer < MAX_ZOOM) {
            distanceFromPlayer = 0;
        }
    }

    @Deprecated
    private void calculatePitch() {
        if(MouseListener.mouseButtonDown(0)) {
            float pitchChange = MouseListener.getDy() * 0.2f;
            pitch -= pitchChange;
        }
    }

    @Deprecated
    private void calculateAngleAroundPlayer() {
        if(MouseListener.mouseButtonDown(1)) {
            float angleChange = MouseListener.getDx() * 0.2f;
            angleAroundPlayer += angleChange;
        }
    }

    private void calculateCameraMovement() {
        if(MouseListener.mouseButtonDown(1)) {
            float pitchChange = MouseListener.getDy() * 0.2f;
            pitch -= pitchChange;
            float angleChange = MouseListener.getDx() * 0.2f;
            angleAroundPlayer += angleChange;
        }
    }
}
