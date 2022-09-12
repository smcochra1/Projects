
public class MyStack<T> {
	T data;
	private MyNode head;
	
	MyStack(){
		
	}
	
	public void push(T data) {
		MyNode node = new MyNode(data);
		if(head==null) {
		head = node;
		}else {
		MyNode x = head;
		node.setLink(x);
		head = node;
		}
	}
	
	public MyNode pop() {
		MyNode node = head;
		MyNode returnNode = head.getLink();
		head = node.getLink();
		return returnNode;
	}
	
	public void printAll() {
		System.out.print("STACK: ");
		MyNode currNode = head;
		while(currNode != null) {
			System.out.print(currNode.getData() + " ");
			currNode = currNode.getLink();
		}
		System.out.println("");
	}
}
