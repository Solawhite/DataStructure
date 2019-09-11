package b.linked_list;
/**
* @author Yis
* @version Date：2019年3月5日 下午2:45:04
*/
public class Node {
	private Object data;
	private Node next;
	public Node() {
		next = null;
	}
	public Node(Object data) {
        //super();
        this.data = data;
        next = null;
    }
	
	public void setNext(Node next) {
		this.next = next;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Node getNext() {
		return next;
	}
	
}
