package lwjgl.playground.flappy.level;

import lwjgl.playground.flappy.graphics.Shader;
import lwjgl.playground.flappy.graphics.Texture;
import lwjgl.playground.flappy.graphics.VertexArray;
import lwjgl.playground.flappy.input.Action;
import lwjgl.playground.flappy.input.ActionType;
import lwjgl.playground.flappy.input.GamePad;
import lwjgl.playground.flappy.graphics.VertexArrayBuilder;
import lwjgl.playground.flappy.input.KeyListener;
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

    private boolean isDead;

    private Action jump;

    public Bird() {
        position = new Vector3f();
        delta = 0.0f;
        isDead = false;

        VertexArrayBuilder vertexArrayBuilder = new VertexArrayBuilder();

        vertexArrayBuilder.addVertex( -SIZE / 2.0f, -SIZE / 2.0f, 0.2f);
        vertexArrayBuilder.addVertex( -SIZE / 2.0f,  SIZE / 2.0f, 0.2f);
        vertexArrayBuilder.addVertex(  SIZE / 2.0f,  SIZE / 2.0f, 0.2f);
        vertexArrayBuilder.addVertex(  SIZE / 2.0f, -SIZE / 2.0f, 0.2f);

        vertexArrayBuilder.addIndex(0, 1, 2);
        vertexArrayBuilder.addIndex(2, 3, 0);

        vertexArrayBuilder.addTexCoord(0, 1);
        vertexArrayBuilder.addTexCoord(0, 0);
        vertexArrayBuilder.addTexCoord(1, 0);
        vertexArrayBuilder.addTexCoord(1, 1);

        mesh = vertexArrayBuilder.build();
        texture = new Texture("res/bird.png");

        jump = new Action(ActionType.KEY, GLFW.GLFW_KEY_SPACE);
        jump.addAlias(GamePad.A_BUTTON);

    }

    public void dispose(){
        texture.dispose();
        mesh.dispose();
    }

    private void fall() {
        delta = 0.15f;
    }

    public void update() {
        position.y -= delta;

        if (jump.isPressed()) {
            delta = -0.15f;
        } else {
            delta += 0.01f;
        }

        if (position.y < -20.0f) {
            delta = 0.0f;
            position.y = -20.0f;
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

    public void die() {
        isDead = true;
    }

    public float getY() {
        return position.y;
    }

    public float getSize() {
        return SIZE;
    }

    public Action getJump() {
        return jump;
    }

}
