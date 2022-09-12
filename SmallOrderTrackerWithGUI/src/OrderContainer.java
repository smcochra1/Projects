/**
 * Assignment03
 * @author Sydney Cochran
 * StudentID 1218301786
 * CSE 205
 * Lecture time Tues/Thurs 1:30 p.m
 */
public class OrderContainer {
	
	protected static Order[] orders = new Order[100];
	protected static Order[] cancelledOrders = new Order[100];
	protected static int pos=0;
	protected static int j;
	
/**
 * Method to add a cancelled order to the array, and update the order handle panel. 
 * @param o
 */
	public static void addCancelledOrder(Order o) {
		if(j<99) {
			cancelledOrders[j]=o;
			j++;
			OrderHandlePanel.update();
		}
	}
	
/**
 * Method to remove an order from the new order list, and update the order handle panel. 
 * @param i
 */
	public static void removeOrder(int i) {
		if(i < 99 && orders[i] != null) {
			for(int k = i + 1; k < pos; i++) {
				orders[k-1] = orders[k];
			}
			pos--;
		}
		OrderHandlePanel.update();
	}
	
/**
 * Method to remove a cancelled order from the cancelled order list, and update the order handle panel. 
 * @param i
 */
	public static void removeCancelledOrder(int i) {
		if(i < 99 && cancelledOrders[i] != null) {
			for(int k = i + 1; k < j; k++) {
				cancelledOrders[k-1] = cancelledOrders[k];
			}
			j--;
		}
		OrderHandlePanel.update();
	}
	
/**
 * Method to add a new order to the array, and update the order handle panel. 
 * @param o
 */
	public static void addOrder(Order o) {
		if(pos<=99) {
			orders[pos] = o;
			pos++;
			OrderHandlePanel.update();
		}
	}

/**
 * Method to get an order. 
 * @param i
 * @return
 */
	public static Order getOrder(int i) {
		if(pos>0&& pos<=99)
			return orders[i];
			return null;
	}

/**
 * Method to get the order's position in the array. 
 * @return
 */
	public static int getPosition() {
		return pos;
	}
	
	

}
