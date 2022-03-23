package com.engine.scenes;

import com.engine.camera.Camera;
import com.engine.entities.GameObject;
import com.engine.entities.Transform;
import com.engine.game.player.Player;
import com.engine.objects.ModelData;
import com.engine.objects.OBJFileLoader;
import com.engine.renderengine.enviroment.Light;
import com.engine.main.Window;
import com.engine.renderengine.models.RawModel;
import com.engine.renderengine.models.TexturedModel;
import com.engine.objects.OBJLoader;
import com.engine.renderengine.textures.TerrainTexture;
import com.engine.renderengine.textures.TerrainTexturePack;
import com.engine.scenemanagment.Scene;
import com.engine.renderengine.textures.ModelTexture;
import com.engine.terrains.Terrain;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelEditorScene extends Scene {

    private Camera camera;
    private Light light;

    private List<Terrain> terrains = new ArrayList<>();
    private List<GameObject> gameObjects = new ArrayList<>();

    // Terrain stuff

    private TerrainTexture backgroundTexture = new TerrainTexture(Window.getLoader().loadTexture("grassy"));
    private TerrainTexture rTexture = new TerrainTexture(Window.getLoader().loadTexture("dirt"));
    private TerrainTexture gTexture = new TerrainTexture(Window.getLoader().loadTexture("pinkFlowers"));
    private TerrainTexture bTexture = new TerrainTexture(Window.getLoader().loadTexture("path"));

    private TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
    private TerrainTexture blendMap = new TerrainTexture(Window.getLoader().loadTexture("blendMap"));

    //*********************

    Player player;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        /*
        ModelData dragonData = OBJFileLoader.loadOBJ("dragon");
        GameObject dragon = new GameObject("dragon",
                new TexturedModel(
                        Window.getLoader().loadToVAO(dragonData.getVertices(), dragonData.getTextureCoords(), dragonData.getNormals(), dragonData.getIndices()),
                        new ModelTexture(Window.getLoader().loadTexture("white"))),
                new Transform(0, 0, -20, 0, 0, 0),1);
        dragon.getModelTexture().setShineDamper(10);
        dragon.getModelTexture().setReflectivity(1);
        gameObjects.add(dragon);

         */

        ModelData fernData = OBJFileLoader.loadOBJ("fern");
        GameObject fern = new GameObject(
                "fern",
                new TexturedModel(
                        Window.getLoader().loadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices()),
                        new ModelTexture(Window.getLoader().loadTexture("fern"))),
                new Transform(20, 0, 20, 0, 0, 0),0.8f);
        fern.getModelTexture().setTransparency(true);
        gameObjects.add(fern);

        ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
        GameObject grass = new GameObject(
                "grass",
                new TexturedModel(
                        Window.getLoader().loadToVAO(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices()),
                        new ModelTexture(Window.getLoader().loadTexture("grass"))),
                new Transform(16, 0, 12, 0, 0, 0),1f);
        grass.getModelTexture().setTransparency(true);
        grass.getModelTexture().setUseFakeLighting(true);
        gameObjects.add(grass);

        Random random = new Random();

        for(int i =0; i<=60; i++) {
            ModelData treeData = OBJFileLoader.loadOBJ("tree");
            GameObject tree = new GameObject(
                    "tree"+i,
                    new TexturedModel(
                            Window.getLoader().loadToVAO(treeData.getVertices(), treeData.getTextureCoords(), treeData.getNormals(), treeData.getIndices()),
                            new ModelTexture(Window.getLoader().loadTexture("tree"))
                    ),
                    new Transform(random.nextFloat(300)-150,0,random.nextFloat(300)-150, 0, 0, 0), 6
            );
            gameObjects.add(tree);
        }

        ModelData stallData = OBJFileLoader.loadOBJ("stall");
        GameObject stall = new GameObject(
                "grass",
                new TexturedModel(
                        Window.getLoader().loadToVAO(stallData.getVertices(), stallData.getTextureCoords(), stallData.getNormals(), stallData.getIndices()),
                        new ModelTexture(Window.getLoader().loadTexture("stallTexture"))),
                new Transform(3, 0, 6, 0, 180, 0),1f);
        gameObjects.add(stall);

        ModelData playerData = OBJFileLoader.loadOBJ("bunny");
        player = new Player(
                "player",
                new TexturedModel(
                        Window.getLoader().loadToVAO(playerData.getVertices(), playerData.getTextureCoords(), playerData.getNormals(), playerData.getIndices()),
                        new ModelTexture(Window.getLoader().loadTexture("white"))),
                new Transform(0, 0, 0, 0, 0, 0),0.3f);

        camera = new Camera(player);

        light = new Light(new Vector3f(0, 50, 20), new Vector3f(1f, 1, 1f));

        Terrain terrain1 = new Terrain(-1, -1, Window.getLoader(), texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, 0, Window.getLoader(), texturePack, blendMap);
        Terrain terrain3 = new Terrain(0, -1, Window.getLoader(), texturePack, blendMap);
        Terrain terrain4 = new Terrain(0, 0, Window.getLoader(), texturePack, blendMap);
        terrains.add(terrain1);
        terrains.add(terrain2);
        terrains.add(terrain3);
        terrains.add(terrain4);
    }

    @Override
    public void update(float dt) {
        camera.move();
        player.move(dt);

        Window.getRenderer().processGameObject(player);

        for(Terrain terrain : terrains) {
            Window.getRenderer().processTerrain(terrain);
        }

        for(GameObject gameObject : gameObjects) {
            if(gameObject.getName().equals("dragon")) {
                gameObject.increaseRotation(0, 0.2f, 0);
            }
            Window.getRenderer().processGameObject(gameObject);
        }
        Window.getRenderer().render(light, camera);

        if(Window.debug) {
            System.out.println((1.0f / dt) + " fps");
            System.out.println(camera.getPosition().x + " " +camera.getPosition().y + " " + camera.getPosition().z);
        }
    }
}