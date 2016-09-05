package lwjgl.playground.flappy.physics;

import org.lwjgl.system.CallbackI;

/**
 * Created by John on 9/1/2016.
 */
public class Rect {

    float x;
    float y;
    float width;
    float height;
    Point origin;

    public Rect() {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.origin = new Point();
    }

    public Rect(float x, float y, float width, float height) {
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
        this.origin = new Point();
    }

    public void setOrigin(float x, float y) {
        this.origin = new Point(x, y);
    }

    public void setOrigin(Point point) {
        this.origin = point;
    }

    public Point getOrigin() {
        return this.origin;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean contains(Point point) {
        if (point.getX() > this.x && point.getX() < this.x + this.width)
        {
            if (point.getY() > this.y && point.getY() < this.y + this.height)
            {
                return true;
            }
        }

        return false;
    }

    public boolean intersects(Rect rect) {
        //
        // Origin -> ------------------ y+  ---
        //           |                |      |
        //           |                |      |
        //           |                |      |
        //           |      rect      |    height
        //           |                |      |
        //           |                |      |
        //           |                |      |
        //           ------------------ y-  ---
        //           x-               x+
        //           |---- width -----|

        Point top_left_corner = new Point(rect.getX(), rect.getY());

        if (this.contains(top_left_corner))
        {
            return true;
        }

        Point top_right_corner = new Point(rect.getX() + rect.getWidth(), rect.getY());

        if (this.contains(top_right_corner))
        {
            return true;
        }

        Point bottom_left_corner = new Point(rect.getX(), rect.getY() - rect.getHeight());

        if (this.contains(bottom_left_corner))
        {
            return true;
        }

        Point bottom_right_corner = new Point(rect.getX() + rect.getWidth(), rect.getY() - rect.getHeight());

        if (this.contains(bottom_right_corner))
        {
            return true;
        }

        return false;
    }

}
