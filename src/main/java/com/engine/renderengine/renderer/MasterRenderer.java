package com.engine.renderengine.renderer;

import com.engine.camera.Camera;
import com.engine.entities.GameObject;
import com.engine.main.Window;
import com.engine.renderengine.enviroment.Light;
import com.engine.renderengine.models.TexturedModel;
import com.engine.renderengine.shaders.StaticShader;
import com.engine.renderengine.shaders.TerrainShader;
import com.engine.terrains.Terrain;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class MasterRenderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;

    private static final float RED = 0.5f;
    private static final float GREEN = 0.5f;
    private static final float BLUE = 0.5f;

    private Matrix4f projectionMatrix;

    private StaticShader shader = new StaticShader();
    private GORenderer renderer;// = new GORenderer(shader, projectionMatrix);

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();

    private Map<TexturedModel, List<GameObject>> gameObjects = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();

    public MasterRenderer() {
        enableCulling();
        createProjectionMatrix();
        renderer = new GORenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    public static void enableCulling() {
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
    }

    public static void disableCulling() {
        glDisable(GL_CULL_FACE);
    }

    public void render(Light sun, Camera camera) {
        prepare();
        shader.start();
        shader.loadSkyColor(RED, GREEN, BLUE);
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        renderer.render(gameObjects);
        shader.stop();
        terrainShader.start();
        terrainShader.loadSkyColor(RED, GREEN, BLUE);
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        terrains.clear();
        gameObjects.clear();
    }

    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }

    public void processGameObject(GameObject gameObject) {
        TexturedModel gameObjectModel = gameObject.model;
        List<GameObject> batch = gameObjects.get(gameObjectModel);
        if(batch!=null) {
            batch.add(gameObject);
        } else {
            List<GameObject> newBatch = new ArrayList<>();
            newBatch.add(gameObject);
            gameObjects.put(gameObjectModel, newBatch);
        }
    }

    public void cleanUp() {
        shader.cleanUp();
        terrainShader.cleanUp();
    }

    public void prepare() {
        glEnable(GL_DEPTH_TEST);
        glClearColor(RED, GREEN, BLUE, 1);
        glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    }

    private void createProjectionMatrix(){
        float aspectRatio = (float) Window.getWidth() / (float) Window.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix.m33(0);
    }
}
