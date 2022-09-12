/**
 * @author Sydney Cochran
 */
public class CancelledOrder extends Order{
	
	protected String cancelledDate = "";
	protected String cancelledReason = "";
/**Constructs a cancelled order
 * @param productName
 * @param quantity
 * @param unitPrice
 * @param cancelledDate
 * @param cancelledReason
 */
	public CancelledOrder(String productName, int quantity, double unitPrice, String cancelledDate, String cancelledReason) {
		super(productName, quantity, unitPrice);
		this.cancelledDate = cancelledDate;
		this.cancelledReason = cancelledReason;
	}
/**Computes the total cost of a cancelled order.
 * 	
 */
	public void computeTotalCost() {
		double x = unitPrice * quantity;
		totalCost = x * 1.02;
	}
/**Method for printing order details.
 * @return a string representing the order. 
 */
	public String toString() {
		return "\n*** Cancelled Order\n" +
				"Product Name: " + productName + "\n" +
				"Unit Price: " + unitPrice + "\n" +
				"Total Cost: " + totalCost + "\n" +
				"Cancel Date: " + cancelledDate + "\n" +
				"Cancel Reason: " + cancelledReason + "\n";
	}

}
