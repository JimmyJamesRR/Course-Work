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
import java.text.DecimalFormat;

public class Product {

    private int productID;
    private String description;
    private double price;
    
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

    public void printDetails() {
        System.out.println("Product ID: " + productID);
        System.out.println("Description: " + description);
        System.out.println("Price: $" + df.format(price));
    }
}
