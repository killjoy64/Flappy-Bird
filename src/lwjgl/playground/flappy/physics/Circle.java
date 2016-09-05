package lwjgl.playground.flappy.physics;

/**
 * Created by John on 9/1/2016.
 */
public class Circle {

    public float x, y, radius;

    public Circle() {
        this.x = 0;
        this.y = 0;
        this.radius = 0;
    }

    public Circle(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.radius = r;
    }

    public boolean intersects(Circle other)
    {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < this.radius + other.radius) {
            return true;
        }

        return false;
    }

    public boolean contains(Point point) {
        // TODO

        return false;
    }

}
