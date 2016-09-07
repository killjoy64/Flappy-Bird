package lwjgl.playground.flappy.graphics;

/**
 * Created by Donet on 9/7/2016.
 */
public class Color {

    private float r, g, b, a;

    public static Color Blue;

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public static Color fromHSV(float hue, float saturation, float value){
        return new Color(0.0f, 0.0f, 0.0f, 0.0f);
    }


}
