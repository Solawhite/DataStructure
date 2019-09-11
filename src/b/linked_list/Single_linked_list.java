package b.linked_list;

/**
 * <p>节点下标从0开始!!
 * @author Yis
 * @version Date：2019年3月5日 下午2:47:48
 * @param <E> 通用类型
 */
public class Single_linked_list<E> {
	private Node head = new Node();
	
	// 优化尾插，直接指向最后一个就不用遍历了
	private Node last;
	
	//中间变量，省的每次都申请空间
	private Node next;
	private int size;

	public Single_linked_list() {
		last = null;
		size = 0;
	}

	@SuppressWarnings("unchecked")
	public Single_linked_list(Single_linked_list<E> list) {
		Node copy = list.head;
		while (copy.getNext() != null) {
			copy = copy.getNext();
			add((E) copy.getData());
		}
	}

	/*
	 * 添加元素，是第一个插到head后面，否则插到last后面
	 */
	public void add(E e) {
		next = new Node(e);
		if (size == 0) {
			head.setNext(next);
			last = next;
		} else {
			last.setNext(next);
			last = next;
		}
		size++;
	}

	/*
	 * 插入到第几个（第一个元素的下标为0）
	 */
	public void addTo(int index, E data) {
		if (index < 0 || index > size) {// 不合法报错
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		} else {
			/*
			 * 插到头时
			 */
			if (index == 0) {
				next = new Node(data);
				next.setNext(head.getNext());
				head.setNext(next);
			}
			/*
			 * 其他位置，（尾部要改last）
			 */
			else {
				Node node = head;
				for (int i = 0; i < index; i++) {
					node = node.getNext();
				}
				next = new Node(data);
				next.setNext(node.getNext());
				node.setNext(next);
				if (size == index)
					last = next;
			}

			size++;
		}
	}

	/*
	 * 删除元素，pre指前，after指后，pre指after 删除中间的
	 * 最后一个的时候调整last
	 */
	public void delete(int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		} else {
			Node pre = head;
			Node aftet = head;
			for (int i = 0; i < index; i++) {
				pre = pre.getNext();
				aftet = aftet.getNext();
			}
			aftet = aftet.getNext().getNext();
			pre.setNext(aftet);
			if (index == size) {
				last = pre;
			}
			size--;
		}
	}

	 /**
     * 反转
     */

    public void back() {
        int num = size - 1;
        Node node = head.getNext();// 得到下标为num的点
        for (int i = 0; i < num; i++) {
            node = node.getNext();
        }
        last = node;// 更新last
        if (num > 0) {

            for (int j = 0; j < num; j++) {
                Node node1 = head.getNext();// 找到下标为num-j-1的点
                for (int i = 0; i < num - j - 1; i++) {
                    node1 = node1.getNext();
                }

                node1.setNext(null);// 该节点的引用域为空
                last.setNext(node1);// 最后一个几点指向该节点
                last = node1;// 更新最后一个节点
            }
            num--;
        }
        head.setNext(null);// 断开根节点与原第一个节点
        head.setNext(node);// 根节点指向原最后一个节点
    }

	
	
	
	/*
	 * 抛出越界的异常
	 */
	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}

	public void print() {
		Node node = head;
		while (node.getNext() != null) {
			node = node.getNext();
			System.out.print(node.getData() + "->");
		}
		System.out.println("null");
	}

}
