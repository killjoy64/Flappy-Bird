package lwjgl.playground.flappy.threading;

import lwjgl.playground.flappy.graphics.Shader;
import lwjgl.playground.flappy.level.Level;
import lwjgl.playground.flappy.math.Matrix4f;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengles.GLES;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Kyle Flynn on 8/31/2016.
 */
public class ThreadedRenderer {

    private Thread thread;
    private long window;
    private int fps;

    private Level level;

    private boolean ready;

    final Queue<ResizeEvent> rendererEventQueue = new ConcurrentLinkedQueue<>();

    public ThreadedRenderer(long window) {
        this.window = window;
        this.ready = false;
    }

    public void init() {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                glfwMakeContextCurrent(window);
                createCapabilities();
                glEnable(GL_DEPTH_TEST);
                glActiveTexture(GL_TEXTURE1);
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

                System.out.println("Render thread initialized - OpenGL " + glGetString(GL_VERSION));
                Shader.loadAll(); // Must be called before enabling any shaders

                Shader.BG.enable();
                Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
                Shader.BG.setUniformMatrix4f("pr_matrix", pr_matrix);
                Shader.BG.setUniformi("tex", 1);
                Shader.BG.disable();

                Shader.BIRD.enable();
                Shader.BIRD.setUniformMatrix4f("pr_matrix", pr_matrix);
                Shader.BIRD.setUniformi("tex", 1);
                Shader.BIRD.disable();

                Shader.PIPE.enable();
                Shader.PIPE.setUniformMatrix4f("pr_matrix", pr_matrix);
                Shader.PIPE.setUniformi("tex", 1);
                Shader.PIPE.disable();

                level = new Level();

                ready = true;

                while (!glfwWindowShouldClose(window)) {
                    render();

                    ResizeEvent resizeEvent = rendererEventQueue.poll();

                    if (resizeEvent != null)
                    {
                        glViewport(0, 0, resizeEvent.getWidth(), resizeEvent.getHeight());
                    }
                }
                System.exit(0);

                level.dispose();
            }

        }, "Render Thread");
        thread.start();
    }

    public void resize(int width, int height)
    {
        if (rendererEventQueue.size() < 8)
        {
            rendererEventQueue.offer(new ResizeEvent(width, height));
        }
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (level.gameover()) {
            level.dispose();
            level = new Level();
        } else {
            level.render();
        }

        glfwSwapBuffers(window);

        fps++;
    }

    synchronized public int getFPS() {
        int oldFPS = fps;
        fps = 0;
        return oldFPS;
    }

    public Level getLevel() {
        return level;
    }

    public boolean isReady() {
        return ready;
    }

}
