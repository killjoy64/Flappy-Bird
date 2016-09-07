package lwjgl.playground.flappy.graphics;

import lwjgl.playground.flappy.math.Vector3f;
import lwjgl.playground.flappy.util.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by Kyle Flynn on 8/31/2016.
 */
public class VertexArray {

    private int vao; // vertex array object
    private int vbo; // vertex
    private int tbo; // tex
    private int ibo; // index
    private int cbo; // color
    private int nbo; // normal
    private int count; // vertex count
    private int primType;
    private boolean wireframe;

    public VertexArray(int count) {
        this.count = count;
    }

    public VertexArray(int vao, int vbo, int tbo, int ibo, int cbo, int nbo, int count, int primType) {
        this.vao = vao;
        this.vbo = vbo;
        this.tbo = tbo;
        this.ibo = ibo;
        this.cbo = cbo;
        this.nbo = nbo;
        this.count = count;
        this.primType = primType;
        this.wireframe = false;
    }

    public void bind() {
        if (wireframe){
            glPolygonMode(GL_FRONT_FACE, GL_LINE);
        }

        glBindVertexArray(vao);

        if (ibo > 0) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        }
    }

    public void unbind() {
        if (wireframe){
            glPolygonMode(GL_FRONT_FACE, GL_FILL);
        }

        if (ibo > 0) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }

        glBindVertexArray(0);
    }

    public void setWireframe(boolean useWireframe) {
        if (useWireframe){
            wireframe = true;
        } else {
            wireframe = false;
        }
    }

    public void draw() {
        bind();
        if (ibo > 0) {
            glDrawElements(primType, count, GL_UNSIGNED_BYTE, 0);
        } else {
            glDrawArrays(primType, 0, count);
        }
        unbind();
    }

    // Call this to release the native GPU resources
    public void dispose() {
        count = 0;

        if (glIsBuffer(vbo)){
            glDeleteBuffers(vbo);
        }
        if (glIsBuffer(tbo)){
            glDeleteBuffers(tbo);
        }
        if (glIsBuffer(ibo)){
            glDeleteBuffers(ibo);
        }
        if (glIsBuffer(cbo)){
            glDeleteBuffers(cbo);
        }
        if (glIsBuffer(nbo)){
            glDeleteBuffers(nbo);
        }
        if (glIsVertexArray(vao)){
            glDeleteBuffers(vao);
        }
    }

}
