package project3;


/*Author:  James Renger
Course:     Comp 182(16203-SP17)
Project:    Project 3
Program function:  The purpose of this progam is to input a csv file of product
data, a csv of inventory data, and sales data.  Calculate the total sales of
each item, and output the total sales, and change in inventory.
External source code used/augmented for this project:  Project 2.
File List for project: Project3.java, salesReceipt.java, saleItem.java, 
Product.java, Sales Data.csv, Product Data.csv, Inventory Data
*/

//Libraries
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.text.DecimalFormat;


public class Project3 {

   // Main
    public static void main(String[] args) {
        // Variable Filenames
        String productData = "Product Data.csv";
        String inventoryCSV = "Inventory Data.csv";
        String salesCSV = "Sales Data.csv";
        DecimalFormat df = new DecimalFormat("0.00");
        
       //populate a linked list of products with the inventory method processing 
        //the inventoryCSV and productData.  These are fed into the method by the 
        //file name reference variables.
        LinkedList<Product> currentInventory = new LinkedList<>
            (inventory(inventoryCSV, productData));
        
        System.out.println("Inventory");

        
        //populate a linked list of salesReceipt with receipts method
        LinkedList<salesReceipt> sales = new LinkedList<salesReceipt>
            (receipts(salesCSV, currentInventory));    
        
        int salesSize = sales.size();
        
        System.out.println("productID    Price    Starting Units   Sold Units    Remaining Units   Product Sales");
        for(int i=0; i<currentInventory.size(); i++){
              
              System.out.print(currentInventory.get(i).getProductID() + "        ");
              System.out.print("$" + df.format(currentInventory.get(i).getPrice()) + "         ");
              System.out.print(sales.get(0).getItem(i).getUnits() + "             ");
              
              System.out.print(sales.get(salesSize - 1).getItem(i).getUnits() + "           ");
              System.out.print(sales.get(salesSize - 2).getItem(i).getUnits() + "            ");
              System.out.println("$" + df.format(sales.get(salesSize - 1).getItem(i).getLineTotal()) + "              ");
                      
        }
        
        sales.get(salesSize - 1).calculate();
        System.out.println();
        System.out.println( "Total Sales: " + 
                "                                                       " 
                + "$" + sales.get(salesSize - 1).total);
        
        
    
    }
    // End of Main   
    
    //This public method will take the parameter of an inventory filename and product data
    //and convert both into a linked list of Products.
    public static LinkedList<Product> inventory(String inventoryFileName, 
            String productFileName){
        
        //Return Variable
        LinkedList<Product> converted = new LinkedList<>();
        // Conversion Variables
        String csvFile = productFileName;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] originalCSVData;

