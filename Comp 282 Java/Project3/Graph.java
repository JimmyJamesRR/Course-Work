// Team Member Bones: Renger, James 
// Class: COMP282 
// Assignment: Project 3
// Filelist: Graph.java


// Detect the number of nodes on the input, replaced the requirement of marked
// being the full alphabet, and make it only the amount of nodes present

package graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Graph
{
    char[] key = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    int[][] graphDat = new int[26][26];
    int nodes = 0;
    
     public static void main(String [] args)
     {
        //args[0] = "test.txt";
       // args[1] = "a";
        Graph mygraph = new Graph(args[0]);
        mygraph.shortestPath(args[1].charAt(0));
     }
    private Graph(){
        for(int index = 0; index < 26; index++){
            for(int index2 = 0; index2 < 26; index2++){
                graphDat[index][index2] = -1;
            }
        }
    }            
    private Graph(String args){        
        graphDat = new int[26][26];        
        this.graphDat = fileToGraph(args);
    }    
    private int[][] fileToGraph(String fileName){
        int [][] newGraph = new int [26][26];
        boolean [] added = new boolean[26];
        String[] split = new String[20];
        split = fileName.split("\\s+");
        for(int index = 0; index < 26; index++){
            for(int index2 = 0; index2 < 26; index2++){
                if(index == index2)
                    newGraph[index][index2] = 0;
                else
                    newGraph[index][index2] = -1;                
            }
        }
        BufferedReader br = null;
        String line = "";
        String lineSplitBy = "\\s+";
        String[] fileData;

        try {
            br = new BufferedReader(new FileReader(fileName));                     
            
            while ((line = br.readLine()) != null) { 
                String[] inputString = line.split(lineSplitBy);
                fileData = line.split(lineSplitBy);
                if(!added[getKeyInt(inputString[0].charAt(0))]){
                    added[getKeyInt(inputString[0].charAt(0))] = true;
                    nodes++;
                }
                newGraph[getKeyInt(inputString[0].charAt(0))]
                    [getKeyInt(inputString[0].charAt(1))] 
                        = Integer.parseInt(inputString[1]);
                newGraph[getKeyInt(inputString[0].charAt(1))]
                        [getKeyInt(inputString[0].charAt(0))] 
                        = Integer.parseInt(inputString[1]);

            }    
        //Buffer exceptions
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        return newGraph;
    }    
    private int getKeyInt(char target){
        for(int index = 0;index < 26;index++){
            if(target == key[index])
                return index;
        }
        return '*';
    }    
    private void shortestPath(char origin){
        int nxtStep = -1,
            nxtStepDist = -1;
        int currStep = 0;
        int currNode = -1;
        int marked = -1;
        String[] path = new String[26];
        String[] finalPath = new String[26];
        Graph dijkCheck = new Graph();
        //Find Origin, set first step to origin, focus origin, update all path        
        for(int index = 0; index < 26;index++){                            
            if(key[index] == origin){                                        
                currNode = index;
                path[index] = "" + origin;
                currStep++;
                marked++;
                dijkCheck.key[index] = '*';                         
            }
        }
        for(int index = 0; index < 26;index++)
            dijkCheck.graphDat[index][currStep] = graphDat[currNode][index];
        while(marked < nodes){
            nxtStep = -1;
            nxtStepDist = -1;
            //Update the row
            for(int index = 0; index < 26;index++){
                if(dijkCheck.key[index] == '*'){
                    continue;
                }else 
                    dijkCheck.graphDat[index][currStep] 
                        = dijkCheck.graphDat[index][currStep - 1];
                //if curr node has a better path than current best, update
                if(graphDat[currNode][index] > 0 
                        && dijkCheck.graphDat[index][currStep] < 0 ){                   
                        dijkCheck.graphDat[index][currStep] 
                                = graphDat[currNode][index];
                        path[index] = path[currNode] + key[index];
                        
                }else if(dijkCheck.graphDat[index][currStep]
                        < dijkCheck.graphDat[index][currStep] 
                        + graphDat[currNode][index]                         
                        && graphDat[currNode][index] != -1 
                        && dijkCheck.graphDat[index][currStep] > 0)
                {
                    dijkCheck.graphDat[index][currStep]
                        = dijkCheck.graphDat[index][currStep] 
                        + graphDat[currNode][index];
                    path[index] = path[currNode] + key[index];
                }
                if((dijkCheck.graphDat[index][currStep] < nxtStepDist  
                        && dijkCheck.graphDat[index][currStep] > 0)
                    || (nxtStepDist == -1 
                        && dijkCheck.graphDat[index][currStep] > 0)){
                    nxtStep = index;
                    nxtStepDist = dijkCheck.graphDat[index][currStep];                    
                }                                               
            }
            dijkCheck.key[currNode] = '*';//Mark the node
            marked++;//advance the row counter update next nodes path
            finalPath[currNode] = //Path of marked node takes it's 
            String.valueOf  //Final Form  Ex.: "42 lif"
                (dijkCheck.graphDat[currNode][currStep]) 
                    + " " + path[currNode];
            currStep++;
            currNode = nxtStep;    //Take the next step                
        }        
        for(int index = 0; index < 26;index++){
            if(key[index] == origin)
                System.out.println(key[index] + " origin."); 
            else
                System.out.println(key[index] + " " + finalPath[index]);        
        }
    }
}