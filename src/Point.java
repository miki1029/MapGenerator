import java.util.HashMap;
import java.util.Map;

public class Point {

    // Point value
    public final int X;
    public final int Y;

    // Point Map <x, <y, Point>>
    private static Map<Integer, Map<Integer, Point>> pointMap = new HashMap<>();

    // Constructor
    private Point(int x, int y) {
        this.X = x;
        this.Y = y;

        if(!pointMap.containsKey(x)) {
            Map<Integer, Point> newMap = new HashMap<>();
            pointMap.put(x, newMap);
        }
        pointMap.get(x).put(y, this);
    }

    // Methods
    public static Point get(int x, int y) {
        // if exist in pointMap then return
        if(pointMap.containsKey(x)) {
            if(pointMap.get(x).containsKey(y))
                return pointMap.get(x).get(y);
        }

        // create instance
        return new Point(x, y);
    }

    public Point left() {
        return Point.get(X-1, Y);
    }

    public Point right() {
        return Point.get(X+1, Y);
    }

    public Point up() {
        return Point.get(X, Y-1);
    }

    public Point down() {
        return Point.get(X, Y+1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (X != point.X) return false;
        return Y == point.Y;

    }

    @Override
    public int hashCode() {
        int result = X;
        result = 31 * result + Y;
        return result;
    }

}
