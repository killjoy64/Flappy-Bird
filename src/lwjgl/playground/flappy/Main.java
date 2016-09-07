package lwjgl.playground.flappy;

import lwjgl.playground.flappy.audio.Music;
import lwjgl.playground.flappy.input.ActionManager;
import lwjgl.playground.flappy.input.JoystickListener;
import lwjgl.playground.flappy.threading.ThreadedRenderer;
import lwjgl.playground.flappy.input.KeyListener;
import lwjgl.playground.flappy.util.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static lwjgl.playground.flappy.audio.Music.ioResourceToByteBuffer;

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

    private int width = 720;
    private int height = 480;
    private int upsCount;
    private boolean resized = false;

    private long window;

    ThreadedRenderer threadedRenderer;
    Thread musicThread;

    private KeyListener keyListener;
    private JoystickListener joystickListener;
    private ActionManager actionManager;

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
//            musicThread = new Thread(new Music(), "Music Thread");
//            musicThread.start();

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
                    System.out.println(upsCount + " UPS | " + threadedRenderer.getFPS() + " FPS");
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

//        Thread soundThread = new Thread(new Music(), "Music Thread");
//        soundThread.start();
        // END AUDIO CODE

        keyListener = new KeyListener();
        joystickListener = new JoystickListener();
        actionManager = new ActionManager();

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);

        try {
            setIcon("res/bird.png");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        glfwSetKeyCallback(window, keyListener);
        glfwSetJoystickCallback(joystickListener);
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

        joystickListener.update();
        keyListener.update();

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
//            musicThread.join();
            glfwTerminate();
        } catch (Exception e) {
            System.exit(0);
        }
    }

    public void setIcon(String path) throws Exception {
        IntBuffer w = memAllocInt(1);
        IntBuffer h = memAllocInt(1);
        IntBuffer comp = memAllocInt(1);

        // Icons
        {
            ByteBuffer icon16;
            ByteBuffer icon32;
            try {
                icon16 = ioResourceToByteBuffer(path, 2048);
                icon32 = ioResourceToByteBuffer(path, 4096);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try (GLFWImage.Buffer icons = GLFWImage.malloc(2)) {
                ByteBuffer pixels16 = STBImage.stbi_load_from_memory(icon16, w, h, comp, 4);
                icons
                        .position(0)
                        .width(w.get(0))
                        .height(h.get(0))
                        .pixels(pixels16);

                ByteBuffer pixels32 = STBImage.stbi_load_from_memory(icon32, w, h, comp, 4);
                icons
                        .position(1)
                        .width(w.get(0))
                        .height(h.get(0))
                        .pixels(pixels32);

                icons.position(0);
                glfwSetWindowIcon(window, icons);

                STBImage.stbi_image_free(pixels32);
                STBImage.stbi_image_free(pixels16);
            }
        }

        memFree(comp);
        memFree(h);
        memFree(w);
    }
}
