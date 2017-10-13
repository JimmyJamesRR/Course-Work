/*Author:  James Renger
Course:     Comp 182(16203-SP17)
Project:    Project 2
Program function:  The purpose of this progam is to input a csv file of product
data, store the data as a linked array of objects, then input a csv file of sales
data.  Once the sales data has been input into the program, this data should use
the product data to determine the customer sales receipts, and a total inventory
of products sold.
External source code used/augmented for this project:  Project 1.
File List for project: Project2.java, salesReceipt.java, saleItem.java, 
Product.java, Sales Data.csv, Product Data.csv
*/

//Libraries
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;


public class Project2 {

    // Main
    public static void main(String[] args) {
        // Variable Filenames
        String inventoryCSV = "Product Data.csv";
        String salesCSV = "Sales Data.csv";
        
        //populate a linked list of products with the inventory method
        LinkedList<Product> currentInventory = new LinkedList<>
            (inventory(inventoryCSV));
        System.out.println("Test Case 1: Output a Product ADT");
        currentInventory.getFirst().printDetails();
        
        //populate a linked list of salesReceipt with receipts method
        LinkedList<salesReceipt> sales = new LinkedList<salesReceipt>
            (receipts(salesCSV, currentInventory));    
        System.out.println("Test Case 2: Output sales line");
        sales.get(1).items.get(0).printSaleItem();
        System.out.println("Test Case 3: Output a Receipt");
        sales.get(1).calculate();
        sales.get(1).printSalesReceipt();
        salesInventory(sales);
        System.out.println("Test Case 4: Output a Total Sales Receipt");
        sales.get(0).calculate();
        sales.get(0).printSalesReceipt();
        
    
    }
    // End of Main   
    
    //This public method will take the parameter of an inventory filename,
    //and convert it into a linked list of Products.
    public static LinkedList<Product> inventory(String fileName){
        //Return Variable
        LinkedList<Product> converted = new LinkedList<>();
        // Conversion Variables
        String csvFile = fileName;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] originalCSVData;

        /*
	Process CSV into originalCSVData array, with an infinite loop which
	breaks when there is no data left to input.
        */
        try {
            br = new BufferedReader(new FileReader(csvFile));         

            //Output the labels from the Original CSV File for proof of read
            //System.out.println("Test Case 1, Part 1: Proof of CSV read.");
            String[] labelsString = br.readLine().split(cvsSplitBy);
            //System.out.print(Arrays.toString(labelsString));                                
            
            //while loop that will read the data, output for test case, and 
            //store the data into the temp string storage array
            while ((line = br.readLine()) != null) {
               
                //Convert the file line by line into a string, seperating with ,
                originalCSVData = line.split(cvsSplitBy);
                                
                boolean add = converted.add(new Product(
                        Integer.parseInt(originalCSVData[0]), 
                        originalCSVData[1], 
                        Double.parseDouble(originalCSVData[2].substring(1))));
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
        //System.out.println("...Loaded.");
        //System.out.println("--------------------------------");
        return converted;
    }    

    //This Method takes a CSV file of sales, references an inventory, and 
    //populates the sales receipts
    public static LinkedList<salesReceipt> receipts(String fileName, 
            LinkedList<Product> currentInventory){
        //Return Variable
        LinkedList<salesReceipt> converted = new LinkedList<>();
        // Conversion Variables
        String csvFile = fileName;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] originalCSVData;
        int count = 0;
        int sID = 1;
        //Linked list receipts location 0 will be used to hold the inventory
        converted.add(new salesReceipt(0,0));
        
        //populate sales inventory
        while (converted.get(0).items.size() < currentInventory.size()){
            converted.get(0).addItem(currentInventory.get(count)
                    .getProductID(), 0, currentInventory);  
            count++;
        }

	//Process CSV into originalCSVData array, with an infinite loop which
	//breaks when there is no data left to input.
                try {
            br = new BufferedReader(new FileReader(csvFile));         

                //Output the labels from the Original CSV File for proof of read
                //System.out.println("Test Case 2 & 3, Part 1: Proof of CSV read.");
                String[] labelsString = br.readLine().split(cvsSplitBy);
                //System.out.print(Arrays.toString(labelsString));                                
                
                //Start first receipt
                line = br.readLine();
                originalCSVData = line.split(cvsSplitBy);
                converted.add(new salesReceipt(Integer.parseInt
                        (originalCSVData[0]), sID));
                    converted.get(sID).addItem(
                                    Integer.parseInt(originalCSVData[1]), 
                                    Integer.parseInt(originalCSVData[2]), 
                                    currentInventory);

            //while loop that will read the data, output for test case, and 
            //store the data into the temp string storage array
            while ((line = br.readLine()) != null) {
                
                //Convert the file line by line into a string, seperating with ,
                //Populate First Receipt
                originalCSVData = line.split(cvsSplitBy);
                count = 0;
                //If still selling to the same customer, add line item
                if (Integer.parseInt(originalCSVData[0]) == converted.get(sID)
                        .getCustomerID()){
                    
                    converted.get(sID).addItem(
                                    Integer.parseInt(originalCSVData[1]), 
                                    Integer.parseInt(originalCSVData[2]), 
                                    currentInventory);
              }
                //otherwise, start a new sale, and new receipt
                else {
                    //Sales ID increases with each new receipt
                    sID++;
                    boolean add = converted.add(new salesReceipt(Integer.parseInt
                        (originalCSVData[0]), sID));
                    converted.get(sID).addItem(
                                    Integer.parseInt(originalCSVData[1]), 
                                    Integer.parseInt(originalCSVData[2]), 
                                    currentInventory);
                }
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
        //System.out.println("...Loaded.");
        //System.out.println("--------------------------------");
        return converted;
 }
    
    //This method will review the sales, and calculate what was sold total from
    //the receipts
    public static void salesInventory(LinkedList<salesReceipt> totalSales){
        int receiptCount = 1;
        int itemCount = 0;
        int salesItemCount = 0;
        
        
        //System.out.print("Test Case 4, Part 1: Populating Sales Totals");
        
        //Scan Receipts until all receipts are scanned
        while (receiptCount < (totalSales.size() - 1)){
            
            //Scan until current line is added to sales
            //while(true){
            
                //If the Current receipt line Item being added matches the 
                //the total sales line item, add line units together in total sales
                if(totalSales.get(receiptCount).items.get(itemCount).getProductID()
                    == totalSales.get(0).items.get(salesItemCount).getProductID()){
                    
                    //Add current receipt line item to totalsales line item
                    totalSales.get(0).items.get(salesItemCount).setUnits(
                        (totalSales.get(receiptCount).items.get(itemCount).getUnits()
                            + totalSales.get(0).items.get(itemCount).getUnits()));
                    
                    //Move to Next Item on receipt, until all items scanned
                    if(totalSales.get(receiptCount).items.size() < itemCount){
                        itemCount++;
                        salesItemCount = 0;
                    }
                    //If all items have been scanned, move to next receipt
                    else{
                        itemCount = 0;
                        salesItemCount = 0;
                        receiptCount++;
                    }                        
                }
                //Move to next sales inventory item to check for storage
                else
                    salesItemCount++;
            //}
        }        
        //System.out.println("...Loaded.");
        //System.out.println("--------------------------------");                
    }
}