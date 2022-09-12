/**
 * Assignment03
 * @author Sydney Cochran
 * StudentID 1218301786
 * CSE 205
 * Lecture time Tues/Thurs 1:30 p.m
 */
import java.text.DecimalFormat;
public class Order {

	protected String name;
	protected int quantity; 
	protected double price;
	protected double totalCost;
	public static final DecimalFormat df = new DecimalFormat( "#.##" );
		
/**
 * Constructor for Order	
 * @param n
 * @param q
 * @param p
 * @param t
 */
	public Order(String n, int q, double p, double t) {
		name = n;
		quantity = q;
		price = p;
		totalCost = t;
	}
	
/**
 * Method that returns the product name. 	
 * @return
 */
	public String getProductName() {	
		return name;
	}
		
/**
 * Method that returns the quantity. 
 * @return
 */
	public int getQuantity() {
		return quantity;
	}

/**
 * Method that returns the price. 
 * @return
 */
	public double getPrice() {
		return price;
	}
	
/**
 * Method that returns the total cost. 
 * @return
 */
	public double getTotalCost() {
		return totalCost;
	}
	
/**
 * Method that prints the order. Total cost is formatted to 2 decimal places. 
 */
	public String toString() {
		return "Name: " + name + "\nQuantity: " + quantity + "\nPrice: " + price + "\nTotal Cost: " + df.format(totalCost) + "\n";
	}
		
		
}