        /*
        Read the product data with a buffered reader to store the variable 
        information it contains, into a linked list of product objects.  The 
        reader reads one line, stores the product ID int, the Item description 
        string, and the price double, and repeats this until there are no further lines.
        */
        try {
            br = new BufferedReader(new FileReader(csvFile));         

            //Clears out the labels
            String[] labelsString = br.readLine().split(cvsSplitBy);
                              
            
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
        
        csvFile = inventoryFileName;
        
        /*
	Read the inventory data into a buffered reader.  Use the inventory data 
        from the reader’s product ID data to match the input to the product in 
        the linked list product list with the corresponding product ID, and then 
        update the unitsInInventory int with the readers units data.
        */
        try {
            br = new BufferedReader(new FileReader(csvFile));         

            //Clears out the labels
            String[] labelsString = br.readLine().split(cvsSplitBy);
                                           
            //while loop that will read the data, output for test case, and 
            //store the data into the temp string storage array
            while ((line = br.readLine()) != null) {
               
                //Convert the file line by line into a string, seperating with ,
                originalCSVData = line.split(cvsSplitBy);
                 int count = 0;
                while(true){
                   
                    if (converted.get(count).getProductID() == Integer
                            .parseInt(originalCSVData[0])){
                            converted.get(count).setUnitsInInventory(Integer
                            .parseInt((originalCSVData[1].trim().replaceAll("-", "0"))));
                            count = 0;
                            break;
                    }  
                    else
                        count++;
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
        return converted;
    }    

    /*populate a linked list of salesReceipt with the receipts method, by giving 
    the method the file reference variable, and the current product inventory 
    linked list.  Use a buffered reader to process the data to the salesReceipts 
    linked list, and matching data from the product inventory, to fill the fields 
    for the salesReceipts(sale ID, Customer ID, Date, units, price, product 
    description, line total, and total).*/
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
        //Linked list receipts location 0 will be used to hold the start inventory
        converted.add(new salesReceipt(0,0));
        
        //populate start inventory in receipt 1
        while (converted.get(0).items.size() < currentInventory.size()){
            converted.get(0).addItem(currentInventory.get(count)
                    .getProductID(), currentInventory.get(count)
                            .getUnitsInInventory(), currentInventory);  
            count++;
        }

	//Process CSV into originalCSVData array, with an infinite loop which
	//breaks when there is no data left to input.
                try {
            br = new BufferedReader(new FileReader(csvFile));         

                //Output the labels from the Original CSV File for proof of read
                //System.out.println("Test Case 2 & 3, Part 1: Proof of CSV read.");
                String[] labelsString = br.readLine().split(cvsSplitBy);                            
                
                //Start first receipt
                line = br.readLine();
                originalCSVData = line.split(cvsSplitBy);
                converted.add(new salesReceipt(Integer.parseInt
                        (originalCSVData[0]), sID));
                    converted.get(sID).addItem(
                                    Integer.parseInt(originalCSVData[1]), 
                                    Integer.parseInt(originalCSVData[2]), 
                                    currentInventory);
                //reduce inventory by number of units sold      
                    count = 0;
                    while(true)
                    {
                        if(currentInventory.get(count).getProductID() == 
                            Integer.parseInt(originalCSVData[1])){                                                               
                            currentInventory.get(count).setUnitsInInventory
                                (currentInventory.get(count).getUnitsInInventory() -
                                  Integer.parseInt(originalCSVData[2]));                                                      
                            break;
                        }
                        else
                            count++;                            
                }    

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
                     //reduce inventory by number of units sold      
                    count = 0;
                    while(true)
                    {
                        if(currentInventory.get(count).getProductID() == 
                            Integer.parseInt(originalCSVData[1])){                                                               
                            currentInventory.get(count).setUnitsInInventory
                                (currentInventory.get(count).getUnitsInInventory() -
                                  Integer.parseInt(originalCSVData[2]));                                                      
                            break;
                        }
                        else
                            count++;                            
                }    
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
                     //reduce inventory by number of units sold      
                    count = 0;
                    while(true)
                    {
                        if(currentInventory.get(count).getProductID() == 
                            Integer.parseInt(originalCSVData[1])){                                                               
                            currentInventory.get(count).setUnitsInInventory
                                (currentInventory.get(count).getUnitsInInventory() -
                                  Integer.parseInt(originalCSVData[2]));                                                      
                            break;
                        }
                        else
                            count++;                            
                }    
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
        sID++;
        boolean add = converted.add(new salesReceipt(0, sID));
        sID++;
        converted.add(new salesReceipt(0, sID));
        count = 0;
                    
        while(count < currentInventory.size())
        {
            //Set tail - 1 to end inventory
            converted.get(sID - 1).addItem(currentInventory.get(count).getProductID(),
                    currentInventory.get(count).getUnitsInInventory(), currentInventory);
            //Set tail to total sales
           converted.get(sID).addItem(currentInventory.get(count).getProductID(), 
                   (converted.get(0).items.get(count).getUnits() - 
                    converted.get(sID - 1).items.get(count).getUnits()), currentInventory);
           count++;
        }
            return converted;
    }

    //Create a linked list of receipts that sorts the total sales by customer by 
    //total sales in descending order, by using the customer sales method, which 
    //will be provided with the sales linked list of receipts.
    public static LinkedList<salesReceipt> customerSales(LinkedList<salesReceipt> salesReceipts)
    {
        LinkedList<salesReceipt> sortedSales = new LinkedList<>();

        
        
        return sortedSales;
    }
    
//Add each receipt to the list, while scanning to see if the customer ID already exists.
//If the customer ID exists, add the items in the receipt to the customers existing items, and recalculate total.  
//Else, add the customer ID as a new receipt.
// Adjust the position of the receipt in the linked list by total sale value.
//Print the sorted linked list



}
    
    
 //   public static void salesInventory(LinkedList<salesReceipt> totalSales){
      