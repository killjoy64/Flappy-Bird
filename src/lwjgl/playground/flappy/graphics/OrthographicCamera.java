package lwjgl.playground.flappy.graphics;

import lwjgl.playground.flappy.math.Matrix4f;

/**
 * Created by John Doneth on 9/2/2016.
 */
public class OrthographicCamera {

    private int width;
    private int height;
    private float x;
    private float y;

    private Matrix4f projection;
    private Matrix4f translation;

    public OrthographicCamera() {

    }

    public Matrix4f getCombinedMatrix() {
        return projection.multiply(translation);
    }

    public void update() {

    }

    public void resize(int width, int height) {

    }

    public void translate(float x, float y) {

    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
