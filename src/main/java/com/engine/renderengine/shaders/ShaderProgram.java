package com.engine.renderengine.shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Vector;

import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {

    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexFile, String fragmentFile) {
        this.vertexShaderId = loadShader(vertexFile, GL_VERTEX_SHADER);
        this.fragmentShaderId = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
        this.programId = glCreateProgram();
        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        bindAttributes();
        glLinkProgram(programId);
        glValidateProgram(programId);
        getAllUniformLocations();
    }

    protected abstract void getAllUniformLocations();

    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programId, uniformName);
    }

    public void start() {
        glUseProgram(programId);
    }

    public void stop() {
        glUseProgram(0);
    }

    public void cleanUp() {
        stop();
        glDetachShader(programId, vertexShaderId);
        glDetachShader(programId, fragmentShaderId);
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);
        glDeleteProgram(programId);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName) {
        glBindAttribLocation(programId, attribute, variableName);
    }

    protected void loadFloat(int location, float value) {
        glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f vector) {
        glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadBoolean(int location, boolean value) {
        float toLoad = 0;
        if(value) {
            toLoad = 1;
        }
        glUniform1f(location, toLoad);
    }

    protected void loadMatrix(int location, Matrix4f matrix) {
        matrix.get(matrixBuffer);
        //matrixBuffer.flip();
        GL20.glUniformMatrix4fv(location, false, matrixBuffer);
    }

    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Could not read shader file!");
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, shaderSource);
        glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL_COMPILE_STATUS)==GL_FALSE) {
            System.out.println(glGetShaderInfoLog(shaderID));
            System.out.println("Could not compile shader");
            System.exit(-1);
        }
        return shaderID;
    }
}