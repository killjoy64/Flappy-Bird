package lwjgl.playground.flappy.graphics;

import lwjgl.playground.flappy.math.Vector2f;

/**
 * Created by Donet on 9/3/2016.
 */
public class ShapeRenderer {

    public ShapeRenderer() {

    }

    public void drawRect(float x, float y, float w, float h, float r, float g, float b, float a) {

        VertexArrayBuilder vertexArrayBuilder = new VertexArrayBuilder();

        vertexArrayBuilder.addVertex( 0.0f, 0.0f, 0.1f);
        vertexArrayBuilder.addVertex( 0.0f, h, 0.1f);
        vertexArrayBuilder.addVertex( w, h, 0.1f);
        vertexArrayBuilder.addVertex( w, 0.0f, 0.1f);

        vertexArrayBuilder.addIndex(0, 1, 2);
        vertexArrayBuilder.addIndex(2, 3, 0);

        for (int i = 0; i < 3; i++)
        {
            vertexArrayBuilder.addColor(r, g, b, a);
        }

        VertexArray mesh = vertexArrayBuilder.build(PrimitiveType.Triangles);
        mesh.draw();
        mesh.dispose();
    }

    public void drawLine(float x1, float y1, float x2, float y2) {

    }

    public void drawPoints(Vector2f[] points) {

    }

}
