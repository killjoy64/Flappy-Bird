package lwjgl.playground.flappy.input;

import java.util.HashMap;

/**
 * Created by kylef_000 on 9/2/2016.
 */
public class Actions {

    private static HashMap<Integer, Integer> actions = new HashMap<Integer, Integer>();

    public static HashMap<Integer, Integer> getActions() {
        return actions;
    }

    public static void addAction(int action, int status) {
        actions.put(action, status);
    }

}
