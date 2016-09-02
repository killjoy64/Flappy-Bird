package lwjgl.playground.flappy;

import lwjgl.playground.flappy.audio.Music;
import lwjgl.playground.flappy.threading.ThreadedRenderer;
import lwjgl.playground.flappy.input.InputListener;
import lwjgl.playground.flappy.util.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.openal.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by Kyle Flynn on 8/30/2016.
 */
public class Main {

    static double NANOSECONDS = 1000000000.0 / 60.0;

    private int width = 720;
    private int height = 480;
    private int upsCount;
    private boolean resized = false;

    private long window;

    ThreadedRenderer threadedRenderer;
    Thread musicThread;

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
            musicThread = new Thread(new Music(), "Music Thread");
            musicThread.start();

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

        // BEGIN AUDIO CODE

        Thread soundThread = new Thread(new Music(), "Music Thread");
        soundThread.start();
        // END AUDIO CODE

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
        glfwSwapInterval(1);
        glfwSetKeyCallback(window, new InputListener());
        glfwShowWindow(window);

        glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                resized = true;
                width = w;
                height = h;
            }
        });
    }

    private void update() {
        glfwPollEvents();

        if (threadedRenderer.isReady()) {
            threadedRenderer.getLevel().update();

            if (resized)
            {
                threadedRenderer.resize(width, height);
            }
        }
    }

    private void close() {
        try {
            musicThread.join();
            glfwTerminate();
        } catch (InterruptedException e) {
            System.exit(0);
        }
    }

}
