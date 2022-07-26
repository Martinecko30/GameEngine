package com.engine.renderengine.renderer;

import com.engine.entities.GameObject;
import com.engine.game.components.Transform;
import com.engine.renderengine.models.RawModel;
import com.engine.renderengine.models.TexturedModel;
import com.engine.renderengine.shaders.StaticShader;
import com.engine.renderengine.textures.ModelTexture;
import com.engine.util.Maths;
import org.joml.Matrix4f;

import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class GORenderer {

    private StaticShader shader;

    public GORenderer(StaticShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<GameObject>> gameObjects) {
        for(TexturedModel model : gameObjects.keySet()) {
            prepareTexturedModel(model);
            List<GameObject> batch = gameObjects.get(model);
            for(GameObject gameObject : batch) {
                prepareInstance(gameObject);
                glDrawElements(GL_TRIANGLES, model.getRawModel().getVertexCount(), GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    public void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        glBindVertexArray(rawModel.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        ModelTexture texture = model.getModelTexture();
        if(texture.isTransparent()) {
            MasterRenderer.disableCulling();
        }
        shader.loadFakeLightingVariable(texture.isUseFakeLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, model.getModelTexture().getID());
    }

    public void unbindTexturedModel() {
        MasterRenderer.enableCulling();
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }

    public void prepareInstance(GameObject gameObject) {
        Matrix4f transformationMatrix = createTransformationMatrix(gameObject.transform);
        shader.loadTransformationMatrix(transformationMatrix);
    }

    private Matrix4f createTransformationMatrix(Transform transform) {
        return Maths.createTransformationMatrix(transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale().x);
    }

    @Deprecated
    public void render(GameObject gameObject, StaticShader shader) {
        TexturedModel model = gameObject.model;
        RawModel rawModel = model.getRawModel();
        glBindVertexArray(rawModel.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        Matrix4f transformationMatrix = createTransformationMatrix(gameObject.transform);

        ModelTexture texture = model.getModelTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, model.getModelTexture().getID());
        glDrawElements(GL_TRIANGLES, rawModel.getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }
}
