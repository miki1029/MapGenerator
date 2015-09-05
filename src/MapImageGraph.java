import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapImageGraph {

    // IMAGE
    private final BufferedImage IMAGE;
    public final int WIDTH;
    public final int HEIGHT;
    private final Map<Point, RGB> RGB_MAP = new HashMap<>();

    // graph
    private final List<RoomVertex> ROOM_VERTEX_LIST = new ArrayList<>();

    // Constructor
    public MapImageGraph(String fileName) throws IOException {
        URL fileUrl = ClassLoader.getSystemResource(fileName);
        IMAGE = ImageIO.read(new File(fileUrl.getFile()));
        WIDTH = IMAGE.getWidth();
        HEIGHT = IMAGE.getHeight();
        scanImage();
        scanGraph();
    }

    public MapImageGraph(BufferedImage bi) {
        if (bi == null)
            throw new NullPointerException();
        IMAGE = bi;
        WIDTH = IMAGE.getWidth();
        HEIGHT = IMAGE.getHeight();
        scanImage();
        scanGraph();
    }

    // scan image to RGB_MAP
    private void scanImage() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int pixel = IMAGE.getRGB(x, y);
                Point curPoint = Point.get(x, y);
                RGB curRGB = RGB.pixelToRGB(pixel);
                if (!curRGB.equals(RGB.WHITE))
                    RGB_MAP.put(curPoint, curRGB);
            }
        }
    }

    // scan graph to ROOM_VERTEX_LIST
    private void scanGraph() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Point curPoint = Point.get(x, y);
                RGB curRGB = getRGB(curPoint);

                // background
                if (curRGB.equals(RGB.WHITE)) {
                    continue;
                }
                // edge
                else if (curRGB.equals(RGB.BLACK)) {
                    continue;
                }
                // vertex
                else {
                    int jump = jumpSizeExistVertex(curPoint, curRGB);
                    // new vertex
                    if (jump == 0) {
                        RoomVertex newVertex = new RoomVertex(curPoint, this);
                        ROOM_VERTEX_LIST.add(newVertex);
                        x += newVertex.WIDTH - 1;
                    }
                    // exist vertex and jump
                    else {
                        x += jump;
                    }
                }
            }
        }

        for (RoomVertex vertex : ROOM_VERTEX_LIST) {
            CorridorEdge newEdge = new CorridorEdge(vertex, this);
            vertex.ADJ_LIST.add(newEdge);
        }
    }

    // calculate jump size to vertex
    private int jumpSizeExistVertex(Point searchPoint, RGB searchRGB) {
        int findY = searchPoint.Y - 1;

        // find vertex top
        for (; findY >= 0; findY--) {
            RGB findRGB = getRGB(Point.get(searchPoint.X, findY));

            if (!findRGB.equals(searchRGB)) break;
        }
        int goToTop = searchPoint.Y - findY - 1;

        // new vertex
        if (goToTop == 0) {
            return 0;
        }
        // exist vertex
        else {
            Point leftTop = Point.get(searchPoint.X, searchPoint.Y - goToTop);
            // find vertex and jump WIDTH - 1
            for (RoomVertex vertex : ROOM_VERTEX_LIST) {
                if (vertex.LEFT_TOP.equals(leftTop)) {
                    return vertex.WIDTH - 1;
                }
            }
        }
        throw new NullPointerException();
    }

    // public methods
    public RGB getRGB(Point p) {
        if (!RGB_MAP.containsKey(p)) {
            return RGB.WHITE;
        }
        return RGB_MAP.get(p);
    }

    public RoomVertex findRoomVertex(Point p) {
        for (RoomVertex vertex : ROOM_VERTEX_LIST) {
            if (vertex.isInsidePoint(p))
                return vertex;
        }
        throw new NullPointerException();
    }

}
