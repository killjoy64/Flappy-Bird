package lwjgl.playground.flappy.input;

import java.util.HashMap;

/**
 * Created by Kyle Flynn on 9/5/2016.
 */
public class GamePad {

    public static int A_BUTTON = 1;
    public static int B_BUTTON = 2;
    public static int X_BUTTON = 3;
    public static int Y_BUTTON = 4;
    public static int LEFT_BUTTON = 5;
    public static int RIGHT_BUTTON = 6;
    public static int BACK_BUTTON = 7;
    public static int START_BUTTON = 8;
    public static int LEFT_CONTROL_DOWN = 9;
    public static int RIGHT_CONTROL_DOWN = 10;
    public static int D_PAD_UP = 11;
    public static int D_PAD_RIGHT = 12;
    public static int D_PAD_DOWN = 13;
    public static int D_PAD_LEFT = 14;

    public static int LEFT_VERT_AXES = 1;
    public static int LEFT_HORIZ_AXES = 0;
    public static int RIGHT_VERT_AXES = 3;
    public static int RIGHT_HORIZ_AXES = 2;
    public static int LEFT_TRIGGER = 4;
    public static int RIGHT_TRIGGER = 5;

    public static float getAxes(int id) {
        return ActionManager.getAxes(id);
    }

}
