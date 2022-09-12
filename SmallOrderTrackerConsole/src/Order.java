/**
 * @author Sydney Cochran
 */
public abstract class Order {
	
	protected String productName="";
	protected int quantity=0;
	protected double unitPrice=0.0;
	protected double totalCost=0.0;
	
/**Constructs an order.
 * @param productName
 * @param quantity
 * @param unitPrice
 */	
	public Order(String productName, int quantity, double unitPrice) {
		this.productName=productName;
		this.quantity=quantity;
		this.unitPrice=unitPrice;
	}	
/**Retrieves the product name
 * @return A string representing the product name
 */	
	public String getProductName() {
		return productName;
	}
	
/**Creates method for children classes to use
 * 
 */
	public abstract void computeTotalCost();
	
/**Method for printing order details.
 * @return A string representing the order
 */
	
	public String toString() {
		return "***Order\n"+ "Product Name: " + productName 
		+ "\nUnit Price: " + unitPrice + "\nTotal Cost: " + totalCost;
	}

}
