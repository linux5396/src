package java.util;

import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 */

public interface Collection<E> extends Iterable<E> {
    /**
     * 返回此集合中的元素数量。如果这个集合
     * 包含超过{@code Integer.MAX_VALUE}元素,返回
     * {@code Integer.MAX_VALUE}。
     *
     * @return count
     */
    int size();

    /**
     * @return 如果此集合不包含元素，则为{@code true}
     */
    boolean isEmpty();

    /**
     * 如果这个集合包含指定的元素，则返回{@code true}。
     * 更正式地说，当且仅当这个集合返回{@code true}
     * 至少包含一个这样的元素{@code e}
     */
    boolean contains(Object o);

    /**
     *
     */
    Iterator<E> iterator();

    /**
     *
     */
    Object[] toArray();

    /**
     *
     */
    <T> T[] toArray(T[] a);

    // Modification Operations

    /**
     * 添加
     */
    boolean add(E e);

    /**
     * Removes a single instance of the specified element from this
     * collection, if it is present (optional operation).  More formally,
     * removes an element {@code e} such that
     * {@code Objects.equals(o, e)}, if
     * this collection contains one or more such elements.  Returns
     * {@code true} if this collection contained the specified element (or
     * equivalently, if this collection changed as a result of the call).
     *
     * @param o element to be removed from this collection, if present
     * @return {@code true} if an element was removed as a result of this call
     * @throws ClassCastException            if the type of the specified element
     *                                       is incompatible with this collection
     *                                       (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified element is null and this
     *                                       collection does not permit null elements
     *                                       (<a href="#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the {@code remove} operation
     *                                       is not supported by this collection
     */
    boolean remove(Object o);


    // Bulk Operations

    /**
     * 如果当前集合包含指定集合的全部元素，则会返回正确；
     */
    boolean containsAll(Collection<?> c);

    /**
     * 从一个集合中添加所有元素到另外一个集合
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * 这个调用返回后，
     * 此集合将不包含与指定集合的元素相同的元素
     */
    boolean removeAll(Collection<?> c);

    /**
     * 移除满足Predicate过滤条件的元素
     *
     * @since 1.8
     */
    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * @param c 包含要保留在此集合中的元素的集合
     * @return {@code true} if this collection changed as a result of the call
     * @throws UnsupportedOperationException if the {@code retainAll} operation
     *                                       is not supported by this collection
     * @see #remove(Object)
     * @see #contains(Object)
     */
    boolean retainAll(Collection<?> c);

    /**
     * 从该集合中删除所有元素(可选操作)。
     * 该方法返回后，集合将为空。
     *
     * @throws UnsupportedOperationException 如果{@code clear}操作
     *                                       不受此集合支持
     */
    void clear();


    // Comparison and hashing

    /**
     * 将指定的对象与此集合进行相等性比较。 <p>
     *
     * @return {@code true} if the specified object is equal to this
     * collection
     * @see Object#equals(Object)
     * @see Set#equals(Object)
     * @see List#equals(Object)
     */
    boolean equals(Object o);

    /**
     * @return 此集合的哈希码值
     * @see Object#hashCode()
     * @see Object#equals(Object)
     */
    int hashCode();

    /**
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    /**
     * 返回一个序列{@code Stream}，该集合作为其源。
     *
     * @since 1.8
     */
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * 返回一个可能并行的{@code Stream}，该集合作为它的
     * 来源。该方法允许返回顺序流。
     *
     * @since 1.8
     */
    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}
