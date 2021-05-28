//-----------------------------------------------------
// Author: Olivia Anastassov
// Date: 25/4/2021
// Description: data structures for Floyd–Warshall Algorithm
//-----------------------------------------------------
import java.util.*;
import java.io.*;
public class AllPairsShortestPathDataStructures{
    private int nrV;
    private int nrE;
    public int [][] distance;

//Local var
    final static boolean DEBUG = false;

//------------------------------------- 
//Utilities
//-------------------------------------
    public static int toMathId(int n){
        return n+1;
    }
    public static void printDebug(String message){
        if(DEBUG){
            System.out.println(message);
        }
    }
    public static void printArrayInt(int [] array){
        for(int i = 0; i<array.length; i++){
            printDebug(""+ toMathId(i)+ ": " + array[i]);
        }
    }
    public static void printArrayBoolean(boolean [] array){
        for(int i = 0; i<array.length; i++){
            printDebug("" + toMathId(i) + ": " + array[i]);
        }
    }
//------------------------------------- 
// Constructors 
//-------------------------------------
    public AllPairsShortestPathDataStructures(int nrV){
        this.nrV = nrV;
        this.distance = new int [nrV][nrV];
    }
//-------------------------------------
//Serialization and Print
//-------------------------------------
    public String AllPairsShortestPathToString(){
        String output = "";
        output = output + "{\n";
        for(int i= 0; i<distance.length; i++){//loops over each row
            output = output + "{";
            for(int j = 0; j< distance[i].length; j++){//loops over all the elements in the row
                if(distance[i][j] != 100000000){
                    output = output + distance[i][j] + ",";
                }
                else{
                    output = output + "INF,";
                }
            }
            output = output + "}\n";
        }
        output = output + "}";
        return output.replaceAll(",}", "}");
    }
    public void printAllPairsShortestPath(){
        System.out.print(AllPairsShortestPathToString());
    }
//-------------------------------------
// WriteToFile
//------------------------------------- 
    public void writeAPSPToMathematicaFile(String filename){
    try {
        FileWriter myWriter = new FileWriter(filename); 
        myWriter.write(AllPairsShortestPathToString());
        myWriter.close();
    } 
    catch (IOException e) { 
        System.out.println("An error occurred."); 
        e.printStackTrace();
    } 
    }
}