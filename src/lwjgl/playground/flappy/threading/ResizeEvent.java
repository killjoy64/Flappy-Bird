package lwjgl.playground.flappy.threading;

/**
 * Created by John on 9/1/2016.
 */
public class ResizeEvent {

    private int width;
    private int height;

    public ResizeEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
