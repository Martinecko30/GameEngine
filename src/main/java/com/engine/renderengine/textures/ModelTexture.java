package com.engine.renderengine.textures;

public class ModelTexture {

    private int textureID;

    private float shineDamper = 1;
    private float reflectivity = 0;

    private boolean transparent = false;
    private boolean useFakeLighting = false;

    public ModelTexture(int id) {
        this.textureID = id;
    }

    public int getID() {
        return this.textureID;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparency(boolean transparency) {
        transparent = transparency;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public boolean isUseFakeLighting() {
        return useFakeLighting;
    }

    public void setUseFakeLighting(boolean usFakeLighting) {
        this.useFakeLighting = usFakeLighting;
    }
}