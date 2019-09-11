package c.stack_and_queue;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class Vector<E>
    extends AbstractList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
   
    // ����Vector�����ݵ�����
    protected Object[] elementData;

    // ʵ�����ݵ�����
    protected int elementCount;

    // ��������ϵ��
    protected int capacityIncrement;

    // Vector�����а汾��
    private static final long serialVersionUID = -2767605614048989439L;

    // Vector���캯����Ĭ��������10��
    public Vector() {
        this(10);
    }

    // ָ��Vector������С�Ĺ��캯��
    public Vector(int initialCapacity) {
        this(initialCapacity, 0);
    }

    // ָ��Vector"������С"��"����ϵ��"�Ĺ��캯��
    public Vector(int initialCapacity, int capacityIncrement) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        // �½�һ�����飬����������initialCapacity
        this.elementData = new Object[initialCapacity];
        // ������������ϵ��
        this.capacityIncrement = capacityIncrement;
    }

    // ָ�����ϵ�Vector���캯����
    public Vector(Collection<? extends E> c) {
        // ��ȡ������(c)�������飬�����丳ֵ��elementData
        elementData = c.toArray();
        // �������鳤��
        elementCount = elementData.length;
        // c.toArray might (incorrectly) not return Object[] (see 6260652)
        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, elementCount, Object[].class);
    }

    // ������Vector��ȫ��Ԫ�ض�����������anArray��
    public synchronized void copyInto(Object[] anArray) {
        System.arraycopy(elementData, 0, anArray, 0, elementCount);
    }

    // ����ǰ����ֵ��Ϊ =ʵ��Ԫ�ظ���
    public synchronized void trimToSize() {
        modCount++;
        int oldCapacity = elementData.length;
        if (elementCount < oldCapacity) {
            elementData = Arrays.copyOf(elementData, elementCount);
        }
    }

    // ȷ�ϡ�Vector�������İ�������
    private void ensureCapacityHelper(int minCapacity) {
        int oldCapacity = elementData.length;
        // ��Vector���������������ɵ�ǰ��ȫ��Ԫ�أ�����������С��
        // �� ��������ϵ��>0(��capacityIncrement>0)������������capacityIncrement
        // ���򣬽���������һ����
        if (minCapacity > oldCapacity) {
            @SuppressWarnings("unused")
			Object[] oldData = elementData;
            int newCapacity = (capacityIncrement > 0) ?
                (oldCapacity + capacityIncrement) : (oldCapacity * 2);
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    // ȷ��Vector��������
    public synchronized void ensureCapacity(int minCapacity) {
        // ��Vector�ĸı�ͳ����+1
        modCount++;
        ensureCapacityHelper(minCapacity);
    }

    // ��������ֵΪ newSize
    public synchronized void setSize(int newSize) {
        modCount++;
        if (newSize > elementCount) {
            // �� "newSize ���� Vector����"�������Vector�Ĵ�С��
            ensureCapacityHelper(newSize);
        } else {
            // �� "newSize С��/���� Vector����"����newSizeλ�ÿ�ʼ��Ԫ�ض�����Ϊnull
            for (int i = newSize ; i < elementCount ; i++) {
                elementData[i] = null;
            }
        }
        elementCount = newSize;
    }

    // ���ء�Vector���ܵ�������
    public synchronized int capacity() {
        return elementData.length;
    }

    // ���ء�Vector��ʵ�ʴ�С������Vector��Ԫ�ظ���
    public synchronized int size() {
        return elementCount;
    }

    // �ж�Vector�Ƿ�Ϊ��
    public synchronized boolean isEmpty() {
        return elementCount == 0;
    }

    // ���ء�Vector��ȫ��Ԫ�ض�Ӧ��Enumeration��
    public Enumeration<E> elements() {
        // ͨ��������ʵ��Enumeration
        return new Enumeration<E>() {
            int count = 0;

            // �Ƿ������һ��Ԫ��
            public boolean hasMoreElements() {
                return count < elementCount;
            }

            // ��ȡ��һ��Ԫ��
            @SuppressWarnings("unchecked")
			public E nextElement() {
                synchronized (Vector.this) {
                    if (count < elementCount) {
                        return (E)elementData[count++];
                    }
                }
                throw new NoSuchElementException("Vector Enumeration");
            }
        };
    }

    // ����Vector���Ƿ��������(o)
    public boolean contains(Object o) {
        return indexOf(o, 0) >= 0;
    }


    // ��indexλ�ÿ�ʼ������Ԫ��(o)��
    // ���ҵ����򷵻�Ԫ�ص�����ֵ�����򣬷���-1
    public synchronized int indexOf(Object o, int index) {
        if (o == null) {
            // ������Ԫ��Ϊnull���������ҳ�nullԪ�أ�����������Ӧ�����
            for (int i = index ; i < elementCount ; i++)
            if (elementData[i]==null)
                return i;
        } else {
            // ������Ԫ�ز�Ϊnull���������ҳ���Ԫ�أ�����������Ӧ�����
            for (int i = index ; i < elementCount ; i++)
            if (o.equals(elementData[i]))
                return i;
        }
        return -1;
    }

    // ���Ҳ�����Ԫ��(o)��Vector�е�����ֵ
    public int indexOf(Object o) {
        return indexOf(o, 0);
    }

    // �Ӻ���ǰ����Ԫ��(o)��������Ԫ�ص�����
    public synchronized int lastIndexOf(Object o) {
        return lastIndexOf(o, elementCount-1);
    }

    // �Ӻ���ǰ����Ԫ��(o)����ʼλ���Ǵ�ǰ���ĵ�index������
    // ���ҵ����򷵻�Ԫ�صġ�����ֵ�������򣬷���-1��
    public synchronized int lastIndexOf(Object o, int index) {
        if (index >= elementCount)
            throw new IndexOutOfBoundsException(index + " >= "+ elementCount);

        if (o == null) {
            // ������Ԫ��Ϊnull�������ҳ�nullԪ�أ�����������Ӧ�����
            for (int i = index; i >= 0; i--)
            if (elementData[i]==null)
                return i;
        } else {
            // ������Ԫ�ز�Ϊnull�������ҳ���Ԫ�أ�����������Ӧ�����
            for (int i = index; i >= 0; i--)
            if (o.equals(elementData[i]))
                return i;
        }
        return -1;
    }

    // ����Vector��indexλ�õ�Ԫ�ء�
    // ��index�½ᣬ���׳��쳣
    @SuppressWarnings("unchecked")
	public synchronized E elementAt(int index) {
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
        }

        return (E)elementData[index];
    }

    // ��ȡVector�еĵ�һ��Ԫ�ء�
    // ��ʧ�ܣ����׳��쳣��
    public synchronized E firstElement() {
        if (elementCount == 0) {
            throw new NoSuchElementException();
        }
        return (E)elementData[0];
    }

    // ��ȡVector�е����һ��Ԫ�ء�
    // ��ʧ�ܣ����׳��쳣��
    public synchronized E lastElement() {
        if (elementCount == 0) {
            throw new NoSuchElementException();
        }
        return (E)elementData[elementCount - 1];
    }

    // ����indexλ�õ�Ԫ��ֵΪobj
    public synchronized void setElementAt(E obj, int index) {
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " +
                                 elementCount);
        }
        elementData[index] = obj;
    }

    // ɾ��indexλ�õ�Ԫ��
    public synchronized void removeElementAt(int index) {
        modCount++;
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " +
                                 elementCount);
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }

        int j = elementCount - index - 1;
        if (j > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, j);
        }
        elementCount--;
        elementData[elementCount] = null; /* to let gc do its work */
    }

    // ��indexλ�ô�����Ԫ��(obj)
    public synchronized void insertElementAt(E obj, int index) {
        modCount++;
        if (index > elementCount) {
            throw new ArrayIndexOutOfBoundsException(index
                                 + " > " + elementCount);
        }
        ensureCapacityHelper(elementCount + 1);
        System.arraycopy(elementData, index, elementData, index + 1, elementCount - index);
        elementData[index] = obj;
        elementCount++;
    }

    // ����Ԫ��obj����ӵ�Vectorĩβ
    public synchronized void addElement(E obj) {
        modCount++;
        ensureCapacityHelper(elementCount + 1);
        elementData[elementCount++] = obj;
    }

    // ��Vector�в��Ҳ�ɾ��Ԫ��obj��
    // �ɹ��Ļ�������true�����򣬷���false��
    public synchronized boolean removeElement(Object obj) {
        modCount++;
        int i = indexOf(obj);
        if (i >= 0) {
            removeElementAt(i);
            return true;
        }
        return false;
    }

    // ɾ��Vector�е�ȫ��Ԫ��
    public synchronized void removeAllElements() {
        modCount++;
        // ��Vector�е�ȫ��Ԫ����Ϊnull
        for (int i = 0; i < elementCount; i++)
            elementData[i] = null;

        elementCount = 0;
    }

    // ��¡����
    public synchronized Object clone() {
        try {
            Vector<E> v = (Vector<E>) super.clone();
            // ����ǰVector��ȫ��Ԫ�ؿ�����v��
            v.elementData = Arrays.copyOf(elementData, elementCount);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }
    }

    // ����Object����
    public synchronized Object[] toArray() {
        return Arrays.copyOf(elementData, elementCount);
    }

    // ����Vector��ģ�����顣��νģ�����飬�����Խ�T��Ϊ�������������
    public synchronized <T> T[] toArray(T[] a) {
        // ������a�Ĵ�С < Vector��Ԫ�ظ�����
        // ���½�һ��T[]���飬�����С�ǡ�Vector��Ԫ�ظ�������������Vector��ȫ����������������
        if (a.length < elementCount)
            return (T[]) Arrays.copyOf(elementData, elementCount, a.getClass());

        // ������a�Ĵ�С >= Vector��Ԫ�ظ�����
        // ��Vector��ȫ��Ԫ�ض�����������a�С�
    System.arraycopy(elementData, 0, a, 0, elementCount);

        if (a.length > elementCount)
            a[elementCount] = null;

        return a;
    }

    // ��ȡindexλ�õ�Ԫ��
    public synchronized E get(int index) {
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);

        return (E)elementData[index];
    }

    // ����indexλ�õ�ֵΪelement��������indexλ�õ�ԭʼֵ
    public synchronized E set(int index, E element) {
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);

        Object oldValue = elementData[index];
        elementData[index] = element;
        return (E)oldValue;
    }

    // ����Ԫ��e����ӵ�Vector���
    public synchronized boolean add(E e) {
        modCount++;
        ensureCapacityHelper(elementCount + 1);
        elementData[elementCount++] = e;
        return true;
    }

    // ɾ��Vector�е�Ԫ��o
    public boolean remove(Object o) {
        return removeElement(o);
    }

    // ��indexλ�����Ԫ��element
    public void add(int index, E element) {
        insertElementAt(element, index);
    }

    // ɾ��indexλ�õ�Ԫ�أ�������indexλ�õ�ԭʼֵ
    public synchronized E remove(int index) {
        modCount++;
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);
        Object oldValue = elementData[index];

        int numMoved = elementCount - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                     numMoved);
        elementData[--elementCount] = null; // Let gc do its work

        return (E)oldValue;
    }

    // ���Vector
    public void clear() {
        removeAllElements();
    }

    // ����Vector�Ƿ��������c
    public synchronized boolean containsAll(Collection<?> c) {
        return super.containsAll(c);
    }

    // ������c��ӵ�Vector��
    public synchronized boolean addAll(Collection<? extends E> c) {
        modCount++;
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityHelper(elementCount + numNew);
        // ������c��ȫ��Ԫ�ؿ���������elementData��
        System.arraycopy(a, 0, elementData, elementCount, numNew);
        elementCount += numNew;
        return numNew != 0;
    }

    // ɾ������c��ȫ��Ԫ��
    public synchronized boolean removeAll(Collection<?> c) {
        return super.removeAll(c);
    }

    // ɾ�����Ǽ���c�е�Ԫ�ء�
    public synchronized boolean retainAll(Collection<?> c)  {
        return super.retainAll(c);
    }

    // ��indexλ�ÿ�ʼ��������c��ӵ�Vector��
    public synchronized boolean addAll(int index, Collection<? extends E> c) {
        modCount++;
        if (index < 0 || index > elementCount)
            throw new ArrayIndexOutOfBoundsException(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityHelper(elementCount + numNew);

        int numMoved = elementCount - index;
        if (numMoved > 0)
        System.arraycopy(elementData, index, elementData, index + numNew, numMoved);

        System.arraycopy(a, 0, elementData, index, numNew);
        elementCount += numNew;
        return numNew != 0;
    }

    // �������������Ƿ����
    public synchronized boolean equals(Object o) {
        return super.equals(o);
    }

    // �����ϣֵ
    public synchronized int hashCode() {
        return super.hashCode();
    }

    // ���ø����toString()
    public synchronized String toString() {
        return super.toString();
    }

    // ��ȡVector��fromIndex(����)��toIndex(������)���Ӽ�
    public synchronized List<E> subList(int fromIndex, int toIndex) {
        return Collections.synchronizedList(super.subList(fromIndex, toIndex), this);
    }

    // ɾ��Vector��fromIndex��toIndex��Ԫ��
    protected synchronized void removeRange(int fromIndex, int toIndex) {
        modCount++;
        int numMoved = elementCount - toIndex;
        System.arraycopy(elementData, toIndex, elementData, fromIndex,
                         numMoved);

        // Let gc do its work
        int newElementCount = elementCount - (toIndex-fromIndex);
        while (elementCount != newElementCount)
            elementData[--elementCount] = null;
    }

    // java.io.Serializable��д�뺯��
    private synchronized void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        s.defaultWriteObject();
    }
}
