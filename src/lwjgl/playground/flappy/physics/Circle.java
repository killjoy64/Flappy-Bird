package lwjgl.playground.flappy.physics;

/**
 * Created by John on 9/1/2016.
 */
public class Circle {

    private float x, y, r;

    public Circle() {
        this.x = 0;
        this.y = 0;
        this.r = 0;
    }

    public Circle(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public boolean contains(Point point) {
        // TODO

        return false;
    }

}
