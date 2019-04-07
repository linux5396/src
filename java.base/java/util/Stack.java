package java.util;

/**
 * 这个栈遵循：（LIFO）先进后出或者后进先出的规则；
 * 并且，继承自向量<code>Vector</code>,因此，操作方法是线程安全的；
 * <>push</>调用上层的addElement到数组尾；由于底层是线程安全的，因此还是
 * @param <E>
 */
public
class Stack<E> extends Vector<E> {
    /**
     * 初始化的栈为空
     */
    public Stack() {
    }

    /**
     * 压栈，由于底层的<>addElement(item)</>
     * 是线程安全的，并且该方法内没有其它资源问题，因此外层无需加锁。
     */
    public E push(E item) {
        addElement(item);

        return item;
    }

    /**
     * 由于有共享资源size，因此外层需要加锁。
     */
    public synchronized E pop() {
        E       obj;
        int     len = size();

        obj = peek();
        removeElementAt(len - 1);

        return obj;
    }

    /**
     * 由于存在共享资源size，因此需要加锁。
     */
    public synchronized E peek() {
        int     len = size();

        if (len == 0)
            throw new EmptyStackException();
        return elementAt(len - 1);
    }

    /**
     * 判空
     * <>size</>的底层也是线程安全的
     */
    public boolean empty() {
        return size() == 0;
    }

    /**
     * 线程安全
     */
    public synchronized int search(Object o) {
        int i = lastIndexOf(o);

        if (i >= 0) {
            return size() - i;
        }
        return -1;
    }

    private static final long serialVersionUID = 1224463164541339165L;
}
