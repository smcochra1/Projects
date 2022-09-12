
public class MyList<T> {
	T data;
	private MyNode head;
	private int size;

	public MyList(){
		head = null;
		size=0;
	}
	public int getSize() {
		return size;
	}
	
	public void insertFirst(T data){
		MyNode node = new MyNode(data);
		node.setLink(head);
		head = node;	
	}
	
	public void insertAt(int index, T data) {
		MyNode node = new MyNode(data);
		MyNode x = head, y=null;
		int counter = 0;
		while(x != null) {
			if(counter == index) {
				node.setLink(x);
				y.setLink(node);
				break;
			}else {
				y = x;
				x=x.getLink();
				counter++;
			}
		}

	}
	
	public void insertLast(T data) {
		MyNode node = new MyNode(data);
		if(head == null) {
			head = node;
		}else {
			MyNode last = head;
			while(last.getLink() != null) {
				last = last.getLink();
			}
			last.setLink(node);
		}
		
	}
	
	public void removeFirst() {
		MyNode node = head;
		head = node.getLink();
	}
	
	public void removeAt(int index) {
		MyNode currNode = head, prev = null;
		if(index == 0 && currNode != null) {
			head = currNode.getLink();
			System.out.println("Head element deleted");
		}
		int counter = 0;
		while(currNode != null) {
			if(counter == index) {
				prev.setLink(currNode.getLink());
				System.out.println("Element deleted at index " + index);
				break;
			}else {
				prev = currNode;
				currNode = currNode.getLink();
				counter++;
			}
		}
		if(currNode == null)
			System.out.println("Element at index " + index + " does not exist!");
	}
	
	public void removeLast() {
		MyNode node = head, x = null;
		while(node != null) {
			if(node.getLink() == null) {
				x = x.getLink();
				break;
			}else {
				x=node;
				node=node.getLink().getLink();
			}
		}
		x.setLink(null);
	}
	
	public void printAll() {
		System.out.print("LIST: ");
		MyNode currNode = head;
		while(currNode != null) {
			System.out.print(currNode.getData() + " ");
			currNode = currNode.getLink();
		}
		System.out.println("");
		
	}
}
