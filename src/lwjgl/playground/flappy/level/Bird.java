package lwjgl.playground.flappy.level;

import jdk.internal.util.xml.impl.Input;
import lwjgl.playground.flappy.graphics.Shader;
import lwjgl.playground.flappy.graphics.Texture;
import lwjgl.playground.flappy.graphics.VertexArray;
import lwjgl.playground.flappy.input.InputListener;
import lwjgl.playground.flappy.math.Matrix4f;
import lwjgl.playground.flappy.math.Vector3f;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Kyle Flynn on 8/31/2016.
 */
public class Bird {

    private float SIZE = 1.0f;
    private VertexArray mesh;
    private Texture texture;

    private Vector3f position;
    private float rotation;
    private float delta;

    public Bird() {
        position = new Vector3f();
        delta = 0.0f;

        float[] vertices = new float[] {
                -SIZE / 2.0f,   -SIZE / 2.0f, 0.2f,
                -SIZE / 2.0f,    SIZE / 2.0f, 0.2f,
                 SIZE / 2.0f,    SIZE / 2.0f, 0.2f,
                 SIZE / 2.0f,   -SIZE / 2.0f, 0.2f,
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                2, 3, 0
        };

        float[] tcs = new float[] {
                0, 1,
                0, 0,
                1, 0,
                1, 1
        };

        mesh = new VertexArray(vertices, indices, tcs);
        texture = new Texture("res/bird.png");
    }

    private void fall() {
        delta = 0.15f;
    }

    public void update() {
        position.y -= delta;
        if (InputListener.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
            delta = -0.15f;
        } else {
            delta += 0.01f;
        }

        rotation = -delta * 90.0f;
    }

    public void render() {
        Shader.BIRD.enable();
        Shader.BIRD.setUniformMatrix4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rotation)));
        texture.bind();
        mesh.render();
        Shader.BIRD.disable();
    }

    public float getY() {
        return position.y;
    }

    public float getSize() {
        return SIZE;
    }

}
