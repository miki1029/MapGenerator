import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        MapImageGraph mapImageGraph = new MapImageGraph("test2.png");

        mapImageGraph.scanImage();
    }

}
