package nowcoder;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 实现hashMap
 */
public class MyHashMap<K, V> {
    // 默认初始为16
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    //最大为2^29
    static final int MAXIMUM_CAPACITY = 1 << 30;
    // 加载因子
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    // 数组
    Node<K, V>[] table;
    // entry的个数
    int count;

    public Set<Node<K, V>> entrySet() {
        return null;
    }

    // 链表
    static class Node<K, V> {
        final int hash;
        K k;
        V v;
        Node<K, V> next;

        public Node(int hash, K k, V v, Node<K, V> next) {
            this.hash = hash;
            this.k = k;
            this.v = v;
            this.next = next;
        }

        public final K getKey() {
            return k;
        }

        public final V getValue() {
            return v;
        }

        public final V setValue(V value) {
            V temp = v;
            v = value;
            return temp;
        }

        public final String toString() {
            return k + "=" + v;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                if (Objects.equals(k, e.getKey()) &&
                        Objects.equals(v, e.getValue()))
                    return true;
            }
            return false;
        }

        public final int hashCode() {
            return Objects.hashCode(k) ^ Objects.hashCode(v);
        }
    }

    public V put(K k, V v) {
        int hash = Objects.hash(k) ^ Objects.hash(v);
        Node<K, V> node = new Node<>(hash, k, v, null);
        int hashIndex = hash(k);
        if (table == null) {
            table = new Node[DEFAULT_INITIAL_CAPACITY];
        }
        Node<K, V> head = table[hashIndex];
        if (count > DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR) {
            resize();//扩容
        }
        if (head == null) {
            table[hashIndex] = node;
            count++;
            return v;
        } else {
            while (head != null) {
                if (node.hash == head.hash && (head.k == node.k || head.k.equals(node.k))) {
                    V oldV = head.v;
                    head.setValue(v);
                    return oldV;
                }
                head = head.next;
            }
            Node<K, V> temp = table[hashIndex];
            table[hashIndex] = node;
            node.next = temp;
            return v;
        }
    }

    public int resize() {
        int newCapacity = DEFAULT_INITIAL_CAPACITY << 2;
        Node[] newTable = new Node[newCapacity];
        System.arraycopy(table, 0, newTable, 0, table.length);
        this.table = newTable;
        return newCapacity;
    }

    public V get(K key) {
        Node<K, V> entry;
        return (entry = getEntry(hash(key), key)) == null ? null : entry.v;
    }

    public Node<K, V> getEntry(int hash, K key) {
        Node<K, V> entry = table[hash];
        if (entry == null) {
            return null;
        } else if (entry != null && entry.next == null) {
            return entry;
        } else if (entry.next != null) {
            do {
                if (hash == hash(entry.k) &&
                        (key == entry.k || (key != null && key.equals(entry.k)))) {
                    return entry;
                }
            } while ((entry = entry.next) != null);
            return entry;
        }
        return null;
    }

    public final int hash(K key) {
        //key.hashCode可能产生负值，执行一次key.hashCode()& 0x7FFFFFFF操作，
        //变为整数，这里产生hash值直接模4， 保证产生的hash值不会因扩容而产生变化
        return (key == null) ? 0 : (key.hashCode() & 0x7FFFFFFF % DEFAULT_INITIAL_CAPACITY);
    }


    public static void main(String[] args) {
        MyHashMap<String, String> hashMap = new MyHashMap();
        hashMap.put("wujie", "wujie");
        hashMap.put("wujie", "wujie1");
        System.out.println(hashMap.get("wujie"));
    }
}
