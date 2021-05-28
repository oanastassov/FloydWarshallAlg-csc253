//-----------------------------------------------------
// Author: 		Olivia Anastassov
// Date: 		25/4/2021
// Description:	Driver for 
// 				Floyd–Warshall Algorithm
// Used template provided by Professor Streinu
//-----------------------------------------------------

import java.io.*;
// import java.lang.System;
// import java.util.LinkedList;

public class MainAPSP {
	final static boolean DEBUG = true; 
	private static String makeAbsoluteInputFilePath(String inputFilePath){
		File input = new File(inputFilePath);
		String absInputFilePath = "";
		try{
			absInputFilePath= input.getCanonicalPath();
		} catch(IOException e) {
			System.out.println("ERROR: cound not run getCanonicalPath on file "+inputFilePath);
		}

		return absInputFilePath;
	}
	private static String makeAbsoluteInputFilePathWithExt(String inputFilePath, String ext){
		File input = new File(inputFilePath);
	
		String inputFileFolder = input.getParent();
		String fileName = input.getName();
		String name = fileName.split("\\.")[0];
			
		
		// make output file path with extension
		String newInputFilePath = inputFileFolder + "/" + name + "." + ext;
		
		File newInput= new File(newInputFilePath);
		
		String absNewInputFilePath = "";
		try{
			absNewInputFilePath = newInput.getCanonicalPath();
		} catch(IOException e) {
			System.out.println("ERROR: cound not run getCanonicalPath on file "+newInputFilePath);
		}
		
		return absNewInputFilePath;
	}	
	private static String makeAbsoluteOutputFilePathWithExt(String inputFilePath, String outputFileFolder, String ext){
		File input = new File(inputFilePath);
	
		String parentPath = input.getParent();
		String fileName = input.getName();
		String name = fileName.split("\\.")[0];
			
		
		// make output file path with extension
		String outputFilePath = outputFileFolder + "/" + name + "." + ext;
		
		File output= new File(outputFilePath);
		
		String absOutputFilePath = "";
		try{
			absOutputFilePath = output.getCanonicalPath();
		} catch(IOException e) {
			System.out.println("ERROR: cound not run getCanonicalPath on file "+outputFilePath);
		}
		
		return absOutputFilePath;
	}
	
	private static void copyFileUsingStream(String inputFilePath, String outputFilePath) throws IOException {
	  
		File source = new File(inputFilePath);
		File dest = new File(outputFilePath);
		InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}

	//PRINT DEBUG
	public static void printDebug(String s){
		if(DEBUG){
			System.out.println(s);
		}
	}

	// MAIN
    public static void main(String[] args) {
        
		if (args.length < 2){	
			return;
		}
		
		//-----------------------------
		// Input and output files
		// taken from the command line
		//-----------------------------
		String inputFilePath = args[0];
		String outputFileFolder = args[1];
		
		String absInputFilePath = makeAbsoluteInputFilePath(inputFilePath);
		String absInputGrfFilePath = makeAbsoluteInputFilePathWithExt(inputFilePath,"grf");
		String absOutputGrfFilePath = makeAbsoluteOutputFilePathWithExt(inputFilePath,outputFileFolder,"grf");
        String absOutputTreFilePath = makeAbsoluteOutputFilePathWithExt(inputFilePath,outputFileFolder,"tre");
		
		// read graph
		printDebug("======  STEP 1: READ DGRAPH FROM TXT FILE");
		DGraphEdges dGraph = new DGraphEdges();
		dGraph.readFromTxtFile(absInputFilePath);
		printDebug("......  DONE STEP 1");

		printDebug("======  STEP 2: PRINT DGRAPH ");
		dGraph.printDGraph();
		printDebug("......  DONE STEP 2");

		printDebug("======  STEP 3: CONVERT TO ADJACENCY Matrix ");
		DGraphMatrix dGraphMatrix = dGraph.toMatrix();
		printDebug("......  DONE STEP 3");

		printDebug("======  STEP 4: PRINT ADJACENCY Matrix ");
		dGraphMatrix.print();
		printDebug("......  DONE STEP 4");

		printDebug("======  STEP 5: COMPUTE All Pairs Shortest Path");
		AllPairsShortestPathDataStructures APSP = dGraphMatrix.FWA();
		printDebug("......  DONE STEP 5");

		printDebug("======  STEP 6: PRINT All Pairs Shortest Path and Shortest Path Tree");
        APSP.printAllPairsShortestPath();
		printDebug("......  DONE STEP 6");

		printDebug("======  STEP 7: WRITE All Pairs Shortest Path and All Pairs Shortest Path Tree TO MATHEMATICA FILE");
        APSP.writeAPSPToMathematicaFile(absOutputTreFilePath);
		printDebug("......  DONE STEP 7");
	
		printDebug("======  STEP 8: COPY GRF TO OUTPUT FOLDER");
		try {
			copyFileUsingStream(absInputGrfFilePath,absOutputGrfFilePath);
		}  
		catch (IOException e) {
            System.out.println("ERROR: IOException.");
            e.printStackTrace();
		}
		printDebug("......  DONE STEP 8");

	}
}
