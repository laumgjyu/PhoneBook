package com.lmy.core.hash;

/**
 * Created by lmy on 2018/3/10.
 */
/*
处理冲突的方法：线性开型寻址法
 */
public class HashTable<K, V> {

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; //16, 使用二进制操作效率更高

    static final int MAXIMUM_CAPACITY = 1 << 30;  //数组的最大容量

    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    final float loadFactor; //负载因子,负载因子小的表冲突的可能性小，插入和查找的速度也相对较快（但会减慢使用迭代器进行遍历的过程）。HashMap和HashSet都具有允许你指定负载因子的构造器，表示当负载情况达到负载因子的水平时，容器将自动增加容量。实现方法是使容量加倍，并将现有对象分配到新的桶位集中。
    Node<K, V>[] table;
    int size; //Map的大小
    int threshold; //调整大小时的下一个大小值

    public HashTable(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    public HashTable(int initialCpacity) {
        this(initialCpacity, DEFAULT_LOAD_FACTOR);
    }

    public HashTable() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * 找出大于给定的值cap的最小的2的次方数
     *
     * @param cap
     * @return
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public V put(K key, V value) {
        return putVal(hash(key), key, value);
    }

    final V putVal(int hash, K key, V value) {
        Node<K, V>[] tab;
        int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((tab[i = (n - 1) & hash]) == null)
            tab[i] = new Node<K, V>(hash, key, value, null);
        else {
            i = (n - 1) & hash + 1;
            while (true) {
                if ((tab[i]) == null) {
                    tab[i] = new Node<K, V>(hash, key, value, null);
                    break;
                }
                if (i++ >= tab.length) {
                    i = 0;
                }
            }

        }
        if (++size >= threshold)
            resize();
        return null;
    }

    final Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        } else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ?
                    (int) ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes", "unchecked"})
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                if (oldTab[j] != null) {
                    this.put(oldTab[j].key, oldTab[j].value);
                }
            }
        }
        return newTab;
    }

    public V get(Object key) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    public Node<K, V> getNode(Object key) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e;
    }

    final Node<K, V> getNode(int hash, Object key) {
        Node<K, V>[] tab;
        Node<K, V> first;
        int n, i, searchCount = 0;
        K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && // always check first node
                    ((k = first.key) == key || (key != null && key.equals(k)))) {
                first.searchLength = 1;  //记录查找长度
                return first;
            } else {
                i = (n - 1) & hash + 1;
                searchCount = 1;
                while (true) {
                    searchCount++;

                    if (tab[i] != null && tab[i].hash != first.hash) {
                        if (tab[i].hash == hash && // always check first node
                                ((k = tab[i].key) == key || (key != null && key.equals(k)))) {
                            tab[i].searchLength = searchCount;
                            return tab[i];
                        }
                    } else {
                        return null;
                    }
                    if (i++ >= tab.length) {
                        i = 0;
                    }
                }
            }
        }

        return null;
    }
}

