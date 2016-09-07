package lwjgl.playground.flappy.graphics;

import lwjgl.playground.flappy.math.Vector2f;
import lwjgl.playground.flappy.math.Vector3f;
import lwjgl.playground.flappy.util.BufferUtils;

import java.util.Vector;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by John Doneth on 9/3/2016.
 */





public class VertexArrayBuilder {

    private Vector<Float>   vertices;
    private Vector<Float>   texCoords;
    private Vector<Float>   normals;
    private Vector<Float>   colors;
    private Vector<Byte>    indices;

    private int vertexElementCount;
    private int texCoordElementCount;
    private int colorElementCount;
    private int normalElementCount;

    /**
     * A convenience class to ease the creation of OpenGL Vertex Array Objects.
     */
    public VertexArrayBuilder()
    {
        vertices    = new Vector<Float>();
        texCoords   = new Vector<Float>();
        normals     = new Vector<Float>();
        colors      = new Vector<Float>();
        indices     = new Vector<Byte>();

        vertexElementCount      = -1;
        texCoordElementCount    = -1;
        colorElementCount       = -1;
        normalElementCount      = -1;
    }

    public void setVertexElementCount(int count) {
        vertexElementCount = count;
    }

    public void setTexCoordElementCount(int count) {
        texCoordElementCount = count;
    }

    public void setColorElementCount(int count) {
        colorElementCount = count;
    }

    public void setNormalElementCount(int count) {
        normalElementCount = count;
    }

    public void addVertex(float x, float y) {
        vertices.add(x);
        vertices.add(y);
        if (vertexElementCount == -1) vertexElementCount = 2;
    }

    public void addVertex(float x, float y, float z) {
        vertices.add(x);
        vertices.add(y);
        vertices.add(z);
        if (vertexElementCount == -1) vertexElementCount = 3;
    }

    public void addVertex(Vector2f vertex) {
        addVertex(vertex.x, vertex.y);
    }

    public void addVertex(Vector3f vertex) {
        addVertex(vertex.x, vertex.y, vertex.z);
    }

    public void addIndex(int x, int y, int z) {
        indices.add((byte) x);
        indices.add((byte) y);
        indices.add((byte) z);
    }

    public void addIndex(int index) {
        indices.add((byte)index);
    }

    public void addIndex(Byte x, Byte y, Byte z) {
        indices.add(x);
    }

    public void addIndex(Byte index) {
        indices.add(index);
    }

    public void addTexCoord(float x, float y) {
        texCoords.add(x);
        texCoords.add(y);
        if (texCoordElementCount == -1) texCoordElementCount = 2;
    }

    public void addTexCoord(float x, float y, float z) {
        texCoords.add(x);
        texCoords.add(y);
        texCoords.add(z);
        if (texCoordElementCount == -1) texCoordElementCount = 3;
    }

    public void addTexCoord(Vector2f texCoord) {
         addTexCoord(texCoord.x, texCoord.y);
    }

    public void addTexCoord(Vector3f texCoord) {
        addTexCoord(texCoord.x, texCoord.y, texCoord.z);
    }

    public void addNormal(float x, float y) {
        normals.add(x);
        normals.add(y);
        if (normalElementCount == -1) texCoordElementCount = 2;
    }

    public void addNormal(float x, float y, float z) {
        normals.add(x);
        normals.add(y);
        normals.add(z);
        if (normalElementCount == -1) texCoordElementCount = 2;
    }

    public void addNormal(Vector2f normal) {
        addNormal(normal.x, normal.y);
    }

    public void addNormal(Vector3f normal) {
        addNormal(normal.x, normal.y, normal.z);
    }

    public void addColor(float r, float g, float b, float a) {
        colors.add(r);
        colors.add(g);
        colors.add(b);
        colors.add(a);
        if (colorElementCount == -1) colorElementCount = 4;
    }

    public void addColor(float r, float g, float b) {
        colors.add(r);
        colors.add(g);
        colors.add(b);
        if (colorElementCount == -1) colorElementCount = 3;
    }

