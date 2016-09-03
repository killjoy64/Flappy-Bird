package lwjgl.playground.flappy.math;

/**
 * Created by Kyle Flynn on 8/30/2016.
 */
public class Vector3f {

    public float x, y, z;

    public Vector3f() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector2f vec2, float z) {
        this.x = vec2.x;
        this.y = vec2.y;
        this.z = z;
    }

    public Vector3f(Vector2f vec2) {
        this.x = vec2.x;
        this.y = vec2.y;
        this.z = 0;
    }

    public Vector3f add(Vector3f right) {
        this.x += right.x;
        this.y += right.y;
        this.z += right.z;
        return this;
    }

    public Vector3f subtract(Vector3f right){
        this.x -= right.x;
        this.y -= right.y;
        this.z -= right.z;
        return this;
    }

    public Vector3f multiply(Vector3f right){
        this.x *= right.x;
        this.y *= right.y;
        this.z *= right.z;
        return this;
    }

    public Vector3f divide(Vector3f right){
        this.x /= right.x;
        this.y /= right.y;
        this.z /= right.z;
        return this;
    }

    public Vector3f add(float scalar) {
        this.x += scalar;
        this.y += scalar;
        this.z += scalar;
        return this;
    }

    public Vector3f subtract(float scalar){
        this.x -= scalar;
        this.y -= scalar;
        this.z -= scalar;
        return this;
    }

    public Vector3f multiply(float scalar)
    {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    public Vector3f divide(float scalar)
    {
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
        return this;
    }

}
