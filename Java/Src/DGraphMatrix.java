//-----------------------------------------------------
// Author: Olivia Anastassov
// Date: 25/4/2021
// Description: Adjacency Matrix Graph Class
//-----------------------------------------------------
import java.util.*;
import java.io.*;
class DGraphMatrix{
    private ArrayList< ArrayList<Integer> > graph;
    private ArrayList< ArrayList<Integer> > weights;
//------------------------------------- 
// Constructors 
//-------------------------------------
    public DGraphMatrix(){
        graph = new ArrayList< ArrayList<Integer> >();
        weights = new ArrayList< ArrayList<Integer> >();
    }
    public DGraphMatrix(int numVert){
        graph = new ArrayList< ArrayList<Integer> >(numVert);
        weights = new ArrayList< ArrayList<Integer> >(numVert);
        for (int i = 0; i < numVert; i++) {
            graph.add(new ArrayList<Integer>());
            weights.add(new ArrayList<Integer>());
            for (int j = 0; j < numVert; j++) {
                graph.get(i).add(0);
                weights.get(i).add(0);
            }
        }
    }
//------------------------------------- 
// Modifiers
//-------------------------------------
    public void addEdge(int u, int v, int weight){
        graph.get(u).set(v, graph.get(u).get(v)+1);
        weights.get(u).set(v, weight);
    }
//------------------------------------- 
// Serialize and Print
//-------------------------------------
    public String toString(){
        String output = "";
        for (int i = 0; i < graph.size(); i++) {
            //first row add labels
            if(i == 0){
                output = output + "X ";
                for(int a = 0; a < graph.size(); a++){
                    output = output + a + " ";
                }
                output = output + "\n";
            }
            //print row label then row
            output = output + i + " ";
            for (int j = 0; j < graph.size(); j++) {
                output = output + graph.get(i).get(j) + " ";
            }
            output = output + "\n";
        }
        return output;
    }
    public void print(){
        System.out.print(toString());
    }

//------------------------------------- 
// Getters
//-------------------------------------
    public boolean hasEdge(int u, int v){
        if(graph.get(u).get(v) >= 1)
            return true;
        else
            return false;
    }
//------------------------------------- 
// Floyd–Warshall Algorithm
// Computes shortest path between all pairs of verteces
// in the matrix
// All pairs shortest path
// https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
//-------------------------------------
    public AllPairsShortestPathDataStructures FWA(){
        AllPairsShortestPathDataStructures FWData = new AllPairsShortestPathDataStructures(graph.size());
        for(int i = 0; i<graph.size(); i++){
            for(int j = 0; j<graph.get(i).size(); j++){
                if(hasEdge(i,j) == true){
                    FWData.distance[i][j] = weights.get(i).get(j);
                }
                else{
                    FWData.distance[i][j] = 100000000;
                }
            }
        }
        for(int i = 0; i<graph.size(); i++){
            FWData.distance[i][i] = 0;
        }
        for(int k = 0; k<graph.size();k++){
            for(int i = 0; i<graph.size(); i++){
                for(int j = 0; j<graph.size(); j++){
                    if(FWData.distance[i][j]> FWData.distance[i][k] + FWData.distance[k][j]){
                        FWData.distance[i][j] = FWData.distance[i][k] + FWData.distance[k][j];
                    }
                }
            }
        }
        return FWData;
    }
}//adjMatrixGraph