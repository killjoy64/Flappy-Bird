package lwjgl.playground.flappy;

import lwjgl.playground.flappy.threading.ThreadedRenderer;
import lwjgl.playground.flappy.input.InputListener;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by Kyle Flynn on 8/30/2016.
 */
public class Main {

    static double NANOSECONDS = 1000000000.0 / 60.0;

    private int width = 1280;
    private int height = 720;
    private int upsCount;

    private long window;

    ThreadedRenderer threadedRenderer;

    public Main() {
        upsCount = 0;
    }

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        System.out.println("Loading LWJGL " + Version.getVersion());

        try {
            init();

            threadedRenderer = new ThreadedRenderer(window);

            threadedRenderer.init();

            long lastTime = System.nanoTime();
            long timer = System.currentTimeMillis();
            double delta = 0;

            while (!glfwWindowShouldClose(window)) {
                long now = System.nanoTime();

                delta += (now - lastTime) / NANOSECONDS;
                lastTime = now;

                while (delta >= 1) {
                    update();
                    upsCount++;
                    delta--;
                }

                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    System.out.println(upsCount + " ups | " + threadedRenderer.getFPS() + " fps");
                    upsCount = 0;
                }
            }

            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        } finally {
            close();
        }

    }

    private void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        window = glfwCreateWindow(width, height, "Flappy Bird", NULL, NULL);

        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
        glfwSwapInterval(0);
        glfwSetKeyCallback(window, new InputListener());
        glfwShowWindow(window);
    }

    private void update() {
        glfwPollEvents();

        if (threadedRenderer.isReady()) {
            threadedRenderer.getLevel().update();
        }
    }

    private void close() {
        glfwTerminate();
    }

}
