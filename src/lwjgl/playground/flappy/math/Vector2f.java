package lwjgl.playground.flappy.math;

/**
 * Created by John Doneth on 9/2/2016.
 */
public class Vector2f {

    public float x, y;

    public Vector2f() {
        x = 0.0f;
        y = 0.0f;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f add(Vector2f right) {
        this.x += right.x;
        this.y += right.y;
        return this;
    }

    public Vector2f subtract(Vector2f right){
        this.x -= right.x;
        this.y -= right.y;
        return this;
    }

    public Vector2f multiply(Vector2f right){
        this.x *= right.x;
        this.y *= right.y;
        return this;
    }

    public Vector2f divide(Vector2f right){
        this.x /= right.x;
        this.y /= right.y;
        return this;
    }

    public Vector2f add(float scalar) {
        this.x += scalar;
        this.y += scalar;
        return this;
    }

    public Vector2f subtract(float scalar){
        this.x -= scalar;
        this.y -= scalar;
        return this;
    }

    public Vector2f multiply(float scalar)
    {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2f divide(float scalar)
    {
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }


}
