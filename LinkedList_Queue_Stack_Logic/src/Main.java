import java.util.Scanner;
public class Main<T> {
	
	MyList<String> linkedList = new MyList<String>();
	MyStack<String> stack = new MyStack<String>();
	MyQueue<String> queue = new MyQueue<String>();
	Scanner scan = new Scanner(System.in);
	Scanner scanString = new Scanner(System.in);
	
	Main(){
		linkedList.insertFirst("3");
		linkedList.insertFirst("2");
		linkedList.insertFirst("1");
		linkedList.insertLast("5");
		linkedList.insertAt(3,"4");
		linkedList.printAll();
		System.out.println("");
	
		stack.push("C");
		stack.push("B");
		stack.push("A");
		stack.push("D");
		stack.pop();
		stack.printAll();
		System.out.println("");
		
		queue.enqueue("quatro");
		queue.enqueue("tres");
		queue.enqueue("dos");
		queue.enqueue("uno");
		queue.dequeue();
		queue.printAll();
	}
	public static void main(String[] args) {
		Main main = new Main();
		
	}
}
