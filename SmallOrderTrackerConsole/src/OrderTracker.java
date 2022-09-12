/**
 * Assignment01
 * @author Sydney Cochran
 * StudentID 
 * Lecture time Tues/Thurs 1:30 p.m
 */
import java.util.Scanner;
public class OrderTracker {
	public static void main(String[] args) {
		
		Order[] arr = new Order[100];
		String choice;
		Scanner stringScan = new Scanner(System.in);
		Scanner scan = new Scanner(System.in);
		
		
		for(int i=0; i<100; i++ ) {
		
		System.out.println("\n*** Choice Action \n" +
				"A) Add an Order\n" +
				"B) Compute Total Costs\n" +
				"C) Search for an Order\n" +
				"D) List Orders\n" +
				"E) Quit");
		
			System.out.println("What action would you like to perfom?");
			choice = scan.next().toUpperCase();
			
			if(!choice.equals("E")) {

				switch(choice) {
			
				case "A":
					System.out.println("Please enter the type of order (Cancelled, Shipped, New):");
					String orderType = scan.next();
					orderType.toLowerCase();
				
					System.out.println("\nPlease enter an order information to add:");
				
						if(orderType.equals("cancelled")) {
							System.out.println("Enter product name: ");
							String productName;
							productName = stringScan.nextLine();
							System.out.println("Enter product quantity: ");
							int quantity = scan.nextInt();
							System.out.println("Enter unit price: ");
							double unitPrice = scan.nextDouble();
							System.out.println("Enter date cancelled: ");
							String cancelledDate = stringScan.nextLine();
							System.out.println("Enter reason cancelled: ");
							String cancelledReason = stringScan.nextLine();							
							arr[i] = new CancelledOrder(productName, quantity, unitPrice, cancelledDate, cancelledReason);
						}
						else if(orderType.equals("shipped")) {
							System.out.println("Enter product name: ");
							String productName = stringScan.nextLine();
							System.out.println("Enter product quantity: ");
							int quantity = scan.nextInt();
							System.out.println("Enter unit price: ");
							double unitPrice = scan.nextDouble();
							System.out.println("Enter shipping adress: ");
							String shippingAddress = stringScan.nextLine();
							System.out.print("Shipping options: \n" + "Economy - 6% fee \n" + "Regular - 8% fee \n" + "Express - 12% fee \n" + "Select a shipping option: \n");
							String wayOfShipping = stringScan.nextLine();
							wayOfShipping.toLowerCase();
							arr[i] = new ShippedOrder(productName, quantity, unitPrice, shippingAddress, wayOfShipping); 
						}
						else if(orderType.equals("new")) {
							System.out.println("Enter product name: ");
							String productName = stringScan.nextLine();
							System.out.println("Enter product quantity: ");
							int quantity = scan.nextInt();
							System.out.println("Enter unit price: ");
							double unitPrice = scan.nextDouble();
							System.out.println("Enter name for sales: ");
							String salesName = stringScan.nextLine(); 
							System.out.println("Is order refundable? Y/N");
							String refund = stringScan.nextLine().toLowerCase(); 
							boolean refundableStatus=true; 
							if(refund.equals("y")) {refundableStatus=true;}
							else if(refund.equals("n")){refundableStatus=false;}
							
							NewOrder order = new NewOrder(productName, quantity, unitPrice, salesName, refundableStatus);
							arr[i] = order;
						}
						break;
				
			
				case "B":
					for (int j=0; j<arr.length; j++) {
						if(arr[j] != null) {
							arr[j].computeTotalCost();
							System.out.println("Total costs Computed");
						}
						else if(arr[0] == null) {
							System.out.println("No orders to compute!");
							break;
						}
					}
					break;
				
				case "C":
					System.out.println("Please enter a product name to search:");
					String search = stringScan.nextLine();
					for (int k=0; k<arr.length; k++) {
						if(arr[k] !=null) {
							if(arr[k].productName.equals(search)) {
								System.out.println("Product Found!");
							}
							else{System.out.println("Product not found");}	
						}
						else if(arr[0] == null) {System.out.println("No orders to search!");}
						break;
					}
				
					break;
				
				case "D":
					for (int l=0; l<arr.length; l++) {
						if(arr[l] != null) {
							System.out.print(arr[l].toString());
						}
						else if(arr[0]==null) {
							System.out.println("No orders");
							break;
						}
					}
					break;
				
				case "E":
					System.exit(0);
					break;
				default:
                    System.out.println("\n Unknown action \n"); 
                    break;
				}
                
			}
		}
			
	}

}
