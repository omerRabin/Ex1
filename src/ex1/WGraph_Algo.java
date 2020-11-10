package ex1;

import java.io.*;
import java.util.*;

//WGraph_Algo represent algorithms on graph
public class WGraph_Algo implements weighted_graph_algorithms,Serializable  {
    private weighted_graph ga;//represent graph

    public WGraph_Algo() {//empty constructor
    }

    /**
     *this method get a graph g and init ga to be equal to g
     * @param g
     */
    public void init(weighted_graph g) {
        this.ga = g;
    }
    /**
     * get Graph
     * @return ga
     */
    @Override
    public weighted_graph getGraph() {
        return this.ga;
    }
    /**
     *this method create in deep copy a copy of this.ga and return it's
     * @return copy
     */
    @Override
    public weighted_graph copy() {
        weighted_graph copy = new WGraph_DS(this.ga);//create the copy graph via copy constructor
        return copy;
    }
    /**
     * the method initialize all the info fields to be null
     */
    private void initializeInfo()
    {
        Iterator<node_info> it=this.ga.getV().iterator();
        while (it.hasNext())
        {
            it.next().setInfo(null);
        }
    }
    /**
     *the method initialize all the node_tag to infinity
     */
    private void initializeTag()
    {
        Iterator<node_info> it=this.ga.getV().iterator();
        while(it.hasNext())
        {
            it.next().setTag(Double.POSITIVE_INFINITY);
        }
    }

