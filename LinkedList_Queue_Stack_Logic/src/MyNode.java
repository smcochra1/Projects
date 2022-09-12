
public class MyNode<T> {

		T data;
		private MyNode link;
		
		public T getData() {
			return data;
		}
		
		public MyNode getLink() {
			return link;
		}
		
		public void setData(T data) {
			this.data=data;
		}
		
		public void setLink(MyNode node) {
			link=node;
		}
		
		MyNode(){
			link=null;
		}
		
		MyNode(T data){
			this.data=data;
			link=null;
		}
}
