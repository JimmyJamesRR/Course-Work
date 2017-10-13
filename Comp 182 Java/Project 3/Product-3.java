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

import java.text.DecimalFormat;

public class Product {

    private int productID;
    private String description;
    private double price;
    private int unitsInInventory;
    
    DecimalFormat df = new DecimalFormat("0.00");

    public Product(int id, String d, double p) {
        description = d;
        productID = id;
        price = p;
    }

    public void setDescription(String d) {
        description = d;
    }

    public String getDescription() {
        return description;
    }

    public void setProductID(int id) {
        productID = id;
    }

    public int getProductID() {
        return productID;
    }

    public void setPrice(double p) {
        price = p;
    }

    public double getPrice() {
        return price;
    }

    public void setUnitsInInventory(int u) {
        unitsInInventory = u;
    }

    public int getUnitsInInventory() {
        return unitsInInventory;
    }
    public void printDetails() {
        System.out.println("Product ID: " + productID);
        System.out.println("Description: " + description);
        System.out.println("Price: $" + df.format(price));
    }
}
