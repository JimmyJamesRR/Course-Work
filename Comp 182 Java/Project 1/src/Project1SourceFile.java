//Comp 182 Project 1
//James Renger
//Based on Resource Code Parsing Input from course materials
//Inputs CSV data into strings, sorts, and returns data.

//Libraries
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Project1SourceFile {
	
	//Main
	public static void main(String[] args) {
	
		 
		String csvFile = "/Users/Corsair/Desktop/Spring 2017/Comp 182/Project1/Copy of Sales Data.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String[] originalCSVData;
		String[] totalProductID;
		int[] totalUnits;
		int csvCount = 0;
		int productIDCount = 0;
		
		//Allocate Memory to Arrays
		originalCSVData = new String[601];
		totalProductID = new String[200];
		totalUnits = new int[200];
		
		//Process CSV into originalCSVData array, with an infinite loop which breaks when there is no data left to input.
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
	 
			        // use comma as separator
				originalCSVData = line.split(cvsSplitBy);
	 
				System.out.println(originalCSVData[csvCount]);
			}
			csvCount++;
		} 
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
		System.out.println("Done");
	  }
}
		
		/*			

Process originalCSVData array using a loop, which breaks when originalCSVData position csvCount is empty, starting at position 1(skip first ID).
Scan totalProductID, using an infinite loop, for originalCSVData position csvCount .
If totalProductID position productIDCount is a match, break this nested loop.
If totalProductID position productIDCount is empty. 
input value originalCSVData position csvCount.
Break nested loop.
			Else forward productIDCount by 1.
Forward the csvCount by 1.
Store value originalCSVData position csvCount to totalUnits position productIDCount. 
Forward csvCount by 2.
Reset productIDCount.
Convert totalProductID and totalUnits arrays into a CSV file, with a loop that continues until totalProductID position productIDCount is empty.
	Output column headers, new line.
Output totalProductID position productIDCount, output totalUnits position productIDCount, new line.
Forward productIDCount by 1.
*/

