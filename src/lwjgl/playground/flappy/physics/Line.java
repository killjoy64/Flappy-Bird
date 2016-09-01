package lwjgl.playground.flappy.physics;

/**
 * Created by John on 9/1/2016.
 */
public class Line {

    private float x1;
    private float y1;

    private float x2;
    private float y2;

    public Line()
    {
        this.x1 = 0;
        this.x2 = 0;

        this.y1 = 0;
        this.y2 = 0;
    }

    public Line(float x1, float y1, float x2, float y2)
    {
        this.x1 = x1;
        this.x2 = x2;

        this.y1 = y1;
        this.y2 = y2;
    }

    public Line(Point p1, Point p2)
    {
        this.x1 = p1.getX();
        this.x2 = p2.getX();

        this.y1 = p1.getY();
        this.y2 = p2.getY();
    }



}
