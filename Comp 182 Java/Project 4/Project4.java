/*Author:  James Renger
Course:     Comp 182(16203-SP17)
Project:    Project 2
Program function:  The purpose of this progam is to input a csv file of product
data, store the data as a linked array of objects, then input a csv file of sales
data.  Once the sales data has been input into the program, this data should use
the product data to determine the customer sales receipts, and a total inventory
of products sold.
External source code used/augmented for this project:  Project 1.
File List for project: Project4.java, maze.csv
*/
package project4;

//Libraries
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;



public class Project4 {

    public static void main(String[] args) {

        // Variable Filenames
        LinkedList<String> path = new LinkedList<>();
        Integer[][] mazeIntArray = new Integer [41][41];
        int startRow = 40;
        int startCol = 17;
        String csvFile = "Maze - Input.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] inputCSVData;
        int rowTrackCounter = 0;


        /*
	Process Maze Into Multi Dimensional Array.
        */
        try {
            br = new BufferedReader(new FileReader(csvFile));         

            //Clears out the labels
            String[] labelsString = br.readLine().split(cvsSplitBy);
                              
            
            //Each row of buffered reader increases the row counter which controls
            //the row of the storage array being writen to
            
            while ((line = br.readLine()) != null) {
                               
                    //Convert the file line by line into a string, seperating with ,
                inputCSVData = line.split(cvsSplitBy);
                //load a line at a time, with for to control the col.s
                    for(int colTrackCounter = 0; colTrackCounter <= 40; colTrackCounter++)
                        {                    
                            mazeIntArray[rowTrackCounter][colTrackCounter]
                                = Integer.parseInt(inputCSVData[colTrackCounter + 1]);
                        }
            rowTrackCounter++;     
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
        
    for(int row = 0; row < 41; row++)
    {
        for(int col = 0; col < 41; col++)
        {
            System.out.print(mazeIntArray[row][col] + " ");
        }
    System.out.println(row);
    }
    solveMaze(mazeArray,0,0,40,17,path);
    System.out.println("The Route Through the Maze is:");
    while(path.size() > 0){
        System.out.println(path.getFirst());
        path.removeFirst();        
    }
    
    }
    // End of Main   
    
    public static LinkedList<String> solveMaze(Integer[][] maze, int passRow, int passCol, 
            int tarRow, int tarCol, LinkedList<String> newPath){
        String Temp;
        if((tarRow+1) != passRow && maze[tarRow+1][tarCol] == 1){            
                    newPath = solveMaze(maze, tarRow, tarCol,tarRow++, tarCol,newPath);
                    if(newPath.size() > 0){
                        Temp = Integer.toString(tarRow) + "," +Integer.toString(tarCol);
                        newPath = newPath.addFirst(Temp);
            }
        if((tarRow -1 ) != passRow && maze[tarRow-1][tarCol] == 1){            
                    newPath = solveMaze(maze, tarRow, tarCol,tarRow--, tarCol,newPath);
                    if(newPath.size() > 0)
                        newPath = newPath.addFirst(Integer.toString(tarRow) + "," + Integer.toString(tarCol));
        if((tarCol + 1) != passCol && maze[tarRow][tarCol + 1] == 1){            
                    newPath = solveMaze(maze, tarRow, tarCol,tarRow, tarCol++,newPath);
                    if(newPath.size() > 0)
                        newPath = newPath.addFirst(Integer.toString(tarRow) + "," + Integer.toString(tarCol));
        if(tarCol -1) != passCol && maze[tarRow][tarCol -1] == 1){            
                    newPath = solveMaze(maze, tarRow, tarCol,tarRow, tarCol--,newPath);                   
                    if(newPath.size() > 0)
                        newPath = newPath.addFirst(Integer.toString(tarRow) + "," + Integer.toString(tarCol));     
                                    }            
    }               
   return newPath; 
        }
