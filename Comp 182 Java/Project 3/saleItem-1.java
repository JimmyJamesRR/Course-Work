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

import java.util.LinkedList;
import java.text.DecimalFormat;
public class saleItem {

    private int productID;
    private int units;
    private String productDescription;
    private double unitPrice;
    private double lineTotal = units * unitPrice;
    DecimalFormat df = new DecimalFormat("0.00");
    
    public saleItem(int id, int u, LinkedList<Product> currentInventory) {                            
    productID = id;
    units = u;
    int priceCheck = 0;

        while (unitPrice == 0){
            if (currentInventory.get(priceCheck).getProductID() == id){
                    unitPrice = currentInventory.get(priceCheck).getPrice();
                    productDescription = currentInventory.get(priceCheck)
                            .getDescription();    }
            else if(currentInventory.get(priceCheck).getProductID() != 0)
                priceCheck++;
            else{
                System.out.println("Product ID: " + id + " not found.");
                break;
            }
        }
    lineTotal = units * unitPrice;
}
    
    public void setProductID(int id) {
        productID = id;
    }
    public int getProductID() {
        return productID;
    }
    public void setUnits(int u) {
        units = u;
        lineTotal = units * unitPrice;
    }
    public int getUnits() {
        return units;
    }
    public void setUnitPrice(double p) {
        unitPrice = p;
        lineTotal = units * unitPrice;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    
    public double getLineTotal() {
        return lineTotal;
    }
    public void printSaleItem() {
        System.out.println(units + "     " + productID + "  " + productDescription + 
                "   @   $" + df.format(unitPrice) + "   $" + df.format(lineTotal));
    }
    }
