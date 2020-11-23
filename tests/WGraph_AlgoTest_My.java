package ex1.tests;

import ex1.src.node_info;
import ex1.src.WGraph_Algo;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest_My {

    @Test
    void init() {
        weighted_graph g = WGraph_DSTest_My.graphCreator(10, 20, 1);
        weighted_graph_algorithms ga_0 = new WGraph_Algo();
        ga_0.init(g);

        assertNotNull(ga_0.getGraph());
    }

    @Test
    void getGraph() {
        weighted_graph g = WGraph_DSTest_My.graphCreator(10, 20, 1);
        weighted_graph g1 = WGraph_DSTest_My.graphCreator(10, 20, 2);
        weighted_graph_algorithms ga_0 = new WGraph_Algo();
        ga_0.init(g);
        assertEquals(ga_0.getGraph(), g);
        g.removeNode(1);
        assertNotEquals(ga_0.getGraph(), g1);

    }

    @Test
    void copy() {
        weighted_graph g = WGraph_DSTest_My.graphCreator(10, 20, 1);
        weighted_graph_algorithms ga_0 = new WGraph_Algo();
        ga_0.init(g);
        weighted_graph g1 = ga_0.copy();

        assertEquals(g1, g);
    }

    @Test
    void isConnected() {
        weighted_graph g = WGraph_DSTest_My.graphCreator(10, 0, 1);
        for (int i = 0; i < g.nodeSize() - 1; i++) {
            g.connect(i, i + 1, i);
        }
        weighted_graph_algorithms ga_0 = new WGraph_Algo();
        ga_0.init(g);
        assertTrue(ga_0.isConnected());
        g.removeNode(1);
        assertFalse(ga_0.isConnected());
    }

    @Test
    void shortestPathDist() {
        weighted_graph g = WGraph_DSTest_My.graphCreator(10, 0, 1);
        g.connect(1, 3, 1.52);
        g.connect(3, 6, 2.50);
        g.connect(1, 8, 4.21);
        g.connect(8, 7, 1.52);
        g.connect(7, 9, 1.24);
        g.connect(1, 6, 2.50);
        g.connect(6,9,2.50);


        weighted_graph_algorithms ga_0 = new WGraph_Algo();
        ga_0.init(g);
        double x = ga_0.shortestPathDist(1, 9);
        System.out.print(x);
        assertEquals(ga_0.shortestPathDist(1, 9), 5, 0.01);
    }

    @Test
    void shortestPath() {
        weighted_graph g = WGraph_DSTest_My.graphCreator(10, 0, 1);
        g.connect(1, 3, 1.52);
        g.connect(3, 6, 2.50);
        g.connect(1, 8, 4.21);
        g.connect(8, 7, 1.52);
        g.connect(7, 9, 1.24);
        g.connect(1, 6, 2.50);
        g.connect(6,9,2.50);


        weighted_graph_algorithms ga_0 = new WGraph_Algo();
        ga_0.init(g);
        double[] d = {0, 2.50, 5};
        List<node_info> x = ga_0.shortestPath(1, 9);
        Iterator<node_info> n = x.listIterator();
        int i = 0;
        while (n.hasNext()) {
            node_info temp = n.next();
            System.out.print(temp.getKey() + " - " + temp.getTag() + " , ");
            assertEquals(temp.getTag(), d[i], 0.01);
            i++;
        }
        assertEquals(ga_0.shortestPathDist(1, 9), 5, 0.0001);


    }

    @Test
    void save_load() {
        weighted_graph g = WGraph_DSTest_My.graphCreator(28, 4, 3);
        weighted_graph_algorithms ga0 = new WGraph_Algo();
        ga0.init(g);
        String str = "g.obj";
        ga0.save(str);
        weighted_graph g1 = WGraph_DSTest_My.graphCreator(28, 4, 3);
        ga0.load(str);
        assertEquals(g, g1);
        g.removeNode(0);
        assertNotEquals(g, g1);
    }
}