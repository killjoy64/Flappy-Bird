package lwjgl.playground.flappy.threading;

import lwjgl.playground.flappy.math.Matrix4f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

/**
 * Created by Kyle Flynn on 8/31/2016.
 */
public class ThreadedRenderer {

    private Thread thread;
    private long window;
    private int fps;

    public ThreadedRenderer(long window) {
        this.window = window;
    }

    public void init() {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                glfwMakeContextCurrent(window);
                createCapabilities();
                glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                glEnable(GL_DEPTH_TEST);

                while (!glfwWindowShouldClose(window)) {
                    render();
                }
            }

        }, "Render Thread");
        thread.start();
//        Shader.loadAll();
//
//        Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
//        Shader.BG.setUniformMatrix4f("pr_matrix", pr_matrix);
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(window);

        fps++;
    }

    synchronized public int getFPS() {
        int oldFPS = fps;
        fps = 0;
        return oldFPS;
    }
}
