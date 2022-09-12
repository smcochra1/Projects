/**
 * @author Sydney Cochran
 */
public class ShippedOrder extends Order{
	
	protected String shippingAddress;
	protected String wayOfShipping;
	protected double shippingFee;
/**Constructs a shipped order.
 * @param productName
 * @param quantity
 * @param unitPrice
 * @param shippingAddress
 * @param wayOfShipping
 */
	public ShippedOrder(String productName, int quantity, double unitPrice, String shippingAddress, String wayOfShipping) {
		super(productName, quantity, unitPrice);
		this.shippingAddress = shippingAddress;
		this.wayOfShipping = wayOfShipping;
	}
/**Sets the cost of shipping.
 */
	public void setShippingFee() {
		double economyFee = 0.06;
		double regularFee = 0.08;
		double expressFee = 0.12;
		
		if(wayOfShipping.equals("economy")) {
			shippingFee = economyFee * (unitPrice * quantity);
		}
		else if(wayOfShipping.equals("regular")) {
			shippingFee = regularFee * (unitPrice * quantity);
		}
		else if(wayOfShipping.equals("express")) {
			shippingFee = expressFee * (unitPrice * quantity);
		}
	}
/**Computes the total cost of a shipped order. 
 */
	public void computeTotalCost() {
		double x = unitPrice * quantity;
		setShippingFee();
		totalCost = x +shippingFee;
	}
/**
 * Method for printing order details.
 * @return A string representing the order
 */
	public String toString() {
		return "\n*** Shipped Order\n" +
				"Product Name: " + productName + "\n" +
				"Unit Price: " + unitPrice + "\n" +
				"Total Cost: " + totalCost + "\n" +
				"Shipping Way: " + wayOfShipping + "\n" +
				"Shipping Fee: " + shippingFee + "\n";		
	}
		

}
