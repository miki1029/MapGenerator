import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class CorridorEdge {

    final Point BASE_POINT;
    final Set<Point> EDGE_SET = new HashSet<>();

    final RoomVertex PREV;
    final RoomVertex NEXT;
    final int WEIGHT;

    public CorridorEdge(RoomVertex startVertex, MapImageGraph mig) {
        PREV = startVertex;
        BASE_POINT = PREV.ENTRANCE;
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
