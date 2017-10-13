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

import java.util.Date;
import java.util.LinkedList;

import java.text.DecimalFormat;

public class salesReceipt {
     int saleID;
     int customerID;
     Date saleDateTime;
     LinkedList<saleItem> items = new LinkedList<>();
     double total = 0;
     
    DecimalFormat df = new DecimalFormat("0.00");
     
    //Constructor
    public salesReceipt(int cID, int sID){
     saleID = sID;
     customerID = cID;
     saleDateTime = new Date();  
    }


    //Sales Receipt Get/sets
    public void setSaleID(int sID){
        saleID = sID;
    }
    public int getSaleID(){
        return saleID;
    }
    public void setCustomerID(int cID){
        customerID = cID;
    }
    public int getCustomerID(){
        return customerID;
    }
    public void setSaleDateTime(){
        saleDateTime = new Date();
    }
    public Date getDate(){
        return saleDateTime;
    }
    public void addItem(int id, int u, LinkedList<Product> currentInventory){
        items.add(new saleItem( id, u, currentInventory));
    }
    
    public void calculate(){        
        int count = 0;
        while (items.size() > count){
            total = total + (items.get(count).getLineTotal());
            count++;
        }
    }
    
    public void printSalesReceipt(){
        int count = 0;
        System.out.println("Customer ID: " + customerID);
        if(saleID == 0)
            System.out.println("Sales ID: Total Sales Receipt");
        else
            System.out.println("Sales ID: " + saleID);
        System.out.println("Sales Date: " + saleDateTime);
        System.out.println("#   Product ID  Product Description  Price    SubTotal");
        while (items.size() > count){
            items.get(count).printSaleItem();        
            count++;
        }
        System.out.println("                                     Total:  $" 
                + df.format(total));
                        
    }
    
}    
