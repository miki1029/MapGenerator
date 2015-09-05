import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class CorridorEdge {

    public final Point BASE_POINT;
    public final Set<Point> EDGE_SET = new HashSet<>();

    public final RoomVertex PREV;
    public final RoomVertex NEXT;
    public final int WEIGHT;

    public CorridorEdge(RoomVertex startVertex, Point entrancePoint, MapImageGraph mig) {
        PREV = startVertex;
        BASE_POINT = entrancePoint;
        EDGE_SET.add(BASE_POINT);
        NEXT = floodFill(BASE_POINT, mig);
        WEIGHT = EDGE_SET.size();
    }

    private RoomVertex floodFill(Point seedPoint, MapImageGraph mig) {
        Queue<Point> readyQueue = new LinkedList<>();
        readyQueue.add(seedPoint.up());
        readyQueue.add(seedPoint.down());
        readyQueue.add(seedPoint.left());
        readyQueue.add(seedPoint.right());

        for(Point point: readyQueue) {
            // background
            if(mig.getRGB(point).equals(RGB.WHITE)) continue;
            // edge
            else if(mig.getRGB(point).equals(RGB.BLACK)) {
                if(!EDGE_SET.contains(point)) {
                    EDGE_SET.add(point);
                    return floodFill(point, mig);
                }
            }
            // vertex
            else {
                // not PREV
                if(!PREV.isInsidePoint(point)) {
                    return mig.findRoomVertex(point);
                }
            }
        }
        throw new NullPointerException();
    }

}
