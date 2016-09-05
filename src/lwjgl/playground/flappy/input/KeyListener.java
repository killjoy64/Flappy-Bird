package lwjgl.playground.flappy.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Kyle Flynn on 8/30/2016.
 */
public class KeyListener extends GLFWKeyCallback {

    public boolean[] keys;

    private int lastKey;
    private int lastEvent;

    public KeyListener() {
        keys = new boolean[65536];
        lastKey = -99;
        lastEvent = -99;
    }

    @Override
    public void invoke(long window, int key, int scancode, int event, int mods) {
        lastKey = key;
        lastEvent = event;
    }

    public void update() {
        if (lastEvent == GLFW_PRESS || lastEvent == GLFW_RELEASE) {
            for (Action action : ActionManager.getActions()) {
                if (action.getAliasCodes().contains(lastKey)) {
                    action.setPressed(lastEvent == GLFW_PRESS ? true : false); // Nice little ternary to save 6 lines of code :)
                }
            }
            lastEvent = -99;
        }
    }

}
