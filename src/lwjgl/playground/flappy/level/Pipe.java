package lwjgl.playground.flappy.level;

import lwjgl.playground.flappy.graphics.Texture;
import lwjgl.playground.flappy.graphics.VertexArray;
import lwjgl.playground.flappy.graphics.VertexArrayBuilder;
import lwjgl.playground.flappy.math.Matrix4f;
import lwjgl.playground.flappy.math.Vector3f;

/**
 * Created by Kyle Flynn on 8/31/2016.
 */
public class Pipe {

    private static float width = 1.5f;
    private static float height = 8.0f;
    private static Texture texture;
    private static VertexArray mesh;

    private Vector3f position = new Vector3f();
    private Matrix4f ml_matrix;

    public static void create() {

        VertexArrayBuilder vertexArrayBuilder = new VertexArrayBuilder();

        vertexArrayBuilder.addVertex( 0.0f, 0.0f, 0.1f);
        vertexArrayBuilder.addVertex( 0.0f, height, 0.1f);
        vertexArrayBuilder.addVertex( width, height, 0.1f);
        vertexArrayBuilder.addVertex( width, 0.0f, 0.1f);

        vertexArrayBuilder.addIndex(0, 1, 2);
        vertexArrayBuilder.addIndex(2, 3, 0);

        vertexArrayBuilder.addTexCoord(0, 1);
        vertexArrayBuilder.addTexCoord(0, 0);
        vertexArrayBuilder.addTexCoord(1, 0);
        vertexArrayBuilder.addTexCoord(1, 1);

        mesh = vertexArrayBuilder.build();
        texture = new Texture("res/pipe.png");
    }

    public Pipe(float x, float y) {
        position.x = x;
        position.y = y;

        ml_matrix = Matrix4f.translate(position);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public static VertexArray getMesh() {
        return mesh;
    }

    public static Texture getTexture() {
        return texture;
    }

    public static float getWidth() {
        return width;
    }

    public static float getHeight() {
        return height;
    }

    public Matrix4f getModelMatrix() {
        return ml_matrix;
    }

}
