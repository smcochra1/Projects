
public class MyQueue<T> {
	T data;
	private MyNode head;
	private MyNode tail;
	
	MyQueue(){
		head = null;
		tail = null;
	}
	
	public void enqueue(T data) {
		MyNode node = new MyNode(data);
		if(head == null && tail == null) {
		head = node;
		tail = node;
		}else {
			MyNode nodeTwo = tail;
			node.setLink(nodeTwo);
			tail = node;
		}
	}
	
	public MyNode dequeue() {
		MyNode node = tail, last = null;
		while(node != null) {
			if(node.getLink()==null) {
				last = last.getLink();
				break;
			}else {
				last = node;
				node = node.getLink().getLink();
			}
		}
		MyNode returnNode = last.getLink();
		last.setLink(null);
		head = last;
		return returnNode;
	}
	
	public void printAll() {
		System.out.print("QUEUE: ");
		MyNode node = tail;
		while(node != null) {
			System.out.print(node.getData() + " ");
			node = node.getLink();
		}
		System.out.println("");
	}
}
