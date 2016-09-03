package lwjgl.playground.flappy.input;

import org.lwjgl.glfw.GLFWJoystickCallback;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by kylef_000 on 9/2/2016.
 */
public class JoystickListener extends GLFWJoystickCallback {

    @Override
    public void invoke(int joy, int event) {
        if (event == GLFW_CONNECTED) {
            System.out.println("Joystick " + glfwJoystickPresent(joy) + " connected");
        }
    }
}
