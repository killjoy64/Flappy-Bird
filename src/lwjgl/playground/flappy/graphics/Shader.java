package lwjgl.playground.flappy.graphics;

import lwjgl.playground.flappy.math.Matrix4f;
import lwjgl.playground.flappy.math.Vector3f;
import lwjgl.playground.flappy.util.ShaderUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

/**
 * Created by Kyle Flynn on 8/31/2016.
 */
public class Shader {

    public static Shader BG;

    public static final int VERTEX_ATTRIBUTE = 0;
    public static final int TEX_COORD = 1;

    private final int id;
    private Map<String, Integer> locationCache = new HashMap<String, Integer>();

    public Shader(String vertex, String fragment) {
        id = ShaderUtils.load(vertex, fragment);
    }

    public int getUniform(String name) {
        if (locationCache.containsKey(name)) {
            return locationCache.get(name);
        }

        int result = glGetUniformLocation(id, name);

        if (result == -1) {
            System.err.println("Could not find uniform variable " + name);
        } else {
            locationCache.put(name, result);
        }

        return result;
    }

    public void setUniformi(String name, int value) {
        glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value) {
        glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, float x, float y) {
        glUniform2f(getUniform(name), x, y);
    }

    public void setUniform2f(String name, Vector3f vector) {
        glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
    }

    public void setUniformMatrix4f(String name, Matrix4f matrix) {
        glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
    }

    public void enable() {
        glUseProgram(id);
    }

    public void disable() {
        glUseProgram(0);
    }

    public static void loadAll() {
        BG = new Shader("shaders/bg.vert", "shaders/bg.frag");
    }

}
