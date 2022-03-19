package com.engine.scenes;

import com.engine.camera.Camera;
import com.engine.entities.GameObject;
import com.engine.renderengine.enviroment.Light;
import com.engine.main.Window;
import com.engine.renderengine.models.RawModel;
import com.engine.renderengine.models.TexturedModel;
import com.engine.objects.OBJLoader;
import com.engine.scenemanagment.Scene;
import com.engine.renderengine.textures.ModelTexture;
import com.engine.terrains.Terrain;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class LevelEditorScene extends Scene {

    Camera camera = new Camera();

    RawModel model;
    ModelTexture texture;
    TexturedModel texturedModel;
    Light light;
    Terrain terrain, terrain2;

    List<GameObject> gameObjects = new ArrayList<>();

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        model = OBJLoader.loadObjModel("dragon", Window.get().loader);
        texture = new ModelTexture(Window.get().loader.loadTexture("white"));
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        texturedModel = new TexturedModel(model, texture);
        GameObject dragon = new GameObject("dragon", texturedModel, new Vector3f(0,-4,-20),0,0,0,1);
        gameObjects.add(dragon);
        GameObject fern = new GameObject(
                "fern",
                new TexturedModel(
                OBJLoader.loadObjModel("fern", Window.getLoader()),
                        new ModelTexture(Window.getLoader().loadTexture("fern"))),
                new Vector3f(20,0,0),0,0,0,0.8f);
        fern.getModelTexture().setTransparency(true);
        gameObjects.add(fern);

        GameObject grass = new GameObject(
                "grass",
                new TexturedModel(
                        OBJLoader.loadObjModel("grassModel", Window.getLoader()),
                        new ModelTexture(Window.getLoader().loadTexture("grass"))),
                new Vector3f(16,0,12),0,0,0,1f);
        grass.getModelTexture().setTransparency(true);
        gameObjects.add(grass);

        GameObject stall = new GameObject(
                "grass",
                new TexturedModel(
                        OBJLoader.loadObjModel("stall", Window.getLoader()),
                        new ModelTexture(Window.getLoader().loadTexture("stallTexture"))),
                new Vector3f(3,0,6),0,180,0,1f);
        gameObjects.add(stall);

        light = new Light(new Vector3f(0, 0, 20), new Vector3f(1f, 1, 1f));
        terrain = new Terrain(0, 0, Window.getLoader(), new ModelTexture(Window.getLoader().loadTexture("grass_floor"))); //TODO: TEST MISSING TEXTURE
        terrain2 = new Terrain(1, 0, Window.getLoader(), new ModelTexture(Window.getLoader().loadTexture("grass_floor"))); //TODO: TEST MISSING TEXTURE
    }

    @Override
    public void update(float dt) {
        camera.move();

        Window.getRenderer().processTerrain(terrain);
        Window.getRenderer().processTerrain(terrain2);

        for(GameObject gameObject : gameObjects) {
            if(gameObject.getName().equals("dragon")) {
                gameObject.increaseRotation(0, 0.2f, 0);
            }
            Window.getRenderer().processGameObject(gameObject);
        }
        Window.getRenderer().render(light, camera);

        if(Window.debug) {
            System.out.println((1.0f / dt) + " fps");
            //System.out.println(camera.getPosition());
        }
    }
}