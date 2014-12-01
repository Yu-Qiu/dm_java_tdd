package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by YuQiu on 26/11/14.
 */
public class Graph {
    private List<Vertex> vertices = new ArrayList<Vertex>();

    public Graph(Vertex... vertices) {
        this.vertices.addAll(Arrays.asList(vertices));
    }


    /**
     * chercher index d'un vertex dans vertices
     */
    public int findVertexIndice(String vertex) {
        Boolean found = false;
        int count = 0;
        while (!found && count < vertices.size()) {
            if (vertices.get(count).getName().equals(vertex)) {
                found = true;
            }
            count++;
        }
        return count - 1;
    }

    /**
     * return une liste String de targets d'un vertex
     */
    public List<String> findTargetsVertex(String from) {

        List<String> targets = new ArrayList<String>();

        int fromIndex = findVertexIndice(from);

        Vertex vertexFrom = vertices.get(fromIndex);

        for (Edge edge : vertexFrom.getEdges()) {
            targets.add(edge.getTarget().getName());
        }

        return targets;
    }

    /**
     * distance entre deux villes adjacents
     */
    public int getDistance(String from, String to) {
        int distance = -1;
        int fromIndex = findVertexIndice(from);

        Vertex vertexFrom = vertices.get(fromIndex);
        for (Edge edge : vertexFrom.getEdges()) {
            if (edge.getTarget().getName().equals(to)) {
                distance = edge.getDistance();
            }
        }

        return distance;
    }

    /**
     * distance entre deux ville en passant par un intermediare
     */
    public int getDistancePassVertex(String from, String to) {
        int distance = -1;
        List<Integer> distances = new ArrayList<Integer>();

        for (String pass : this.findTargetsVertex(from)) {

            if (this.findTargetsVertex(pass).contains(to)) {
                distances.add(this.getDistance(from, pass) + this.getDistance(pass, to));
                /**System.out.println(from);
                System.out.println(findTargetsVertex(from));*/
            }
        }

        if (distances.size()!=0){
            distance = Collections.min(distances);}

        return distance;
    }

    /**
     * distance entre deux villes en passant par deux intermediares
     */
    public int getDistancePassTwoVertices(String from, String to) {
        int distance = -1;
        List<Integer> distances = new ArrayList<Integer>();

        for (String pass1 : this.findTargetsVertex(from)) {
            if (this.getDistancePassVertex(pass1, to) != -1){
                distances.add(this.getDistance(from, pass1) + this.getDistancePassVertex(pass1, to));

                System.out.println(pass1);
                System.out.println(this.findTargetsVertex(pass1));
            }
        }

        /**for (int a:distances){
            System.out.println(a);
        }*/

        if (distances.size()!=0){
            distance = Collections.min(distances);}

        return distance;
    }

    /**
     * distance entre deux villes en passant par N intermediares
     */

    public int getDistancePassNVertices(String from, String to, int n){
        int distanceFinal = -1;
        List<Integer> distances = new ArrayList<Integer>();

        if(this.findVertexIndice(from)!=-1 && this.findVertexIndice(to)!= -1)
             {

                if (this.findTargetsVertex(from).contains(to)) {
                    n = 0;
                    distanceFinal = this.getDistance(from, to);
                } else if (n == 1) {
                    distanceFinal = this.getDistancePassVertex(from, to);
                } else {
                    for (String pass : this.findTargetsVertex(from)) {

                        int d = this.getDistancePassNVertices(pass, to, n - 1);

                        if (d != -1) {
                            int distance = d + this.getDistancePassNVertices(from, pass, n - 1);
                            distances.add(distance);
                        }
                    }
                }

                if (distances.size() != 0) {
                    distanceFinal = Collections.min(distances);
                }
            }
        return distanceFinal;
    }

}

