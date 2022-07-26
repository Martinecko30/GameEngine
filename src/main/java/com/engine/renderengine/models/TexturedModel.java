package com.engine.renderengine.models;

import com.engine.renderengine.textures.ModelTexture;

public class TexturedModel {
    private RawModel rawModel;
    private ModelTexture modelTexture;

    public TexturedModel(RawModel model, ModelTexture texture) {
        this.setRawModel(model);
        this.setModelTexture(texture);
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public void setRawModel(RawModel rawModel) {
        this.rawModel = rawModel;
    }

    public ModelTexture getModelTexture() {
        return modelTexture;
    }

    public void setModelTexture(ModelTexture modelTexture) {
        this.modelTexture = modelTexture;
    }
}