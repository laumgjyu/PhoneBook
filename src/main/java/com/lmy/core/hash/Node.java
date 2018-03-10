package com.lmy.core.hash;

import java.util.Objects;

public class Node<K, V> {
    final int hash;

    final K key;

    V value;

    int searchLength; //查找长度

    Node<K, V> next;

    public Node(int hash, K key, V value, Node<K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
        this.searchLength = 0;
    }

    public int getSearchLength() {
        return searchLength;
    }

    public void setSearchLength(int searchLength) {
        this.searchLength = searchLength;
    }

    public Node(int hash, K key, V value) {
        this(hash, key, value, null);
    }

    public final Node<K, V> getNext() {
        return next;
    }

    public final void setNext(Node<K, V> next) {
        this.next = next;
    }

    public final int getHash() {
        return hash;
    }

    public final K getKey() {
        return key;
    }

    public final V getValue() {
        return value;
    }

    public final V setValue(V newValue) {
        V oldValue = this.value;
        this.value = newValue;
        return oldValue;
    }

    public final int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    public final String toString() {
        return key + "=" + value;
    }

    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof Node) {
            Node<?, ?> node = (Node<?, ?>) other;
            if (Objects.equals(key, node.getKey()) && Objects.equals(value, node.getValue())) {
                return true;
            }
        }

        return false;
    }
}
