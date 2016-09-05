package lwjgl.playground.flappy.physics;

import lwjgl.playground.flappy.graphics.Shader;
import lwjgl.playground.flappy.graphics.VertexArray;
import lwjgl.playground.flappy.graphics.VertexArrayBuilder;

/**
 * Created by John on 9/1/2016.
 */
public class DebugPhysicsRenderer {

    DebugPhysicsRenderer()
    {

    }

    public void draw(Rect rect)
    {

        VertexArrayBuilder vertexArrayBuilder = new VertexArrayBuilder();

        vertexArrayBuilder.addVertex( -rect.getWidth() / 2.0f,  -rect.getHeight() / 2.0f, 1.0f );
        vertexArrayBuilder.addVertex( -rect.getWidth() / 2.0f,   rect.getHeight() / 2.0f, 1.0f );
        vertexArrayBuilder.addVertex(  rect.getWidth() / 2.0f,   rect.getHeight() / 2.0f, 1.0f );
        vertexArrayBuilder.addVertex(  rect.getWidth() / 2.0f,  -rect.getHeight() / 2.0f, 1.0f );

        vertexArrayBuilder.addIndex(0, 1, 2);
        vertexArrayBuilder.addIndex(2, 3, 0);

        vertexArrayBuilder.addColor(0.0f, 1.0f, 0.0f);
        vertexArrayBuilder.addColor(0.0f, 1.0f, 0.0f);
        vertexArrayBuilder.addColor(0.0f, 1.0f, 0.0f);
        vertexArrayBuilder.addColor(0.0f, 1.0f, 0.0f);

        VertexArray vertexArray = vertexArrayBuilder.build();

        Shader.PHYSICS.enable();
        vertexArray.draw();
        Shader.PHYSICS.disable();
    }

}