    public void addColor(Vector3f color) {
        addColor(color.x, color.y, color.z);
    }

    public VertexArray build() {
        return build(PrimitiveType.Triangles, BufferType.Static);
    }

    public VertexArray build(PrimitiveType primitiveType) {
        return build(primitiveType, BufferType.Static);
    }

    public VertexArray build(PrimitiveType primitiveType, BufferType bufferType) {

        int glPrimitiveType = 0;

        if (primitiveType == PrimitiveType.Points){
            glPrimitiveType = GL_POINTS;
        } else if (primitiveType == PrimitiveType.Lines){
            glPrimitiveType = GL_LINES;
        } else if (primitiveType == PrimitiveType.Triangles){
            glPrimitiveType = GL_TRIANGLES;
        }

        int glBufferType = 0;

        if (bufferType == BufferType.Static){
            glBufferType = GL_STATIC_DRAW;
        } else if (bufferType == BufferType.Dynamic){
            glBufferType = GL_DYNAMIC_DRAW;
        } else if (bufferType == BufferType.Stream){
            glBufferType = GL_STREAM_DRAW;
        }


        boolean useVertexBuffer    = (vertices.size()  != 0);
        boolean useTexCoordBuffer  = (texCoords.size() != 0);
        boolean useNormalBuffer    = (normals.size()   != 0);
        boolean useIndexBuffer     = (indices.size()   != 0);
        boolean useColorBuffer     = (colors.size()    != 0);

        if (!useVertexBuffer && !useTexCoordBuffer && !useNormalBuffer && !useIndexBuffer && !useColorBuffer)
        {
            System.err.println("VertexArrayObject: Cannot create empty an Vertex Array Object!");
        }

        int vao = glGenVertexArrays(); // vertex array object
        int vbo = -1; // vertex
        int tbo = -1; // tex
        int ibo = -1; // index
        int cbo = -1; // color
        int nbo = -1; // normal

        glBindVertexArray(vao);

        if (useVertexBuffer) {
            float[] vertex_buffer_data = new float[vertices.size()];
            for (int i = 0; i < vertices.size(); i++)
            {
                vertex_buffer_data[i] = vertices.elementAt(i);
            }

            vbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertex_buffer_data), glBufferType);
            glVertexAttribPointer(Shader.VERTEX_ATTRIBUTE, vertexElementCount, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(Shader.VERTEX_ATTRIBUTE);
        }

        if (useTexCoordBuffer) {
            float[] texcoord_buffer_data = new float[texCoords.size()];
            for (int i = 0; i < texCoords.size(); i++)
            {
                texcoord_buffer_data[i] = texCoords.elementAt(i);
            }

            tbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, tbo);
            glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(texcoord_buffer_data), glBufferType);
            glVertexAttribPointer(Shader.TEX_COORD, texCoordElementCount, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(Shader.TEX_COORD);
        }

        if (useNormalBuffer) {
            float[] normal_buffer_data = new float[normals.size()];
            for (int i = 0; i < normals.size(); i++)
            {
                normal_buffer_data[i] = normals.elementAt(i);
            }

            nbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, nbo);
            glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(normal_buffer_data), glBufferType);
            glVertexAttribPointer(Shader.NORMAL, normalElementCount, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(Shader.NORMAL);
        }

        if (useColorBuffer) {
            float[] color_buffer_data = new float[colors.size()];
            for (int i = 0; i < colors.size(); i++)
            {
                color_buffer_data[i] = colors.elementAt(i);
            }

            cbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, cbo);
            glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(color_buffer_data), glBufferType);
            glVertexAttribPointer(Shader.COLOR, colorElementCount, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(Shader.COLOR);
        }

        if (useIndexBuffer) {
            byte[] index_buffer_data = new byte[indices.size()];
            for (int i = 0; i < indices.size(); i++)
            {
                index_buffer_data[i] = indices.elementAt(i);
            }

            ibo = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(index_buffer_data), glBufferType);
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        return new VertexArray(vao, vbo, tbo, ibo, cbo, nbo, vertices.size(), glPrimitiveType);

    }

}
