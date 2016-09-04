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
    public static Shader BIRD;
    public static Shader PIPE;
    public static Shader FADE;
    public static Shader PHYSICS;

    public static final int VERTEX_ATTRIBUTE = 0;
    public static final int TEX_COORD = 1;
    public static final int NORMAL = 2;
    public static final int COLOR = 3;

    private final int id;
    private Map<String, Integer> locationCache = new HashMap<String, Integer>();

    private boolean enabled = false;

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
        if (!enabled) enable();
        glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value) {
        if (!enabled) enable();
        glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, float x, float y) {
        if (!enabled) enable();
        glUniform2f(getUniform(name), x, y);
    }

    public void setUniform2f(String name, Vector3f vector) {
        if (!enabled) enable();
        glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
    }

    public void setUniformMatrix4f(String name, Matrix4f matrix) {
        if (!enabled) enable();
        glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
    }

    public void enable() {
        glUseProgram(id);
        enabled = true;
    }

    public void disable() {
        glUseProgram(0);
        enabled = false;
    }

    public static void loadAll() {
        BG = new Shader("shaders/bg.vert", "shaders/bg.frag");
        BIRD = new Shader("shaders/bird.vert", "shaders/bird.frag");
        PIPE = new Shader("shaders/pipe.vert", "shaders/pipe.frag");
        FADE = new Shader("shaders/fade.vert", "shaders/fade.frag");
    }

}
