import ex1.WGraph_Algo;
import ex1.WGraph_DS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Random;

public class WGraph_AlgoTest {
    private WGraph_DS graph=new WGraph_DS();//graph DS for be init into graphA
    private WGraph_Algo graphA=new WGraph_Algo();//Graph on which the algorithms will be run
    @BeforeEach
    public void init()
    {
        for(int i=0;i<100;i++) {
            graph.addNode(i);
        }
        while(graph.edgeSize()<=1000)
        {
            Random r = new Random();
            int low = 0;
            int high = 99;
            int a = r.nextInt(high-low) + low;
            int b = r.nextInt(high-low) + low;
            graph.connect(a,b,Math.random()*10);
        }
    }
    @Test
    public void initTest()
    {
        graphA.init(graph);
        assertEquals(true,graphA.getGraph().equals(graph));//equals of java
    }
    @Test
    public void getGraphTest()
    {
        graphA.init(graph);
        WGraph_DS g1=(WGraph_DS) graphA.getGraph();
        assertEquals(true,graph.equals(g1));//equals of java
    }
    @Test
    public void CopyTest() {
        graphA.init(graph);
        WGraph_DS a;
        a=(WGraph_DS) graphA.copy();
        boolean resultCopy=graphA.getGraph().equals(a);//my equals method check if two graph is deeply equal
        assertEquals(true,resultCopy);
    }
    @Test
    public void isConnectedTest(){
        WGraph_DS g1=new WGraph_DS();
        g1.addNode(1);
        g1.addNode(2);
        g1.connect(1,2,3.33);
        graphA.init(g1);
        assertEquals(true,graphA.isConnected());//check on connected graph
        g1.removeEdge(1,2);
        graphA.isConnected();
        assertEquals(false,graphA.isConnected());//disconnected graph because we deleted an essential edge
        WGraph_DS g2=new WGraph_DS();
        graphA.init(g2);
        assertEquals(true,graphA.isConnected());//empty graph is connected
        WGraph_DS g3=new WGraph_DS();
        g3.addNode(3);
        graphA.init(g3);
        assertEquals(true,graphA.isConnected());//graph with only one node is connected
    }
    @Test
    public void DijkstraHelpTest() {
        WGraph_DS g = new WGraph_DS();
        for (int i = 0; i < 7; i++) {
            g.addNode(i);
        }
        g.connect(0, 1, 4);
        g.connect(0, 4, 2);
        g.connect(0, 5, 8);
        g.connect(1, 2, 7);
        g.connect(1, 4, 1);
        g.connect(3, 4, 4);
        g.connect(6, 3, 9);
        g.connect(5, 3, 3);
        graphA.init(g);
        assertEquals(true, graphA.DijkstraHelp(0, 6));
        assertEquals(true, graphA.getGraph().getNode(6).getTag() == 15);
        assertEquals(true, graphA.getGraph().getNode(5).getTag() == 8);
    }
    @Test
    public void shortestPathTest() {
        WGraph_DS g=new WGraph_DS();
        for(int i=0;i<7;i++){
            g.addNode(i);
        }
        g.connect(0,1,4);
        g.connect(0,4,2);
        g.connect(0,5,8);
        g.connect(1,2,7);
        g.connect(1,4,1);
        g.connect(3,4,4);
        g.connect(6,3,9);
        g.connect(5,3,3);
        graphA.init(g);//0-6->0-4-3-6
        assertEquals(graphA.shortestPath(0,6).get(0).getKey(),0);
        assertEquals(graphA.shortestPath(0,6).get(1).getKey(),4);
        assertEquals(graphA.shortestPath(0,6).get(2).getKey(),3);
        assertEquals(graphA.shortestPath(0,6).get(3).getKey(),6);
    }
    @Test
    public void shortestPathDistTest(){
        WGraph_DS g=new WGraph_DS();
        for(int i=0;i<7;i++){
            g.addNode(i);
        }
        g.connect(0,1,4);
        g.connect(0,4,2);
        g.connect(0,5,8);
        g.connect(1,2,7);
        g.connect(1,4,1);
        g.connect(3,4,4);
        g.connect(6,3,9);
        g.connect(5,3,3);
        graphA.init(g);
        assertEquals(15,graphA.shortestPathDist(0,6));
    }
    @Test
    public void saveTest(){
        String file_name="C:\\Users\\omer rabin\\IdeaProjects\\Ex1\\out\\test_for_WGraph_Algo.mytest";
        graphA.init(graph);
        graphA.save(file_name);
        WGraph_Algo wg=new WGraph_Algo();
        wg.load(file_name);
        boolean result=graphA.getGraph().equals(wg.getGraph());//check if equal with deep equal checking
        Assertions.assertEquals(true,result);
    }
    @Test
    public void loadTest(){
        String file_name1="C:\\Users\\omer rabin\\IdeaProjects\\Ex1\\out\\test_for_WGraph_Algo.mytest";
        String file_name2="C:\\Users\\omer rabin\\IdeaProjects\\Ex1\\src\\test_for_WGraph_Algo1.mytest";
        graphA.init(graph);
        graphA.save(file_name1);
        WGraph_DS g1=new WGraph_DS();
        WGraph_Algo wg1=new WGraph_Algo();
        wg1.init(g1);
        wg1.save(file_name2);//save the null graph
        WGraph_Algo wg=new WGraph_Algo();
        wg.load(file_name2);//load the null graph
        boolean result=graphA.equals(wg.getGraph());//check if equal with deep equal checking
        Assertions.assertEquals(false,result);

    }
}
