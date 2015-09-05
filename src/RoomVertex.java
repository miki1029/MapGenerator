import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomVertex {

    public final Point LEFT_TOP;
    public final Point RIGHT_BOTTOM;
    public final int WIDTH;
    public final int HEIGHT;
    public final Set<Point> ENTRANCE_SET;
    public final List<CorridorEdge> ADJ_LIST;

    public RoomVertex(Point leftTop, MapImageGraph mig) {
        this.LEFT_TOP = leftTop;
        this.WIDTH = findWidth(mig);
        this.HEIGHT = findHeight(mig);
        this.RIGHT_BOTTOM = Point.get(LEFT_TOP.X + WIDTH - 1, LEFT_TOP.Y + HEIGHT - 1);
        this.ENTRANCE_SET = new HashSet<>();
        findEntrance(mig);
        this.ADJ_LIST = new ArrayList<>();
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

    private void findEntrance(MapImageGraph mig) {
        Point searchLT = Point.get(LEFT_TOP.X - 1, LEFT_TOP.Y - 1);
        Point searchRT = Point.get(RIGHT_BOTTOM.X + 1, LEFT_TOP.Y - 1);
        Point searchLB = Point.get(LEFT_TOP.X - 1, RIGHT_BOTTOM.Y + 1);
        Point searchRB = Point.get(RIGHT_BOTTOM.X + 1, RIGHT_BOTTOM.Y + 1);

        findEntrance(mig, searchLT, searchRT);
        findEntrance(mig, searchLT, searchLB);
        findEntrance(mig, searchRT, searchRB);
        findEntrance(mig, searchLB, searchRB);
    }

    private void findEntrance(MapImageGraph mig, Point startPoint, Point endPoint) {
        // top -> bottom
        if ((startPoint.X == endPoint.X) && (startPoint.Y < endPoint.Y)) {
            int x = startPoint.X;
            for (int y = startPoint.Y; y < endPoint.Y; y++) {
                Point curPoint = Point.get(x, y);
                RGB curRGB = mig.getRGB(curPoint);
                if (curRGB.equals(RGB.BLACK) && !ENTRANCE_SET.contains(curPoint.up())) {
                    ENTRANCE_SET.add(curPoint);
                }
            }
        }
        // left -> right
        else if ((startPoint.X < endPoint.X) && (startPoint.Y == endPoint.Y)) {
            int y = startPoint.Y;
            for (int x = startPoint.X; x < endPoint.X; x++) {
                Point curPoint = Point.get(x, y);
                RGB curRGB = mig.getRGB(curPoint);
                if (curRGB.equals(RGB.BLACK) && !ENTRANCE_SET.contains(curPoint.left())) {
                    ENTRANCE_SET.add(curPoint);
                }
            }
        }
    }

    public boolean isInsidePoint(Point p) {
        return p.X >= LEFT_TOP.X && p.X <= RIGHT_BOTTOM.X &&
                p.Y >= LEFT_TOP.Y && p.Y <= RIGHT_BOTTOM.Y;
    }

}
