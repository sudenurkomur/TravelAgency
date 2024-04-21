// Java program to implement Graph
// with the help of Generics

import java.util.*;

class Graph<T, I extends Number> {

    private Map<T, List<Pair<T, Integer>>> map = new HashMap<>();

    public void addVertex(T vertex) {
        map.put(vertex, new LinkedList<Pair<T, Integer>>());
    }

    public void addEdge(T source, T destination, int duration, boolean bidirectional) {
        if (!map.containsKey(source))
            addVertex(source);

        if (!map.containsKey(destination))
            addVertex(destination);

        map.get(source).add(new Pair<>(destination, duration));
        if (bidirectional) {
            map.get(destination).add(new Pair<>(source, duration));
        }
    }


    // This function gives the count of vertices
    public void getVertexCount() {
        System.out.println("The graph has "
                + map.keySet().size()
                + " vertices");
    }

    // This function gives the count of edges
    public void getEdgesCount(boolean bidirection) {
        int count = 0;
        for (T v : map.keySet()) {
            count += map.get(v).size();
        }
        if (bidirection == true) {
            count = count / 2;
        }
        System.out.println("The graph has " + count
                + " edges.");
    }

    // This function gives whether
    // a vertex is present or not.
    public void hasVertex(T s) {
        if (map.containsKey(s)) {
            System.out.println("The graph contains " + s
                    + " as a vertex.");
        } else {
            System.out.println("The graph does not contain "
                    + s + " as a vertex.");
        }
    }

    // This function gives whether an edge is present or
    // not.
    public void hasEdge(T s, T d) {
        if (map.get(s).contains(d)) {
            System.out.println(
                    "The graph has an edge between " + s
                            + " and " + d + ".");
        } else {
            System.out.println(
                    "The graph has no edge between " + s
                            + " and " + d + ".");
        }
    }

    public void neighbours(T s) {
        if (!map.containsKey(s))
            return;
        System.out.println("The neighbours of " + s + " are");
        for (Pair<T, Integer> w : map.get(s))
            System.out.print(w + ",");
    }

    // Prints the adjacency list of each vertex.
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (T v : map.keySet()) {
            builder.append(v.toString() + ": ");
            for (Pair<T, Integer> pair : map.get(v)) {
                builder.append("(" + pair.getFirst().toString() + ", " + pair.getSecond() + ") ");
            }
            builder.append("\n");
        }

        return (builder.toString());
    }

    public int getDuration(T source, T destination) {
        List<Pair<T, Integer>> neighbors = map.get(source);
        for (Pair<T, Integer> pair : neighbors) {
            if (pair.getFirst().equals(destination)) {
                return pair.getSecond();
            }
        }
        // Eğer belirtilen komşu yoksa, -1 ya da uygun bir değer döndürebilirsiniz.
        return -1;
    }


}

// Driver Code
public class Main {

    public static void main(String args[]) {

        // Object of graph is created.
        Graph<String, Integer> g = new Graph<String, Integer>();

        // edges are added.
        // Since the graph is bidirectional,
        // so boolean bidirectional is passed as true.

        g.addEdge("Hotel", "Museum", 10, true);
        g.addEdge("Hotel", "Tower", 20, true);
        g.addEdge("Hotel", "Park", 30, true);
        g.addEdge("Park", "Tower", 18, true);
        g.addEdge("Park", "Museum", 15, true);
        g.addEdge("Museum", "Tower", 25, true);

        System.out.println(g.toString());

        System.out.println(g.getDuration("Hotel", "Museum"));


    }
}


class Pair<T, U> {
    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }
}