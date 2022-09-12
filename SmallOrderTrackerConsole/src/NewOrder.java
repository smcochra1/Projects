/**
 * @author Sydney Cochran
 */
public class NewOrder extends Order {
	
	protected String salesName;
	protected boolean refundableStatus=true;
/**Constructs a new order.
 * @param productName
 * @param quantity
 * @param unitPrice
 * @param salesName
 * @param refundableStatus
 */
	public NewOrder (String productName, int quantity, double unitPrice, String salesName, boolean refundableStatus) {
		super(productName, quantity, unitPrice);
		this.salesName = salesName;
		this.refundableStatus = refundableStatus;
	}
/**Computes the total cost of a new order. 
 * 
 */
	public void computeTotalCost() {
		double x = (quantity * unitPrice) * 1.086;
		if (refundableStatus == true) {
			totalCost = x * 1.02;
		}
		else {
			totalCost = x;
		}
	}
/**Method for printing order details.
 * @return a String representing the order. 
 */
	public String toString() {
		return "\n*** New Order\n" +
				"Product Name: " + productName + "\n" +
				"Unit Price: " + unitPrice + "\n" +
				"Total Cost: " + totalCost + "\n" +
				"Sales Name " + salesName + "\n" +
				"Refund Status: " + refundableStatus + "\n";		
	}

}
