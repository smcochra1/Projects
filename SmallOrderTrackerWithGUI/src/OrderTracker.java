
/**
 * Assignment03
 * @author Sydney Cochran
 * StudentID 1218301786
 * CSE 205
 * Lecture time Tues/Thurs 1:30 p.m
 * This class contians the main method. It is where the window is constructed. 
 */
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class OrderTracker extends JFrame {

	OrderInputPanel orderInput;

/**
 * Main method
 * @param a
 */
	public static void main(String[]a){
		OrderTracker window = new OrderTracker();
		window.setTitle("Order Handling");
		window.setSize(580, 400);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
/**
 * Constructor for Assignment Class
 */
	public OrderTracker(){
		JTabbedPane tabs = new JTabbedPane();
		OrderInputPanel input = new OrderInputPanel();
		OrderHandlePanel handle = new OrderHandlePanel();
		tabs.add("Order Input", input);
		tabs.add("Order Handle", handle);
		add(tabs);
	}
	
	
	}
