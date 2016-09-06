package lwjgl.playground.flappy.input;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kyle Flynn on 9/5/2016.
 */
public class ActionManager {

    private static ArrayList<Action> actions;

    protected static float[] axes;

    public ActionManager() {
        actions = new ArrayList<>();
        axes = new float[6];
    }

    protected static void setAxes(int id, float val) {
        axes[id] = val;
    }

    protected static float getAxes(int id) {
        return axes[id];
    }

    public static ArrayList<Action> getActions() {
        return actions;
    }

}
