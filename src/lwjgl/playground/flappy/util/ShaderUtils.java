package lwjgl.playground.flappy.util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by Kyle Flynn on 8/30/2016.
 */
public class ShaderUtils {

    private ShaderUtils() {

    }

    public static int load(String verPath, String fragPath) {
        String vert = FileUtils.loadAsString(verPath);
        String frag = FileUtils.loadAsString(fragPath);

        return create(vert, frag);
    }

    public static int create(String vert, String frag) {
        int program = glCreateProgram();
        int vertID = glCreateShader(GL_VERTEX_SHADER);
        int fragID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertID, vert);
        glShaderSource(fragID, frag);

        glCompileShader(vertID);
        if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("FAILED TO COMPILE VERTEX SHADER");
            System.err.println(glGetShaderInfoLog(vertID));
            return -1;
        }

        glCompileShader(fragID);
        if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("FAILED TO COMPILE FRAGMENT SHADER");
            System.err.println(glGetShaderInfoLog(fragID));
        }

        glAttachShader(program, vertID);
        glAttachShader(program, fragID);
        glLinkProgram(program);
        glValidateProgram(program);

        glDeleteShader(vertID);
        glDeleteShader(fragID);

        return program;
    }

}

