package com.engine.main;

import com.engine.listeners.KeyListener;
import com.engine.listeners.MouseListener;
import com.engine.renderengine.renderer.MasterRenderer;
import com.engine.scenemanagment.SceneManager;
import com.engine.scenes.LevelEditorScene;
import com.engine.scenes.LevelScene;
import com.engine.scenemanagment.Scene;
import com.engine.util.Time;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int WIDTH, HEIGHT;
    private final String TITLE;
    public long glfwWindow;

    public float r = 1, g = 1, b = 1, a = 1;

    public Loader loader;// = new Loader();

    public MasterRenderer renderer;

    public SceneManager sceneManager;

    private static Window window = null;

    private static Scene currentScene;

    public static boolean debug = true; //TODO: DEBUG

    private Window() {
        this.WIDTH = 1920;
        this.HEIGHT = 1080;
        this.TITLE = "GameEngine";
    }

    public static void changeScene(Scene scene) {
        currentScene = scene;
        currentScene.init();
    }

    public static Window get() {
        if(Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();


        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Init GLFW
        if(!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.WIDTH, this.HEIGHT, this.TITLE, NULL, NULL);
        if(glfwWindow == NULL) {
            throw new IllegalStateException("Failed to open the window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);


        GL.createCapabilities();

        renderer = new MasterRenderer();
        loader = new Loader();
        sceneManager = new SceneManager();

        // Adding scenes
        SceneManager.addScene(new LevelEditorScene());
        SceneManager.addScene(new LevelScene());

        SceneManager.loadScene(0);
    }

    public void loop() {
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            Time.deltaTime = dt;
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if(dt >= 0) {
                currentScene.update();
            }

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }

        loader.cleanUp();
        renderer.cleanUp();
    }

    public static int getWidth() {
        return Window.get().WIDTH;
    }

    public static int getHeight() {
        return Window.get().HEIGHT;
    }

    public static MasterRenderer getRenderer() {
        return Window.get().renderer;
    }

    public static Loader getLoader() {
        return Window.get().loader;
    }
}