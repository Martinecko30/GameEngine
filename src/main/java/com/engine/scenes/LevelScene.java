package com.engine.scenes;

import com.engine.main.Window;
import com.engine.scenemanagment.Scene;

public class LevelScene extends Scene {

    public LevelScene() {
        System.out.println("Inside LevelScene");

        Window.get().r = 0;
        Window.get().g = 1;
        Window.get().b = 1;
    }

    @Override
    public void update() {

    }
}