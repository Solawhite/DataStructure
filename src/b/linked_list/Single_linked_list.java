package b.linked_list;

/**
 * <p>�ڵ��±��0��ʼ!!
 * @author Yis
 * @version Date��2019��3��5�� ����2:47:48
 * @param <E> ͨ������
 */
public class Single_linked_list<E> {
	private Node head = new Node();
	
	// �Ż�β�壬ֱ��ָ�����һ���Ͳ��ñ�����
	private Node last;
	
	//�м������ʡ��ÿ�ζ�����ռ�
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
	 * ���Ԫ�أ��ǵ�һ���嵽head���棬����嵽last����
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
	 * ���뵽�ڼ�������һ��Ԫ�ص��±�Ϊ0��
	 */
	public void addTo(int index, E data) {
		if (index < 0 || index > size) {// ���Ϸ�����
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		} else {
			/*
			 * �嵽ͷʱ
			 */
			if (index == 0) {
				next = new Node(data);
				next.setNext(head.getNext());
				head.setNext(next);
			}
			/*
			 * ����λ�ã���β��Ҫ��last��
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
	 * ɾ��Ԫ�أ�preָǰ��afterָ��preָafter ɾ���м��
	 * ���һ����ʱ�����last
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
     * ��ת
     */

    public void back() {
        int num = size - 1;
        Node node = head.getNext();// �õ��±�Ϊnum�ĵ�
        for (int i = 0; i < num; i++) {
            node = node.getNext();
        }
        last = node;// ����last
        if (num > 0) {

            for (int j = 0; j < num; j++) {
                Node node1 = head.getNext();// �ҵ��±�Ϊnum-j-1�ĵ�
                for (int i = 0; i < num - j - 1; i++) {
                    node1 = node1.getNext();
                }

                node1.setNext(null);// �ýڵ��������Ϊ��
                last.setNext(node1);// ���һ������ָ��ýڵ�
                last = node1;// �������һ���ڵ�
            }
            num--;
        }
        head.setNext(null);// �Ͽ����ڵ���ԭ��һ���ڵ�
        head.setNext(node);// ���ڵ�ָ��ԭ���һ���ڵ�
    }

	
	
	
	/*
	 * �׳�Խ����쳣
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
