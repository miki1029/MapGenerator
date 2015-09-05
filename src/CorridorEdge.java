import java.util.HashSet;
import java.util.Set;

public class CorridorEdge {

    final Point BASE_POINT;
    final Set<Point> EDGE_SET = new HashSet<>();

    RoomVertex from;
    RoomVertex target;
    int weight;

    public CorridorEdge(Point startPoint, MapImageGraph mig) {
        this.BASE_POINT = startPoint;
        EDGE_SET.add(startPoint);
    }

//    private

}
