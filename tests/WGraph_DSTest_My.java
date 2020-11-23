package ex1.tests;


import ex1.src.node_info;
import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest_My {


    public static weighted_graph graphCreator(int v, int e, int seed) {
        DecimalFormat round = new DecimalFormat("##.##");
        round.setRoundingMode(RoundingMode.DOWN);
        Random rand = new Random(seed);
        weighted_graph g = new WGraph_DS();
        int node1, node2;
        double tag;

        while (g.nodeSize() < v) {
            node1 = (int) (v * rand.nextDouble());
            g.addNode(node1);
        }

        while (g.edgeSize() < e) {
            node1 = (int) (v * rand.nextDouble());
            node2 = (int) (v * rand.nextDouble());
            tag = (rand.nextDouble() * v);

            g.connect(node1, node2, (
                    Double.valueOf(round.format(tag))));
        }

        return g;
    }

    @Test
    void getNode() {
        weighted_graph g = graphCreator(10, 20, 1);
        weighted_graph g1 = graphCreator(10, 20, 1);
        Iterator<node_info> current;
        int edgesize = 0;
        for (node_info n : g.getV()
        ) {
            System.out.print(n.getKey() + " - (");
            current = g.getV(n.getKey()).iterator();
            while (current.hasNext()) {
                int x = current.next().getKey();
                double y = g.getEdge(n.getKey(), x);
                System.out.print(x + " - " + y + ", ");

                edgesize++;
            }
            System.out.print(")" + edgesize + "\n");
        }
        System.out.print("\n");

        for (node_info n : g1.getV()
        ) {
            System.out.print(n.getKey() + " - (");
            current = g1.getV(n.getKey()).iterator();
            while (current.hasNext()) {
                System.out.print(current.next().getKey() + " ");
            }
            System.out.print(")");
        }
    }

    @Test
    void hasEdge() {
        weighted_graph g = graphCreator(10, 20, 1);
        assertTrue(g.hasEdge(0, 1));
        g.removeEdge(0, 1);
        assertFalse(g.hasEdge(0, 1));
    }

    @Test
    void getEdge() {
        weighted_graph g = graphCreator(10, 20, 1);
        g.removeEdge(2, 0);
        g.connect(2, 0, 2.1);
        assertEquals(2.1, g.getEdge(0, 2));
    }

    @Test
    void addNode() {
        weighted_graph g1 = graphCreator(100, 300, 3);
        g1.addNode(105);
        assertEquals(105, g1.getNode(105).getKey());
    }

    @Test
    void connect() {
        weighted_graph g1 = graphCreator(100, 20, 4);
        g1.removeEdge(2, 42);
        assertFalse(g1.hasEdge(2, 42));
        g1.connect(2, 42, 3.2);
        assertTrue(g1.hasEdge(2, 42));
        assertEquals(g1.getEdge(2, 42), 3.2);
    }

    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        int[] nodes = new int[25];
        for (int i = 0; i < 25; i++) {
            nodes[i] = i * 3;
            g.addNode(nodes[i]);
            g.getNode(nodes[i]).setTag(i);
        }

        Collection nodeV = g.getV();
        Iterator<node_info> node = g.getV().iterator();
        while (node.hasNext()) {
            node_info n = node.next();
            assertNotNull(n);
            assertEquals(n.getKey(), nodes[(int) n.getTag()]);
        }
    }

    @Test
    void testGetV() {
        weighted_graph g = new WGraph_DS();
        int[] nodes = new int[25];
        for (int i = 0; i < 25; i++) {
            nodes[i] = i;
            g.addNode(nodes[i]);
            g.getNode(nodes[i]).setTag(i);
            if (i > 3) {
                g.connect(i, (i - 1), 24);
                g.connect(i, (i - 2), 25);
            }
        }

        Collection nodeV = g.getV(4);
        Iterator<node_info> node = nodeV.iterator();
        while (node.hasNext()) {
            node_info n = node.next();
            assertNotNull(n);
            assertEquals(n.getTag(), 24, 2);
        }

        g.removeNode(4);
        assertFalse(g.hasEdge(4, 3));

    }

    @Test
    void removeNode() {
        weighted_graph g = graphCreator(10, 15, 1);
        g.connect(1, 2, 2);
        g.connect(1, 5, 2);
        assertTrue(g.hasEdge(1, 2));
        g.removeNode(1);
        assertNull(g.getNode(1));
    }

    @Test
    void removeEdge() {
        weighted_graph g = graphCreator(20, 30, 1);
        g.connect(4, 2, 5);
        g.connect(4, 6, 5);
        assertTrue(g.hasEdge(4, 6));
        g.removeEdge(4, 6);
        assertFalse(g.hasEdge(4, 6));
        assertTrue(g.hasEdge(4, 2));
    }

    @Test
    void nodeSize() {
        weighted_graph g1 = graphCreator(15, 20, 2);
        g1.addNode(4); //exist does nothing
        g1.addNode(20); //new node
        g1.addNode(28); // new node
        assertEquals(g1.nodeSize(), 17);
        g1.removeNode(1);
        assertEquals(g1.nodeSize(), 16);
    }

    @Test
    void edgeSize() {
        weighted_graph g1 = graphCreator(10, 20, 1);
        int i = 0;
        int counter = 15;
        while (i < 10 && counter > 0) {
            for (int j = 9; j > 0 && counter > 0; j--) {
                if (!g1.hasEdge(i, j) && i != j) {
                    g1.connect(i, j, 1);
                    counter--;
                }
            }
            i++;
        }

        assertEquals(g1.edgeSize(), 35);
        g1.removeEdge(1, 2);
        g1.removeEdge(0, 8);
        assertEquals(g1.edgeSize(), 33);

    }

    @Test
    void getMC() {
        weighted_graph g = graphCreator(100, 0, 1);
        //mc=100
        g.connect(1, 5, 2);//mc++;
        g.connect(1, 6, 2);//mc++;
        g.connect(1, 7, 2);//mc++;
        g.connect(1, 5, 2);//does nothing
        g.connect(1, 5, 3);// update weight mc++
        g.connect(1, 8, 2); //mc++;
        g.addNode(2);//does nothing
        g.addNode(105);//mc++;

        assertEquals(g.getMC(), 106);
    }

    @Test
    void testEquals() {
        weighted_graph g1 = graphCreator(10, 15, 1);
        weighted_graph g2 = graphCreator(10, 15, 1);

        assertTrue(g1.equals(g2));

        g1.removeEdge(0, 1);
        assertFalse(g1.equals(g2));
        g1.connect(0, 1, g2.getEdge(0, 1));
        assertTrue(g1.equals(g2));
        g2.removeNode(9);
        assertFalse(g1.equals(g2));


    }


}