package com.engine.terrains;

import com.engine.main.Loader;
import com.engine.renderengine.models.RawModel;
import com.engine.renderengine.textures.ModelTexture;

public class Terrain {

    private static final float SIZE = 800;
    private static final int VERTEX_COUNT = 127;

    private float x;
    private float z;
    private RawModel model;
    private ModelTexture texture;

    public Terrain(int gridX, int gridZ, Loader loader, ModelTexture texture) {
        this.texture = texture;
        this.x = gridX * getSIZE();
        this.z = gridZ * getSIZE();
        this.model = generateTerrain(loader);
    }

    public static float getSIZE() {
        return SIZE;
    }

    public static int getVertexCount() {
        return VERTEX_COUNT;
    }

    private RawModel generateTerrain(Loader loader){
        int count = getVertexCount() * getVertexCount();
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count*2];
        int[] indices = new int[6*(getVertexCount() -1)*(getVertexCount() -1)];
        int vertexPointer = 0;
        for(int i = 0; i< getVertexCount(); i++){
            for(int j = 0; j< getVertexCount(); j++){
                vertices[vertexPointer*3] = (float)j/((float) getVertexCount() - 1) * getSIZE();
                vertices[vertexPointer*3+1] = 0;
                vertices[vertexPointer*3+2] = (float)i/((float) getVertexCount() - 1) * getSIZE();
                normals[vertexPointer*3] = 0;
                normals[vertexPointer*3+1] = 1;
                normals[vertexPointer*3+2] = 0;
                textureCoords[vertexPointer*2] = (float)j/((float) getVertexCount() - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float) getVertexCount() - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for(int gz = 0; gz< getVertexCount() -1; gz++){
            for(int gx = 0; gx< getVertexCount() -1; gx++){
                int topLeft = (gz* getVertexCount())+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)* getVertexCount())+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public RawModel getModel() {
        return model;
    }

    public ModelTexture getTexture() {
        return texture;
    }
}
