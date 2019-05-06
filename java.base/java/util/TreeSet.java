package java.util;

/**
 * @param <E>
 * @author linxu
 * @date 2019/5/6
 */

public class TreeSet<E> extends AbstractSet<E>
        implements NavigableSet<E>, Cloneable, java.io.Serializable {
    /**
     * 本质就是TreeMap
     */
    private transient NavigableMap<E, Object> m;

    //与支持映射中的对象关联的虚值
    private static final Object PRESENT = new Object();

    /**
     * 可以用指定的navigable map创建，TreeMap实现了该接口
     */
    TreeSet(NavigableMap<E, Object> m) {
        this.m = m;
    }

    /**
     * 虽然该构造方法无参数，但其实是new 了一个TreeMap并且调用如下的构造器：
     * <code>
     * TreeSet(NavigableMap<E,Object> m) {
     * this.m = m;
     * }
     * </code>
     */
    public TreeSet() {
        this(new TreeMap<>());
    }

    /**
     * 自定义比较器的构造方法
     */
    public TreeSet(Comparator<? super E> comparator) {
        this(new TreeMap<>(comparator));
    }

    /**
     * 通过集合构造TreeSet
     */
    public TreeSet(Collection<? extends E> c) {
        //this()其实就是调用无参的构造器
        this();
        //把元素添加进入TreeMap
        addAll(c);
    }

    /**
     * 通过集合构造TreeSet
     */
    public TreeSet(SortedSet<E> s) {
        this(s.comparator());
        addAll(s);
    }

    /**
     * 按升序返回此集合中元素的迭代器。
     */
    public Iterator<E> iterator() {
        return m.navigableKeySet().iterator();
    }

    /**
     * 按降序返回该集合中元素的迭代器。
     */
    public Iterator<E> descendingIterator() {
        return m.descendingKeySet().iterator();
    }



    /**
     * 返回降序的set
     */
    public NavigableSet<E> descendingSet() {
        return new TreeSet<>(m.descendingMap());
    }

    /**
     * 返回TreeSet的容量
     */
    public int size() {
        return m.size();
    }

    /**
     * 判空
     */
    public boolean isEmpty() {
        return m.isEmpty();
    }

    /**
     * 判断是否存在某个对象，底层实现是：
     * 调用
     * <code>
     *     TreeMap.containsKey(o);
     * </code>
     */
    public boolean contains(Object o) {
        return m.containsKey(o);
    }

    /**
     * 添加e到TreeSet中
     */
    public boolean add(E e) {
        return m.put(e, PRESENT) == null;
    }

    /**
     * 删除TreeSet中的对象o
     */
    public boolean remove(Object o) {
        return m.remove(o) == PRESENT;
    }

    /**
     * 清空TreeSet，实质上是清空treeMap
     */
    public void clear() {
        m.clear();
    }

    /**
     * 添加集合的所有元素
     */
    public boolean addAll(Collection<? extends E> c) {
        // Use linear-time version if applicable
        if (m.size() == 0 && c.size() > 0 &&
                c instanceof SortedSet &&
                m instanceof TreeMap) {
            SortedSet<? extends E> set = (SortedSet<? extends E>) c;
            TreeMap<E, Object> map = (TreeMap<E, Object>) m;
            Comparator<?> cc = set.comparator();
            Comparator<? super E> mc = map.comparator();
            if (cc == mc || (cc != null && cc.equals(mc))) {
                map.addAllForTreeSet(set, PRESENT);
                return true;
            }
        }
        return super.addAll(c);
    }

    /**
     * 返回子Set，实际上是通过TreeMap的subMap()实现的。
     */
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive,
                                  E toElement, boolean toInclusive) {
        return new TreeSet<>(m.subMap(fromElement, fromInclusive,
                toElement, toInclusive));
    }

    // 返回Set的头部，范围是：从头部到toElement。
    // inclusive是是否包含toElement的标志
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return new TreeSet<>(m.headMap(toElement, inclusive));
    }
    // 返回Set的尾部，范围是：从fromElement到结尾。
    // inclusive是是否包含fromElement的标志
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return new TreeSet<>(m.tailMap(fromElement, inclusive));
    }

    // 返回子Set。范围是：从fromElement(包括)到toElement(不包括)。
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    // 返回Set的头部，范围是：从头部到toElement(不包括)。
    public SortedSet<E> headSet(E toElement) {
        return headSet(toElement, false);
    }

    // 返回Set的尾部，范围是：从fromElement到结尾(不包括)。
    public SortedSet<E> tailSet(E fromElement) {
        return tailSet(fromElement, true);
    }
    // 返回Set的比较器
    public Comparator<? super E> comparator() {
        return m.comparator();
    }

    //返回第一个键
    public E first() {
        return m.firstKey();
    }

    //返回最后一个键
    public E last() {
        return m.lastKey();
    }

    // NavigableSet API methods
    // 返回Set中小于e的最大元素
    public E lower(E e) {
        return m.lowerKey(e);
    }

    // 返回Set中小于/等于e的最大元素
    public E floor(E e) {
        return m.floorKey(e);
    }

    // 返回Set中大于/等于e的最小元素
    public E ceiling(E e) {
        return m.ceilingKey(e);
    }

    // 返回Set中大于e的最小元素
    public E higher(E e) {
        return m.higherKey(e);
    }

    // 获取第一个元素，并将该元素从TreeMap中删除。
    public E pollFirst() {
        Map.Entry<E, ?> e = m.pollFirstEntry();
        return (e == null) ? null : e.getKey();
    }

    // 获取最后一个元素，并将该元素从TreeMap中删除。
    public E pollLast() {
        Map.Entry<E, ?> e = m.pollLastEntry();
        return (e == null) ? null : e.getKey();
    }

    // 克隆一个TreeSet，并返回Object对象
    @SuppressWarnings("unchecked")
    public Object clone() {
        TreeSet<E> clone;
        try {
            clone = (TreeSet<E>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }

        clone.m = new TreeMap<>(m);
        return clone;
    }

    // java.io.Serializable的写入函数
    // 将TreeSet的“比较器、容量，所有的元素值”都写入到输出流中
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        // Write out any hidden stuff
        s.defaultWriteObject();

        // Write out Comparator
        s.writeObject(m.comparator());

        // Write out size
        s.writeInt(m.size());

        // Write out all elements in the proper order.
        for (E e : m.keySet())
            s.writeObject(e);
    }

    // java.io.Serializable的读取函数：根据写入方式读出
    // 先将TreeSet的“比较器、容量、所有的元素值”依次读出
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        // Read in any hidden stuff
        s.defaultReadObject();

        // Read in Comparator
        @SuppressWarnings("unchecked")
        Comparator<? super E> c = (Comparator<? super E>) s.readObject();

        // Create backing TreeMap
        TreeMap<E, Object> tm = new TreeMap<>(c);
        m = tm;

        // Read in size
        int size = s.readInt();

        tm.readTreeSet(size, s, PRESENT);
    }
    //分裂迭代器
    public Spliterator<E> spliterator() {
        return TreeMap.keySpliteratorFor(m);
    }

    private static final long serialVersionUID = -2479143000061671589L;
}
