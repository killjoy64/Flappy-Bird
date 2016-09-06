package lwjgl.playground.flappy.input;

import org.lwjgl.glfw.GLFWJoystickCallback;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by kylef_000 on 9/2/2016.
 */
public class JoystickListener extends GLFWJoystickCallback {

    private static int[] joysticks;

    public JoystickListener() {
        joysticks = new int[10];
    }

    @Override
    public void invoke(int joy, int event) {
        joysticks[joy] = (event == GLFW_CONNECTED) ? 1 : 0;
    }

    public void update() {

        if (isJoystickConnected(0) || isJoystickConnected(1)) {
            ByteBuffer buttons = glfwGetJoystickButtons( (isJoystickConnected(0) || isJoystickConnected(1) ? 0 : 1) );

            int buttonID = 1;
            while (buttons.hasRemaining()) {
                int state = buttons.get();
                if (state == GLFW_PRESS || state == GLFW_RELEASE) {
                    for (Action action : ActionManager.getActions()) {
                        if (action.getAliasCodes().contains(buttonID)) {
                            action.setPressed(state == GLFW_PRESS ? true : false); // Nice little ternary to save 6 lines of code :)
                        }
                    }
                }
                buttonID++;
            }

            FloatBuffer axes = glfwGetJoystickAxes( (isJoystickConnected(0) || isJoystickConnected(1) ? 0 : 1) );

            int axesID = 0;
            while (axes.hasRemaining()) {
                float value = axes.get();
                ActionManager.setAxes(axesID, value);
                axesID++;
            }

        }
    }

    public boolean isJoystickConnected(int joy) {
        return joysticks[joy] == 1 || glfwJoystickPresent(joy);
    }

}
