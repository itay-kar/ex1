package ex1.src;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;


public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer, node_info> V = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, node_info>> E = new HashMap<>();
    private int Edge;
    private int MC;

    /**
     * return the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return V.get(key);
    }

    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (V.get(node1) != null && V.get(node2) != null) {
            return (E.get(node1).get(node2) != null);
        } else
            return false;
    }

    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (E.get(node1).get(node2) != null) {
            return E.get(node1).get(node2).getTag();
        } else return -1;
    }

    /**
     * add a new node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (V.get(key) == null) {
            V.put(key, new NodeInfo(key));
            E.put(key, new HashMap<>());
            MC++;
        }
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     *
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (w >= 0 && node1 != node2) {
            if (hasEdge(node1, node2) && E.get(node1).get(node2).getTag() != w) {
                E.get(node1).get(node2).setTag(w);
                MC++;
            } else if (!hasEdge(node1, node2)) {
                E.get(node1).put(node2, new NodeInfo(node2, w));
                E.get(node2).put(node1, new NodeInfo(node1, w));
                MC++;
                Edge++;
            }
        }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return V.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     *
     * @param node_id
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> Neighbors = new LinkedList<>();
        for (node_info x : E.get(node_id).values()
        ) {
            Neighbors.add(x);
        }
        return Neighbors;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if (V.get(key) != null) {
            for (node_info current : this.getV(key)
            ) {
                removeEdge(key, current.getKey());
            }
            node_info x = V.get(key);
            E.remove(key);
            V.remove(key);
            MC--;
            return x;
        } else return null;
    }

    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (E.get(node1).get(node2) != null) {
            E.get(node1).remove(node2);
            E.get(node2).remove(node1);
            Edge--;
            MC++;
        }
    }

    /**
     * return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return V.size();
    }

    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return Edge;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     *
     * @return
     */
    @Override
    public int getMC() {
        return MC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return Edge == wGraph_ds.Edge &&
                V.equals(wGraph_ds.V) &&
                E.equals(wGraph_ds.E);
    }

    @Override
    public int hashCode() {
        return Objects.hash(V, E, Edge, MC);
    }

    public class NodeInfo implements node_info, Serializable {
        private double tag;
        private int key;
        private String info = "White";

        public NodeInfo(int key) {
            this.key = key;
        }

        public NodeInfo(node_info x) {
            this.key = x.getKey();
            this.info = x.getInfo();
            this.tag = x.getTag();
        }


        public NodeInfo(int key, double w) {
            this.key = key;
            this.tag = w;
        }

        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         *
         * @return
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * return the remark (meta data) associated with this node.
         *
         * @return
         */
        @Override
        public String getInfo() {
            return info;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         *
         * @param s
         */
        @Override
        public void setInfo(String s) {
            info = s;
        }

        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         *
         * @return
         */
        @Override
        public double getTag() {
            return tag;
        }

        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         *
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            tag = t;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeInfo nodeInfo = (NodeInfo) o;
            return Double.compare(nodeInfo.tag, tag) == 0 &&
                    key == nodeInfo.key &&
                    info.equals(nodeInfo.info);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tag, key, info);
        }
    }
    /**
     * Class Node info creating a node for this unweighted graph with a set key and info to save data
     * and tag that represent weight
     */


}
