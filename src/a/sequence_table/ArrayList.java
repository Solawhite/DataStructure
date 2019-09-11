package a.sequence_table;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;

public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
	private static final long serialVersionUID = 8683452581122892189L;
	// Ĭ�ϵĳ�ʼ����Ϊ10
	private static final int DEFAULT_CAPACITY = 10;
	private static final Object[] EMPTY_ELEMENTDATA = {};
	private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
	transient Object[] elementData;
	// ArrayList��ʵ�����ݵ�����
	private int size;

	public ArrayList(int initialCapacity) // ����ʼ������С�Ĺ��캯��
	{
		if (initialCapacity > 0) // ��ʼ��������0,ʵ��������
		{
			this.elementData = new Object[initialCapacity];
		} else if (initialCapacity == 0) // ��ʼ������0���������鸳��elementData
		{
			this.elementData = EMPTY_ELEMENTDATA;
		} else // ��ʼ����С�ڣ����쳣
		{
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		}
	}

	public ArrayList() // �޲ι��캯��,Ĭ������Ϊ10
	{
		this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
	}

	public ArrayList(Collection<? extends E> c) // ����һ������collection��ArrayList
	{
		elementData = c.toArray(); // ���ذ���c����Ԫ�ص�����
		if ((size = elementData.length) != 0) {
			if (elementData.getClass() != Object[].class)
				elementData = Arrays.copyOf(elementData, size, Object[].class);// ����ָ�����飬ʹelementData����ָ������
		} else {
			// c��û��Ԫ��
			this.elementData = EMPTY_ELEMENTDATA;
		}
	}

	// ����ǰ����ֵ��Ϊ��ǰʵ��Ԫ�ش�С
	public void trimToSize() {
		modCount++;
		if (size < elementData.length) {
			elementData = (size == 0) ? EMPTY_ELEMENTDATA : Arrays.copyOf(elementData, size);
		}
	}

	// �����ϵ�capacit����minCapacity
	public void ensureCapacity(int minCapacity) {
		int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) ? 0 : DEFAULT_CAPACITY;
		if (minCapacity > minExpand) {
			ensureExplicitCapacity(minCapacity);
		}
	}

	private void ensureCapacityInternal(int minCapacity) {
		if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
			minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
		}
		ensureExplicitCapacity(minCapacity);
	}

	private void ensureExplicitCapacity(int minCapacity) {
		modCount++;
		if (minCapacity - elementData.length > 0)
			grow(minCapacity);
	}

	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private void grow(int minCapacity) {
		int oldCapacity = elementData.length;
		// ע��˴�����capacity�ķ�ʽ�ǽ�������һλ�ټ���ԭ��������ʵ������������1.5��
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		if (newCapacity - minCapacity < 0)
			newCapacity = minCapacity;
		if (newCapacity - MAX_ARRAY_SIZE > 0)
			newCapacity = hugeCapacity(minCapacity);
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	private static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}

	// ����ArrayList�Ĵ�С
	public int size() {
		return size;
	}

	// �ж�ArrayList�Ƿ�Ϊ��
	public boolean isEmpty() {
		return size == 0;
	}

	// �ж�ArrayList���Ƿ����Object(o)
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	// ������ң�����ArrayList��Ԫ��Object(o)������λ��
	public int indexOf(Object o) {
		if (o == null) {
			for (int i = 0; i < size; i++)
				if (elementData[i] == null)
					return i;
		} else {
			for (int i = 0; i < size; i++)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
	}

	// ������ң����ط���ArrayList��Ԫ��Object(o)������λ��
	public int lastIndexOf(Object o) {
		if (o == null) {
			for (int i = size - 1; i >= 0; i--)
				if (elementData[i] == null)
					return i;
		} else {
			for (int i = size - 1; i >= 0; i--)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
	}

	// ���ش� ArrayListʵ����ǳ������
	public Object clone() {
		try {
			ArrayList<?> v = (ArrayList<?>) super.clone();
			v.elementData = Arrays.copyOf(elementData, size);
			v.modCount = 0;
			return v;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError(e);
		}
	}

	// ����һ������ArrayList������Ԫ�ص�����
	public Object[] toArray() {
		return Arrays.copyOf(elementData, size);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		if (a.length < size)
			return (T[]) Arrays.copyOf(elementData, size, a.getClass());
		System.arraycopy(elementData, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}

	@SuppressWarnings("unchecked")
	E elementData(int index) {
		return (E) elementData[index];
	}

	// ������ָ��������ֵ
	public E get(int index) {
		rangeCheck(index); // ������������ֵ�Ƿ�Խ��
		return elementData(index);
	}

	// ��ָ�������ϵ�ֵ�滻Ϊ��ֵ�������ؾ�ֵ
	public E set(int index, E element) {
		rangeCheck(index);
		E oldValue = elementData(index);
		elementData[index] = element;
		return oldValue;
	}

	// ��ָ����Ԫ����ӵ����б��β��
	public boolean add(E e) {
		ensureCapacityInternal(size + 1);
		elementData[size++] = e;
		return true;
	}

	// ��element��ӵ�ArrayList��ָ��λ��
	public void add(int index, E element) {
		rangeCheckForAdd(index);
		ensureCapacityInternal(size + 1);

		// ��ָ��Դ�����и���һ�����飬���ƴ�ָ����λ�ÿ�ʼ����Ŀ�������ָ��λ�ý�����
		// arraycopy(�����Ƶ�����, �ӵڼ���Ԫ�ؿ�ʼ����, Ҫ���Ƶ�������, �ӵڼ���Ԫ�ؿ�ʼճ��, һ����Ҫ���Ƶ�Ԫ�ظ���)
		// ��������elementData��indexλ�ÿ�ʼ�����Ƶ�index+1λ��,������size-index��Ԫ��
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = element;
		size++;
	}

	// ɾ��ArrayListָ��λ�õ�Ԫ��
	public E remove(int index) {
		rangeCheck(index);
		modCount++;
		E oldValue = elementData(index);
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = null; // ��ԭ�������һ��λ����Ϊnull
		return oldValue;
	}

	// �Ƴ�ArrayList���״γ��ֵ�ָ��Ԫ��(�������)��
	public boolean remove(Object o) {
		if (o == null) {
			for (int index = 0; index < size; index++)
				if (elementData[index] == null) {
					fastRemove(index);
					return true;
				}
		} else {
			for (int index = 0; index < size; index++)
				if (o.equals(elementData[index])) {
					fastRemove(index);
					return true;
				}
		}
		return false;
	}

	// ����ɾ��ָ��λ�õ�Ԫ��
	private void fastRemove(int index) {
		modCount++;
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = null;
	}

	// ���ArrayList����ȫ����Ԫ����Ϊnull
	public void clear() {
		modCount++;
		for (int i = 0; i < size; i++)
			elementData[i] = null;
		size = 0;
	}

	// ����c�ĵ����������ص�Ԫ��˳�򣬽�c�е�����Ԫ����ӵ����б��β��
	public boolean addAll(Collection<? extends E> c) {
		Object[] a = c.toArray();
		int numNew = a.length;
		ensureCapacityInternal(size + numNew); // Increments modCount
		System.arraycopy(a, 0, elementData, size, numNew);
		size += numNew;
		return numNew != 0;
	}

	// ��ָ��λ��index��ʼ����ָ��c�е�����Ԫ�ز��뵽���б���
	public boolean addAll(int index, Collection<? extends E> c) {
		rangeCheckForAdd(index);
		Object[] a = c.toArray();
		int numNew = a.length;
		ensureCapacityInternal(size + numNew); // Increments modCount
		int numMoved = size - index;
		if (numMoved > 0)
			// �Ƚ�ArrayList�д�index��ʼ��numMoved��Ԫ���ƶ�����ʼλ��Ϊindex+numNew�ĺ���ȥ
			System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
		// �ٽ�c�е�numNew��Ԫ�ظ��Ƶ���ʼλ��Ϊindex�Ĵ洢�ռ���ȥ
		System.arraycopy(a, 0, elementData, index, numNew);
		size += numNew;
		return numNew != 0;
	}

	// ɾ��fromIndex��toIndex֮���ȫ��Ԫ��
	protected void removeRange(int fromIndex, int toIndex) {
		modCount++;
		// numMovedΪɾ�����������Ԫ�ظ���
		int numMoved = size - toIndex;
		// ��ɾ�����������Ԫ�ظ��Ƶ���fromIndexΪ��ʼλ�õĴ洢�ռ���ȥ
		System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);
		int newSize = size - (toIndex - fromIndex);
		// ��ArrayList����(toIndex-fromIndex)��Ԫ����Ϊnull
		for (int i = newSize; i < size; i++) {
			elementData[i] = null;
		}
		size = newSize;
	}

	// ��������Ƿ�Խ��
	private void rangeCheck(int index) {
		if (index >= size)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private void rangeCheckForAdd(int index) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}

	// ɾ��ArrayList�а�����c�е�Ԫ��
	public boolean removeAll(Collection<?> c) {
		Objects.requireNonNull(c);
		return batchRemove(c, false);
	}

	// ɾ��ArrayList�г�������c�е�Ԫ�أ���removeAll�෴
	public boolean retainAll(Collection<?> c) {
		Objects.requireNonNull(c); // ���ָ�������Ƿ�Ϊ��
		return batchRemove(c, true);
	}

	private boolean batchRemove(Collection<?> c, boolean complement) {
		final Object[] elementData = this.elementData;
		int r = 0, w = 0;
		boolean modified = false;
		try {
			for (; r < size; r++)
				if (c.contains(elementData[r]) == complement) // �ж�c���Ƿ���elementData[r]Ԫ��

					elementData[w++] = elementData[r];
		} finally {
			if (r != size) {
				System.arraycopy(elementData, r, elementData, w, size - r);
				w += size - r;
			}
			if (w != size) {
				// clear to let GC do its work
				for (int i = w; i < size; i++)
					elementData[i] = null;
				modCount += size - w;
				size = w;
				modified = true;
			}
		}
		return modified;
	}

	// ��ArrayList�ġ����������е�Ԫ��ֵ����д�뵽�������
	private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException {
		int expectedModCount = modCount;
		s.defaultWriteObject();
		// д�������С
		s.writeInt(size);
		// д�����������Ԫ��
		for (int i = 0; i < size; i++) {
			s.writeObject(elementData[i]);
		}
		if (modCount != expectedModCount) {
			throw new ConcurrentModificationException();
		}
	}

	// �Ƚ�ArrayList�ġ���С��������Ȼ�󽫡����е�Ԫ��ֵ������
	private void readObject(java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException {
		elementData = EMPTY_ELEMENTDATA;
		s.defaultReadObject();
		s.readInt(); // ignored
		if (size > 0) {
			// be like clone(), allocate array based upon size not capacity
			ensureCapacityInternal(size);
			Object[] a = elementData;
			// Read in all elements in the proper order.
			for (int i = 0; i < size; i++) {
				a[i] = s.readObject();
			}
		}
	}
}