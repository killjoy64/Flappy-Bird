package lwjgl.playground.flappy.physics;

/**
 * Created by John on 9/1/2016.
 */
public class DynamicRect {

    float x;
    float y;
    float width;
    float height;
    float rotation;
    Point origin;

    public DynamicRect()
    {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.rotation = 0;
        this.origin = new Point();
    }

    public DynamicRect(float x, float y, float width, float height, float degrees)
    {
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
        this.rotation = degrees;
        this.origin = new Point();
    }

}
