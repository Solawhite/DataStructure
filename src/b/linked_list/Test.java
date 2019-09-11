package b.linked_list;
/**
* @author Yis
* @version Date：2019年3月5日 下午3:08:32
*/
public class Test {

	public static void main(String[] args) {
		Single_linked_list<Integer> list = new Single_linked_list<>();
		for(int i=1;i<=5;i++) {
			list.add(i);
		}
		list.print();
		list.add(6);
		list.print();
		list.addTo(0,0);
		list.print();
		list.addTo(7,7);
		list.print();
		
		list.delete(5);
		list.print();
		list.delete(6);
		list.print();
		
		list.addTo(5, 5);
		list.back();
		list.print();
		
		/*Single_linked_list<Integer> list2 = new Single_linked_list<>(list);
		System.out.println("list2");
		list.add(0);
		list2.add(8);
		list.print();
		list2.print();*/
	}

}
