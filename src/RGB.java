import java.util.HashMap;
import java.util.Map;

public class RGB {

    // RGB value
    public final int A;
    public final int R;
    public final int G;
    public final int B;

    // RGB Map <pixel, value>
    private static Map<Integer, RGB> rgbMap = new HashMap<>();

    // RGB CONST
    public static final RGB RED = new RGB(255, 0, 0);
    public static final RGB GREEN = new RGB(0, 255, 0);
    public static final RGB BLUE = new RGB(0, 0, 255);
    public static final RGB YELLOW = new RGB(255, 255, 0);
    public static final RGB PINK = new RGB(255, 0, 255);
    public static final RGB BLACK = new RGB(0, 0, 0);
    public static final RGB WHITE = new RGB(255, 255, 255);

    // Constructor
    private RGB(int pixel) {
        this((pixel >> 24) & 0xff,
                (pixel >> 16) & 0xff,
                (pixel >> 8) & 0xff,
                (pixel) & 0xff);
    }

    private RGB(int r, int g, int b) {
        this(255, r, g, b);
    }

    private RGB(int a, int r, int g, int b) {
        this.A = a;
        this.R = r;
        this.G = g;
        this.B = b;

        int pixel = RGBToPixel(a, r, g, b);
        rgbMap.put(pixel, this);
    }

    // Methods
    public static RGB pixelToRGB(int pixel) {
        // if exist in rgbMap then return
        if (rgbMap.containsKey(pixel))
            return rgbMap.get(pixel);

        // create instance
        return new RGB(pixel);
    }

    public static int RGBToPixel(int r, int g, int b) {
        return RGBToPixel(255, r, g, b);
    }

    public static int RGBToPixel(int a, int r, int g, int b) {
        int pixel = a;
        pixel = (pixel << 8) + r;
        pixel = (pixel << 8) + g;
        pixel = (pixel << 8) + b;

        return pixel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RGB rgb = (RGB) o;

        if (R != rgb.R) return false;
        if (G != rgb.G) return false;
        return B == rgb.B;

    }

    @Override
    public int hashCode() {
        int result = R;
        result = 31 * result + G;
        result = 31 * result + B;
        return result;
    }

}
