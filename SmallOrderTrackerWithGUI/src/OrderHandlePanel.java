/**
 * Assignment03
 * @author Sydney Cochran
 * StudentID 1218301786
 * CSE 205
 * Lecture time Tues/Thurs 1:30 p.m
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class OrderHandlePanel extends JPanel implements ActionListener{
	
	static DefaultListModel newOrder = new DefaultListModel();
	static DefaultListModel cancelledOrder = new DefaultListModel();
	JList newOrderList, cancelledOrderList;
	JButton buttonIn, buttonOut;
	static JTextField textOrderedAmount, textCancelledAmount;
	public static final DecimalFormat df = new DecimalFormat( "#.##" );
	/**
	 * Initializations
	 */
	
/**
 * Constructor for OrderHandlePanel.
 */
	OrderHandlePanel(){
		
		setLayout (new GridLayout(1, 3));
		
		JPanel newOrderPanel = new JPanel(new BorderLayout());
		JLabel newOrderLabel = new JLabel("New Order");
		newOrderPanel.add(newOrderLabel, BorderLayout.NORTH);
		newOrderList = new JList(newOrder);
		newOrderPanel.add(newOrderList, BorderLayout.CENTER);
		textOrderedAmount = new JTextField();
		newOrderPanel.add(textOrderedAmount, BorderLayout.SOUTH);
		add(newOrderPanel);
		/**
		 * Adds new order label, list, and total amount to panel. 
		 */
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 1));
		buttonIn = new JButton("=>");
		buttonIn.addActionListener(this);
		buttonPanel.add(buttonIn);
		buttonOut = new JButton("<=");
		buttonOut.addActionListener(this);
		buttonPanel.add(buttonOut);
		JPanel buttonPanelLayout = new JPanel(new BorderLayout());
		buttonPanelLayout.add(buttonPanel, BorderLayout.NORTH);
		add(buttonPanelLayout);
		/**
		 * Adds two buttons to switch new and cancelled orders between lists. 
		 */
		
		JPanel cancelledOrderPanel = new JPanel(new BorderLayout());
		JLabel cancelledOrderLabel = new JLabel("Cancelled Order");
		cancelledOrderPanel.add(cancelledOrderLabel, BorderLayout.NORTH);
		cancelledOrderList = new JList(cancelledOrder);
		cancelledOrderPanel.add(cancelledOrderList, BorderLayout.CENTER);
		textCancelledAmount = new JTextField();
		cancelledOrderPanel.add(textCancelledAmount, BorderLayout.SOUTH);
		add(cancelledOrderPanel);
		/**
		 * Adds cancelled order label, list, and total amount to panel. 
		 */
	}

	
/**
 * Method to get the total for new orders. 
 * @return
 */
	public static double getTotal() {
		double totalOrder = 0.0;
		for(int i = 0; i < OrderContainer.getPosition(); i++) {
			totalOrder += OrderContainer.orders[i].getTotalCost();
		}
		return totalOrder;
	}
	
/**
 * Method to get the total for cancelled orders. 
 * @return
 */
	public static double getCancelledTotal() {
		double totalCancelled = 0.0;
		for(int i = 0; i < OrderContainer.j; i++) {
			totalCancelled += OrderContainer.cancelledOrders[i].getTotalCost();
		}
		return totalCancelled;
	}
	
/**
 * Action method creates what happens when information is inputted. 
 */
	public void actionPerformed(ActionEvent e) {
	
		if(e.getSource() == buttonIn) {
			int [] fromIndex = newOrderList.getSelectedIndices();
			Object[] from = newOrderList.getSelectedValues();
			
			for(int i = 0; i < from.length; i++) {
				cancelledOrder.addElement(from[i]);
				OrderContainer.addCancelledOrder(OrderContainer.orders[fromIndex[i]]);
			}
			
			for(int j = (fromIndex.length-1); j>=0; j--) {
				newOrder.remove(fromIndex[j]);
				OrderContainer.removeOrder(fromIndex[j]);
			}
		}
		/**
		 * If "=>" is pressed, add element to cancelled list and remove from new order list. 
		 */
		else if(e.getSource() == buttonOut) {
			Object[] to = cancelledOrderList.getSelectedValues();
			int[] toIndex = cancelledOrderList.getSelectedIndices();
			
			for(int i = 0; i < to.length; i++) {
				newOrder.addElement(to[i]);
				OrderContainer.addOrder(OrderContainer.cancelledOrders[toIndex[i]]);
			}
			
			for(int i = (toIndex.length-1); i >=0; i--) {
				cancelledOrder.remove(toIndex[i]);
				OrderContainer.removeCancelledOrder(toIndex[i]);
			}
		}
		/**
		 * If "<=" is pressed, add element to new order list and remove from cancelled list. 
		 */
	}
	
/**
 * Method to add the orders from the OrderInputPanel into the new order list, and update the total amounts. 
 */
	public static void update() {
		newOrder.clear();
		double tc=0.0;
		for(int i = 0; i < OrderContainer.getPosition(); i++) {
			if(OrderContainer.orders[i] !=null) {
				newOrder.addElement(OrderContainer.orders[i].toString());
			}
			
		}
		textOrderedAmount.setText("Ordered amt: " + df.format(getTotal()));
		textCancelledAmount.setText("Cancelled amt: " + df.format(getCancelledTotal()));
	}
	}
