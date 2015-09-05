import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RoomVertex {

    public final Point ENTRANCE;
    public final Point LEFT_TOP;
    public final Point RIGHT_BOTTOM;
    public final int WIDTH;
    public final int HEIGHT;
    public final List<CorridorEdge> ADJ_LIST;

    public RoomVertex(Point leftTop, MapImageGraph mig) {
        this.LEFT_TOP = leftTop;
        this.WIDTH = findWidth(mig);
        this.HEIGHT = findHeight(mig);
        this.RIGHT_BOTTOM = Point.get(LEFT_TOP.X + WIDTH - 1, LEFT_TOP.Y + HEIGHT - 1);
        this.ENTRANCE = findEntrance(mig);
        this.ADJ_LIST = new LinkedList<>();
    }

    private int findWidth(MapImageGraph mig) {
        RGB rgb = mig.getRGB(LEFT_TOP);
        for (int x = LEFT_TOP.X; x < mig.WIDTH; x++) {
            Point curPoint = Point.get(x, LEFT_TOP.Y);
            RGB curRGB = mig.getRGB(curPoint);
            if (!curRGB.equals(rgb)) {
                return x - LEFT_TOP.X;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    private int findHeight(MapImageGraph mig) {
        RGB rgb = mig.getRGB(LEFT_TOP);
        for (int y = LEFT_TOP.Y; y < mig.HEIGHT; y++) {
            Point curPoint = Point.get(LEFT_TOP.X, y);
            RGB curRGB = mig.getRGB(curPoint);
            if (!curRGB.equals(rgb)) {
                return y - LEFT_TOP.Y;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    // only 1 entrance
    private Point findEntrance(MapImageGraph mig) {
        Point searchLT = Point.get(LEFT_TOP.X - 1, LEFT_TOP.Y - 1);
        Point searchRT = Point.get(RIGHT_BOTTOM.X + 1, LEFT_TOP.Y - 1);
        Point searchLB = Point.get(LEFT_TOP.X - 1, RIGHT_BOTTOM.Y + 1);
        Point searchRB = Point.get(RIGHT_BOTTOM.X + 1, RIGHT_BOTTOM.Y + 1);

        List<Point> entList = new ArrayList<>(4);
        entList.add(findEntrance(mig, searchLT, searchRT));
        entList.add(findEntrance(mig, searchLT, searchLB));
        entList.add(findEntrance(mig, searchRT, searchRB));
        entList.add(findEntrance(mig, searchLB, searchRB));

        for(Point p: entList) {
            if(p != null)
                return p;
        }

        return null;
    }

    private Point findEntrance(MapImageGraph mig, Point startPoint, Point endPoint) {
        if ((startPoint.X == endPoint.X) && (startPoint.Y < endPoint.Y)) {
            int x = startPoint.X;
            for (int y = startPoint.Y; y < endPoint.Y; y++) {
                Point curPoint = Point.get(x, y);
                RGB curRGB = mig.getRGB(curPoint);
                if (curRGB.equals(RGB.BLACK)) {
                    return curPoint;
                }
            }
        } else if ((startPoint.X < endPoint.X) && (startPoint.Y == endPoint.Y)) {
            int y = startPoint.Y;
            for (int x = startPoint.X; x < endPoint.X; x++) {
                Point curPoint = Point.get(x, y);
                RGB curRGB = mig.getRGB(curPoint);
                if (curRGB.equals(RGB.BLACK)) {
                    return curPoint;
                }
            }
        }
        return null;
    }

    public boolean isInsidePoint(Point p) {
        return p.X >= LEFT_TOP.X && p.X <= RIGHT_BOTTOM.X &&
                p.Y >= LEFT_TOP.Y && p.Y <= RIGHT_BOTTOM.Y;
    }

}
