package c.stack_and_queue;

import java.util.EmptyStackException;
import java.util.Vector;

public
class Stack<E> extends Vector<E> {
    // �汾ID��������ڰ汾�������ƣ����ﲻ����ᣡ
    private static final long serialVersionUID = 1224463164541339165L;

    // ���캯��
    public Stack() {
    }

    // push��������Ԫ�ش���ջ��
    public E push(E item) {
        // ��Ԫ�ش���ջ����
        // addElement()��ʵ����Vector.java��
        addElement(item);

        return item;
    }

    // pop����������ջ��Ԫ�أ��������ջ��ɾ��
    public synchronized E pop() {
        E obj;
        int len = size();

        obj = peek();
        // ɾ��ջ��Ԫ�أ�removeElementAt()��ʵ����Vector.java��
        removeElementAt(len - 1);

        return obj;
    }

    // peek����������ջ��Ԫ�أ���ִ��ɾ������
    public synchronized E peek() {
        int    len = size();

        if (len == 0)
            throw new EmptyStackException();
        // ����ջ��Ԫ�أ�elementAt()����ʵ����Vector.java��
        return elementAt(len - 1);
    }

    // ջ�Ƿ�Ϊ��
    public boolean empty() {
        return size() == 0;
    }

    // ���ҡ�Ԫ��o����ջ�е�λ�ã���ջ����ջ��������
    public synchronized int search(Object o) {
        // ��ȡԪ��������elementAt()����ʵ����Vector.java��
        int i = lastIndexOf(o);

        if (i >= 0) {
            return size() - i;
        }
        return -1;
    }
}