    /**
     * the method returns true iff there is a valid path from EVREY node to each other node.
     * @return true if the graph is linked else return false
     */
    @Override
    public boolean isConnected() { //i got help from the site https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
        if(this.ga.edgeSize()<this.ga.nodeSize()-1) return false;
        initializeInfo();//initialize all the info fields to be null for the algorithm to work
        if(this.ga.nodeSize()==0||this.ga.nodeSize()==1) return true;//if there is not node or one its connected
        WGraph_DS copy = (WGraph_DS) (copy());//create a copy graph that the algorithm will on it
        LinkedList<node_info> qValues = new LinkedList<>();//create linked list that will storage all nodes that we didn't visit yet
        int firstNodeKey = copy.getV().iterator().next().getKey();//first key for get the first node(its doesnt matter which node
        node_info first = copy.getNode(firstNodeKey);//get the node
        qValues.add(first);//without limiting generality taking the last node added to graph
        int counterVisitedNodes = 0;//counter the times we change info of node to "visited"
        while (qValues.size() != 0) {
            node_info current = qValues.removeFirst();
            if (current.getInfo() != null) continue;//if we visit we can skip to the next loop because we have already marked
            current.setInfo("visited");//remark the info
            counterVisitedNodes++;

            Collection<node_info> listNeighbors = copy.getV(current.getKey());//create a collection for the neighbors list
            LinkedList<node_info> Neighbors = new LinkedList<>(listNeighbors);//create the neighbors list
            if (Neighbors == null) continue;
            for (node_info n : Neighbors) {
                if (n.getInfo() == null) {//if there is a node we didn't visited it yet, we will insert it to the linkedList
                    qValues.add(n);
                }
            }
        }
        if (this.ga.nodeSize() != counterVisitedNodes) return false;//check that we visited all of the nodes

        return true;
    }
    /**
     *the method compute the distance of the shortest path from src to dest in the graph
     * @param src - start node
     * @param dest - end (target) node
     * @return distance of shortest path src-->dest
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(shortestPath(src,dest)==null) return -1;
        return (shortestPath(src,dest).size()-1);
    }
    /**
     *the method return a list represent the path from source to destination in the graph
     * @param src - start node
     * @param dest - end (target) node
     * @return List<node_data>
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        int counter=0;//counter for index of listPath
        List<node_info> listPath=new ArrayList<node_info>();//The reverse list that is returned
        List<node_info> listResult=new ArrayList<node_info>();//the returned list
        if(!DijkstraHelp(src,dest)) return null;//if there is no path from src to dest
        if(src==dest) {
            listPath.add(this.ga.getNode(src));
            return listPath;
        }
        //the other case:
        node_info d=this.ga.getNode(dest);
        listPath.add(counter,d);//insert the dest in order to go from destination to source
        //counter++;
        weighted_graph gCopy=copy();
        Iterator<node_info> it=gCopy.getV().iterator();//run on the whole graph
        while(it.hasNext()){
            if(listPath.get(counter).getKey()==src) break;//if we finished
            if(gCopy.getV(listPath.get(counter).getKey()).contains(it.next())) {//remove the nodes that we were already checked if
                //they need to be insert to listPath
                continue;
            }
            Iterator<node_info> currentIt=gCopy.getV(listPath.get(counter).getKey()).iterator();//iterator on the ni-list of the
            //last node were insert to the listPath
            if(currentIt!=null) {
                node_info current=currentIt.next();
                node_info min=current;
                while (currentIt.hasNext()){
                    if(current.getTag()<min.getTag()) min=current;//update the node that has the minmium tag
                    currentIt.next();
                }
                listPath.add(min);//insert to listPath
                counter++;
            }
        }
        for(int i=listPath.size()-1;i>=0;i--){
            listResult.add(listPath.size()-i-1,listPath.get(i));//reverse the list
        }
        return listResult;
    }
    //------------------------------------------------------------
    //class Entry for enable the priority queue to storage node info objects and sort by the minimum key
    private class Entry implements Comparable<Entry> {
        private node_info node;//info-node
        private double tag;//tag of node
        //constructor
        public Entry(node_info node, double tag) {
            this.tag = tag;
            this.node = node;
        }

        /**
         * compre to between tags for implementation Comparable
         * @param other
         * @return
         */
        @Override
        public int compareTo(Entry other) {
            if(this.tag>other.tag) return 1;
            if(this.tag==other.tag) return 0;
            return -1;
        }
    }

    /**
     * this method change each tag to the distance between src to the mode
     * @param src
     * @param dest
     * @return
     */
    public boolean DijkstraHelp(int src, int dest) {
        PriorityQueue<Entry>queue=new PriorityQueue();//queue storages the nodes that we will visit by the minimum tag
        //WGraph_DS copy = (WGraph_DS) (copy());//create a copy graph that the algorithm will on it
        initializeTag();
        initializeInfo();
        node_info first=this.ga.getNode(src);
        first.setTag(0);//distance from itself=0
        queue.add(new Entry(first,first.getTag()));
        while(!queue.isEmpty()) {
             Entry pair=queue.poll();
            node_info current= pair.node;
            if (current.getInfo() != null) continue;//if we visit we can skip to the next loop because we have already marked
            current.setInfo("visited");//remark the info
            Collection<node_info> listNeighbors = this.ga.getV(current.getKey());//create a collection for the neighbors list
            LinkedList<node_info> Neighbors = new LinkedList<>(listNeighbors);//create the neighbors list
            if (Neighbors == null) continue;
            for(node_info n:Neighbors) {

                if(n.getTag()>ga.getEdge(n.getKey(),current.getKey())+current.getTag())
                {
                    n.setTag(current.getTag() + ga.getEdge(n.getKey(), current.getKey()));//compute the new tag
                }
                queue.add(new Entry(n,n.getTag()));
            }
        }
        Iterator<node_info> it=this.ga.getV().iterator();
        while(it.hasNext()){
            if(it.next().getInfo()==null) return false;
        }
        return true;
    }

    /**
     * this method Saves the graph to the given file name
     * @param file - the file name (may include a relative path).
     * @return true if the file saved and false if not
     */
    @Override
    public boolean save(String file) {
        boolean ans = false;
        ObjectOutputStream oos;
        try {
            FileOutputStream fileOut = new FileOutputStream(file, true);
            oos = new ObjectOutputStream(fileOut);
            oos.writeObject(this);
            ans= true;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace(); }
        catch (IOException e) {e.printStackTrace();}
        return ans;
    }
    /**
     * This method load a graph to this graph algorithms
     * @param file - file name
     * @return true if the file changed else false
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream streamIn = new FileInputStream(file);
            ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
            WGraph_Algo readCase = (WGraph_Algo) objectinputstream.readObject();
             this.ga=null;//initialize the graph
             this.ga=readCase.ga;//take the graph from readCase to this.ga
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * this method check if object ot is equal to this.ga
     * @param ot
     * @return true if equal else return false
     */
    public boolean equals(Object ot){
        if(ot==null || !(ot instanceof WGraph_DS)){return false;}
        Iterator<node_info> it= ((WGraph_DS) ot).getV().iterator();
        if(((WGraph_DS) ot).getMC()!=this.ga.getMC() || ((WGraph_DS) ot).edgeSize()!=this.ga.edgeSize()||
                ((WGraph_DS) ot).nodeSize()!=this.ga.nodeSize()) return false;//try to fail if one of the fields is not equal to ga fields
        while(it.hasNext()){//check if the graph structures are equal
            if(!containsNode(it.next())) return false;
        }
        return true;
    }
    /**
     *this method cheack if ot.graph is equal to this.ga.graph
     * @param n
     * @return
     */
    public boolean containsNode(node_info n){
        Iterator<node_info> it=this.ga.getV().iterator();
        while (it.hasNext()){
            if(it.next().getKey()==n.getKey()) return true;//if the keys are equal its enough
        }
        return false;
    }
}