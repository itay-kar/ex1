package ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms, Serializable {
    private weighted_graph graph = new WGraph_DS();

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return graph;
    }

    /**
     * Compute a deep copy of this weighted graph.
     * This method runs through each of this graph nodes with an iterator _nodeSearch
     * First it adds all the nodes than its iterates through each node neighbors and adds
     * them to this graph.
     *
     * @return
     */

    @Override
    public weighted_graph copy() {
        Iterator<node_info> nodeSearch;
        WGraph_DS temp = new WGraph_DS();

        //adds all nodes to the new graph//
        for (node_info z : this.graph.getV()
        ) {
            temp.addNode(z.getKey());
        }

        //adds all neighbors of each node z//
        for (node_info z : this.graph.getV()) {
            nodeSearch = this.graph.getV(z.getKey()).iterator();

            //connects each node and his neighbors with their tag in the new graph//
            while (nodeSearch.hasNext()) {
                node_info x = nodeSearch.next();
                temp.connect(z.getKey(), x.getKey(), x.getTag());
            }
        }

        return temp;

    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     * <p>
     * This Method tests if a weighted graph is connected by going through each node neighbors
     * and adding them into a queue,each node has info "White" as defualt if node is visited his
     * info turns "Black",and wont be visited again, if a node is added to queue his color turn grey
     * so he wont be added again, there is a count (node_size) that goes down every time node is added
     * into the queue ,once Queue is empty or count (node_size) is 0 , its returns the boolean.
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        Iterator<node_info> nodeSearch;
        node_info current;
        node_info current2;

        //if this graph had one node or is empty returns true//
        if (graph.nodeSize() <= 1) {
            return true;
        }
        //count = nodeSize-1 , start node doesn't count//
        int count = graph.nodeSize() - 1;

        nodeSearch = graph.getV().iterator();
        Stack<node_info> queue = new Stack<>();

        //adds start node into queue , this is a random node//
        queue.push(nodeSearch.next());

        while (!queue.empty()) {
            //condition 1//
            if (count == 0) {
                return true;
            }

            current = queue.pop();
            node_info currentV = graph.getNode(current.getKey());

            //check if node isn't black and runs through hid neighbors with iterator //
            if (!currentV.getInfo().equals("Black")) {
                nodeSearch = this.graph.getV(current.getKey()).iterator();

                while (nodeSearch.hasNext()) {
                    current2 = nodeSearch.next();
                    node_info currentV2 = graph.getNode(current2.getKey());

                    if (currentV2.getInfo().equals("White")) {
                        queue.push(current2);
                        currentV2.setInfo("Grey");
                        count--;
                    }


                }
                graph.getNode(current.getKey()).setInfo("Black");
            }
        }

        if (count == 0) {
            return true;
        }


        return false;
    }


    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * 
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        PriorityQueue<node_info> Queue =
                new PriorityQueue<node_info>(Comparator.comparingDouble(node_info::getTag));
        Iterator<node_info> nodeSearch;
        resetInfo();
        weighted_graph graph1 = this.copy();
        graph1.getNode(src).setTag(0);
        Queue.add(graph1.getNode(src));


        while (!Queue.isEmpty()) {
            node_info current = Queue.poll();
            if (current.getKey() == dest) {
                return current.getTag();
            }

            nodeSearch = graph1.getV(current.getKey()).iterator();
            node_info Vcurrent = graph1.getNode(current.getKey());
            Vcurrent.setTag(Double.max(current.getTag(), Vcurrent.getTag()));
            Vcurrent.setInfo("Black");

            while (nodeSearch.hasNext()) {
                node_info Next = nodeSearch.next();
                node_info VNext = graph1.getNode(Next.getKey());

                if (VNext.getInfo().equals("White")) {
                    VNext.setTag(Double.max(Next.getTag(), VNext.getTag()));
                    Queue.add(Next);
                    VNext.setInfo("Grey");
                } else if (Next.getTag() < VNext.getTag()) {
                    VNext.setTag(Next.getTag());
                    Queue.add(Next);
                }

                Next.setTag(Double.max(Vcurrent.getTag() + Next.getTag(), VNext.getTag()));

                ;

            }

        }

        return -1;

    }

    private void resetInfo() {
        for (node_info z : graph.getV()
        ) {
            z.setInfo("White");

        }
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        PriorityQueue<node_info> Queue =
                new PriorityQueue<node_info>(Comparator.comparingDouble(node_info::getTag));

        Iterator<node_info> nodeSearch;
        LinkedList<node_info> path = new LinkedList<>();
        resetInfo();
        weighted_graph graph1 = this.copy();
        Queue.add(graph1.getNode(src));
        graph1.getNode(src).setInfo("root");


        while (!Queue.isEmpty()) {
            node_info current = Queue.poll();
            if (current.getKey() == dest) {
                break;
            }

            nodeSearch = graph1.getV(current.getKey()).iterator();
            node_info Vcurrent = graph1.getNode(current.getKey());


            while (nodeSearch.hasNext()) {
                node_info Next = nodeSearch.next();
                node_info VNext = graph1.getNode(Next.getKey());
                Next.setTag(Vcurrent.getTag() + graph1.getEdge(current.getKey(), Next.getKey()));

                if (VNext.getInfo().equals("White")) {
                    VNext.setTag(Next.getTag());
                    Queue.add(Next);
                    VNext.setInfo(String.valueOf(current.getKey()));
                } else if (Next.getTag() < VNext.getTag()) {
                    VNext.setTag(Next.getTag());
                    VNext.setInfo(String.valueOf(current.getKey()));
                    Queue.add(Next);
                }
            }
        }

        node_info temp = graph1.getNode(dest);
        graph1.getNode(src).setTag(0);
        while (temp != graph1.getNode(src)) {
            path.push(temp);
            String s = temp.getInfo();
            int n = Integer.decode(s);
            temp = graph1.getNode(n);
            if (temp == graph1.getNode(src)) {
                path.push(temp);
            }
        }

        return path;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {

            // write object to file
            FileOutputStream Graph_Out = new FileOutputStream(file);
            ObjectOutputStream t = new ObjectOutputStream(Graph_Out);
            t.writeObject(this.graph);
            t.close();
            return true;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */

    @Override
    public boolean load(String file) {
        try {
            // read object from file
            FileInputStream Graph_in = new FileInputStream(file);
            ObjectInputStream t = new ObjectInputStream(Graph_in);
            this.graph = ((weighted_graph) t.readObject());
            t.close();


            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;

    }

}

