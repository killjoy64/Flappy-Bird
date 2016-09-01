package lwjgl.playground.flappy.physics;

import lwjgl.playground.flappy.graphics.Shader;
import lwjgl.playground.flappy.graphics.VertexArray;

/**
 * Created by John on 9/1/2016.
 */
public class DebugPhysicsRenderer {

    DebugPhysicsRenderer()
    {

    }

    public void draw(Rect rect)
    {
        float[] vertices = new float[] {
                -rect.getWidth() / 2.0f,   -rect.getHeight() / 2.0f, 1.0f,
                -rect.getWidth() / 2.0f,    rect.getHeight() / 2.0f, 1.0f,
                rect.getWidth() / 2.0f,    rect.getHeight() / 2.0f, 1.0f,
                rect.getWidth() / 2.0f,   -rect.getHeight() / 2.0f, 1.0f,
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                2, 3, 0
        };

        VertexArray vertexArray = new VertexArray(vertices, indices);

        Shader.PHYSICS.enable();
        vertexArray.draw();
        Shader.PHYSICS.disable();
    }

}
