package com.engine.scenemanagment;

import com.engine.main.Window;

import java.util.ArrayList;

public class SceneManager {
    private static ArrayList<Scene> scenes = new ArrayList<>();

    public static void loadScene(int scene) {
        Window.changeScene(scenes.get(scene));
    }

    public static void loadScene(Scene scene) {
        Window.changeScene(scene);
    }

    public static void addScene(Scene scene) {
        scenes.add(scene);
    }

    public static void removeScene(Scene scene) {
        scenes.remove(scene);
    }

    public static void removeScene(int scene) {
        scenes.remove(scene);
    }
}
