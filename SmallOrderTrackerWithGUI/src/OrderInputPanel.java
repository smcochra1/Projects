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

public class OrderInputPanel extends JPanel implements ActionListener {
	JButton button;
	JTextField textName, textQuantity, textPrice;
	JLabel warning;
	JTextArea orderText;
	
/**
 * Constructor method for OrderInputPanel Class
 */
	OrderInputPanel(){
		setLayout (new GridLayout(1,2));
		
		JPanel input = new JPanel();
		input.setLayout(new GridLayout(5,1));
		
		warning = new JLabel("");
		warning.setForeground(Color.RED);
		input.add(warning);
		/**
		 * Creates top label that will indicate if input is valid. 
		 */
		
		JLabel productName = new JLabel("Prod. Name");
		textName = new JTextField();
		JPanel pName = new JPanel();
		pName.setLayout(new GridLayout(1, 2));;
		pName.add(productName);
		pName.add(textName);
		input.add(pName);
		/**
		 * Creates product name textfield. 
		 */
		
		JLabel q = new JLabel("Quantity");
		textQuantity = new JTextField();
		JPanel quantity = new JPanel();
		quantity.setLayout(new GridLayout(1, 2));
		quantity.add(q);
		quantity.add(textQuantity);
		input.add(quantity);
		/**
		 * Creates quantity textfield. 
		 */
		
		JLabel p = new JLabel("Price($)");
		textPrice = new JTextField();
		JPanel price = new JPanel();
		price.setLayout(new GridLayout(1, 2));
		price.add(p);
		price.add(textPrice);
		input.add(price);
		/**
		 * Creates price textfield
		 */
		
		button = new JButton("Place an Order");
		button.addActionListener(this);
		input.add(button);
		/**
		 * Creates button to add the order. 
		 */
		
		JPanel inputLayout = new JPanel(new BorderLayout());
		inputLayout.add(input, BorderLayout.NORTH);
		add(inputLayout);
		/**
		 * Formatting.
		 */
		
		orderText = new JTextArea("No Order");
		add(orderText);
		/**
		 * Creates textArea to print added orders. 
		 */
	}

/**
 * Action method creates what happens when information is inputted. 
 */
	public void actionPerformed(ActionEvent e) {
	
		String quantityString="";
		String priceString="";
		
		for(int j=0; j<OrderContainer.orders.length; j++) {
			if(OrderContainer.orders[j] != null)
					if((OrderContainer.orders[j].name).equals(textName.getText()) && OrderContainer.orders[j].quantity == Integer.parseInt(textQuantity.getText()) && 
					OrderContainer.orders[j].price == Double.parseDouble(textPrice.getText())) {
						boolean duplicate = true;
						warning.setText("duplicate order");
					}
			}
		/**
		 * For method that checks for duplicate order. 
		 */
		
		try {
		     Integer.parseInt(quantityString);
		}
		catch (NumberFormatException f) {
		     warning.setText("Incorrect data format");
		}
		try {
		     Double.parseDouble(priceString);
		}
		catch (NumberFormatException f) {
		     warning.setText("Incorrect data format");
		}
		/**
		 * Checks to see if quantity and price with convert to int and double without error. 
		 */
		
		if(textName.getText().isEmpty() || textQuantity.getText().isEmpty() || textPrice.getText().isEmpty()) {
			warning.setText("Please fill all fields");
		/**
		 * Checks if all textfields are filled. 
		 */
		}else {
			String name = textName.getText();
			quantityString = textQuantity.getText();
			int quantity = Integer.parseInt(quantityString);
			priceString = textPrice.getText();
			double price = Double.parseDouble(priceString);
			double totalCost = price * quantity;
			/**
			 * Storing the text input into variables. 
			 */
			Order orders = new Order(name, quantity, price, totalCost);
			OrderContainer.addOrder(orders);
			/**
			 * Creates new order and adds to array. 
			 */
			
			textName.setText("");
			textQuantity.setText("");
			textPrice.setText("");
			/**
			 * Clears textfields for next input.
			 */
			
			String orderTextArea = "";
			for(int i = 0; i< OrderContainer.orders.length; i++){
				if(OrderContainer.orders[i] != null) {
					orderTextArea = orderTextArea + (OrderContainer.orders[i].toString() + "\n");
				}
			}
			/**
			 * Prints the orders in the array and stores it in orderTextArea. 
			 */
			orderText.setText(orderTextArea);
			warning.setText("Order added");
			

		}
	}
}
