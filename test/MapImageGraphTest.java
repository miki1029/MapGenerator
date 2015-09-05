import org.junit.Before;
import org.junit.Test;

public class MapImageGraphTest {

    private MapImageGraph mapImageGraph;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testConstruct() throws Exception {
        mapImageGraph = new MapImageGraph("test.png");
        System.out.println();
    }
}