
import ex1.WGraph_Algo;
import ex1.WGraph_DS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Random;

public class WGraph_DS_Test {

    private WGraph_DS graph=new WGraph_DS();
    @BeforeEach
    public void init() {
        for(int i=0;i<100;i++) {//this for creates 100 nodes
            graph.addNode(i);
        }
        while(graph.edgeSize()<=1000)//creates 1000 edges
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
    public void getNodeTest()
    {
        int key=graph.getNode(0).getKey();
        assertEquals(0,key);
        assertEquals(graph.getNode(-2),null);//there is no key -2 in the graph right to now
    }
    @Test
    public void hasEdgeTest()
    {
        graph.connect(1,2,1.0);
        assertEquals(true,graph.hasEdge(1,2));
        graph.removeEdge(1,2);
        assertEquals(false,graph.hasEdge(1,2));
    }
    @Test
    public void getEdgeTest()
    {
        graph.connect(0,1,(double) 5);
        assertEquals((double) 5,graph.getEdge(0,1));
        graph.removeEdge(0,1);
        assertEquals(-1.0,graph.getEdge(0,1));
    }
    @Test
    public void addNodeTest()
    {
        boolean flag=false;
        int key=0;
        while(!flag){
            if(graph.getNode(key)==null) {
                graph.addNode(key);
                flag=true;
            }
            key++;
        }
        assertEquals(true,graph.getV().contains(graph.getNode(key-1)));//check if the key is exist in the graph
    }
    @Test
    public void connectTest()
    {
        graph.connect(2,98,3.14);
        assertEquals(3.14,graph.getEdge(2,98));
        graph.connect(2,98,7);
        assertEquals(7,graph.getEdge(2,98));
    }
    @Test
    public void getV1Test() {
        Collection copy=graph.getV();
        assertEquals(graph.nodeSize(),copy.size());
    }
    @Test
    public void getV2Test(){
        graph.connect(2,5,1);
        Collection ni1=graph.getV(2);
        Collection ni2=graph.getV(5);
        assertEquals(true,ni1.contains(graph.getNode(5)));
        assertEquals(true,ni2.contains(graph.getNode(2)));
        graph.removeNode(2);
        graph.removeNode(5);
        assertEquals(false,ni1.contains(graph.getNode(5)));
        assertEquals(false,ni2.contains(graph.getNode(2)));
    }
    @Test
    public void removeNodeTest()
    {
        graph.addNode(1);
        graph.connect(1,55,4.55);
        graph.removeNode(1);
        assertEquals(false,graph.getV().contains(graph.getNode(1)));
        assertEquals(false,graph.hasEdge(1,55));
        assertEquals(-1.0,graph.getEdge(1,55));
    }
    @Test
    public void removeEdge()
    {
        graph.connect(1,2,9.2);
        graph.removeEdge(1,2);
        assertEquals(false,graph.hasEdge(1,2));
        assertEquals(-1.0,graph.getEdge(1,2));
    }
    @Test
    public void toStringTest(){
        WGraph_DS g1=new WGraph_DS(graph);
        assertEquals(graph.toString(),g1.toString());
        for(int i=0;i<g1.nodeSize();i++){
            g1.removeNode(i);
        }
        assertEquals(false,graph.toString().equals(g1.toString()));
    }
    @Test
    public void equalsTest(){
        WGraph_DS g1=new WGraph_DS(graph);
        boolean eq=graph.equals(g1);
        assertEquals(true,eq);
    }
}