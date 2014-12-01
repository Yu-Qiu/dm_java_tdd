package test;

import main.Graph;
import main.Vertex;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTest {
    private Vertex lille = new Vertex("Lille");
    private Vertex paris = new Vertex("Paris");
    private Vertex reims = new Vertex("Reims");
    private Vertex nancy = new Vertex("Nancy");
    private Vertex lyon = new Vertex("Lyon");
    private Vertex marseille = new Vertex("Marseille");
    private Vertex lemans = new Vertex("Le Mans");
    private Vertex nantes = new Vertex("Nantes");
    private Vertex bordeaux = new Vertex("Bordeaux");
    private Vertex toulouse = new Vertex("Toulouse");
    private Vertex clermont = new Vertex("Clermont Ferrant");
    private Vertex montpellier = new Vertex("Montpellier");

    @Before
    public void setup() {
        lille.connectTo(reims, 206);
        lille.connectTo(paris, 222);
        lille.connectTo(nancy, 418);

        reims.connectTo(paris, 144);
        reims.connectTo(nancy, 245);
        reims.connectTo(lyon, 489);

        paris.connectTo(lyon, 465);
        paris.connectTo(lemans, 208);
        paris.connectTo(clermont, 423);

        lyon.connectTo(clermont, 166);
        lyon.connectTo(marseille, 313);
        lyon.connectTo(montpellier, 304);

        lemans.connectTo(nantes, 189);
        lemans.connectTo(bordeaux, 443);

        nantes.connectTo(bordeaux, 347);

        bordeaux.connectTo(toulouse, 243);

        toulouse.connectTo(montpellier, 245);

        montpellier.connectTo(marseille, 169);
        montpellier.connectTo(toulouse, 245);

        marseille.connectTo(montpellier, 169);

        clermont.connectTo(lyon, 166);
        clermont.connectTo(montpellier, 333);
        clermont.connectTo(marseille, 474);
    }

    @Test
    public void getDistanceForTwoAdjacentVertices() {
        Graph graph = new Graph(lille, reims, paris, lyon, lemans, nantes, bordeaux, toulouse, montpellier, marseille, clermont, nancy);

        assertEquals(graph.getDistance("Paris", "Lyon"), 465);
         /** les deux villes ne sont pas adjacents */
        assertEquals(graph.getDistance("Paris","Marseille"),-1);
        /** la ville destination n'existe pas */
        assertEquals(graph.getDistance("Paris","Zhengzhou"),-1);

        assertEquals(graph.getDistance("Lille","Nancy"),418);
    }


    /**Chercher une distance en passant par une ville intermédiaire*/
    @Test
    public void getDistanceForTwoVerticesPassVertex(){
        Graph graph = new Graph(lille, reims, paris, lyon, lemans, nantes, bordeaux, toulouse, montpellier, marseille, clermont, nancy);

        assertEquals(graph.getDistancePassVertex("Paris","Nantes"),397);
        assertEquals(graph.getDistancePassVertex("Paris","Marseille"),778);
        /** il faut passer plus d'une ville */
        assertEquals(graph.getDistancePassVertex("Lille","Bordeaux"),-1);
        /** la ville destination n'existe pas */
        assertEquals(graph.getDistancePassVertex("Lille","Zhengzhou"),-1);
    }

    /**Chercher une distance en passant par deux ville intermédiaire*/

    @Test
    public void getDistanceForTwoVerticesPassTwo(){
        Graph graph = new Graph(lille, reims, paris, lyon, lemans, nantes, bordeaux, toulouse, montpellier, marseille, clermont, nancy);

        assertEquals(graph.getDistancePassTwoVertices("Paris","Toulouse"),894);
        assertEquals(graph.getDistancePassTwoVertices("Lille","Marseille"),1000);

    }

    /**Chercher une distance en passant par N ville*/

    @Test
    public void getDistanceForTwoVerticesPassN(){
        Graph graph = new Graph(lille, reims, paris, lyon, lemans, nantes, bordeaux, toulouse, montpellier, marseille, clermont, nancy);
    /** on choisit un nombre assez grand (10) pour calculer tous les chemins possibles */
        assertEquals(graph.getDistancePassNVertices("Paris","Toulouse",10),894);
        assertEquals(graph.getDistancePassNVertices("Paris", "Lyon",10), 465);
        assertEquals(graph.getDistancePassNVertices("Lille","Zhengzhou",10),-1);
    }

    /** si la graphe est vide */
    @Test
    public void emptyGraph(){
        Graph graph = new Graph();

        assertEquals(graph.getDistancePassNVertices("Paris","Lyon",10),-1);
    }

}