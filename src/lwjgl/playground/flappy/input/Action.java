package lwjgl.playground.flappy.input;

import java.util.ArrayList;

/**
 * Created by Kyle Flynn on 9/4/2016.
 */
public class Action {

    private ArrayList<Integer> aliasCodes;

    private ActionType type;
    private int code;
    private boolean pressed;

    public Action(ActionType type, int code) {
        this.type = type;
        this.code = code;
        this.pressed = false;

        aliasCodes = new ArrayList<>();

        aliasCodes.add(code);

        ActionManager.getActions().add(this);
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public void addAlias(int code) {
        aliasCodes.add(code);
    }

    public ArrayList<Integer> getAliasCodes() {
        return aliasCodes;
    }

    public ActionType getType() {
        return type;
    }

    public int getCode() {
        return code;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
