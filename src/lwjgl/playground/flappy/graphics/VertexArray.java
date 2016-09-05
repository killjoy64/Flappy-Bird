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

    public VertexArray(int count) {
        this.count = count;
    }

    public VertexArray(int vao, int vbo, int tbo, int ibo, int cbo, int nbo, int count) {
        this.vao = vao;
        this.vbo = vbo;
        this.tbo = tbo;
        this.ibo = ibo;
        this.cbo = cbo;
        this.nbo = nbo;
        this.count = count;
    }

    public void bind() {
        glBindVertexArray(vao);
        if (ibo > 0) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        }
    }

    public void unbind() {
        if (ibo > 0) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }

        glBindVertexArray(0);
    }

    public void draw() {
        if (ibo > 0) {
            glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);
        } else {
            glDrawArrays(GL_TRIANGLES, 0, count);
        }
    }

    public void render() {
        bind();
        draw();
    }

}
