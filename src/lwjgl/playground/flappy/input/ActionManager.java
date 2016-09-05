package lwjgl.playground.flappy.input;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Kyle Flynn on 9/5/2016.
 */
public class ActionManager {

    private static ArrayList<Action> actions;

    public ActionManager() {
        actions = new ArrayList<>();
    }

    public static ArrayList<Action> getActions() {
        return actions;
    }

}
