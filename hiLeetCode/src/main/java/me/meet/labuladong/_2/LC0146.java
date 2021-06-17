package me.meet.labuladong._2;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class LC0146 {
    private LC0146() {
    }

    /*
     * LRU 算法就是一种缓存淘汰策略，原理不难，但是面试中写出没有 bug 的算法比较有技巧，需要对数据结构进行层层抽象和拆解，
     *
     * 计算机的缓存容量有限，如果缓存满了就要删除一些内容，给新内容腾位置。
     * 但问题是，删除哪些内容呢？我们肯定希望删掉哪些没什么用的缓存，而把有用的数据继续留在缓存里，方便之后继续使用。
     * 那么，什么样的数据，我们判定为「有用的」的数据呢？
     *
     * LRU 的全称是 Least Recently Used，也就是说我们认为最近使用过的数据应该是是「有用的」，很久都没用过的数据应该是无用的，内存满了就优先删那些很久没用过的数据。
     *
     * 举个简单的例子，安卓手机都可以把软件放到后台运行，比如我先后打开了「设置」「手机管家」「日历」，那么现在他们在后台排列的顺序是这样的：
     * 但是这时候如果我访问了一下「设置」界面，那么「设置」就会被提前到第一个，变成这样：
     * 假设我的手机只允许我同时开 3 个应用程序，现在已经满了。那么如果我新开了一个应用「时钟」，就必须关闭一个应用为「时钟」腾出一个位置，关那个呢？
     * 按照 LRU 的策略，就关最底下的「手机管家」，因为那是最久未使用的，然后把新开的应用放到最上面：
     *
     *
     * 现在你应该理解 LRU（Least Recently Used）策略了。当然还有其他缓存淘汰策略，比如不要按访问的时序来淘汰，
     * 而是按访问频率（LFU 策略）来淘汰等等，各有应用场景。本文讲解 LRU 算法策略。
     *
     */
    /**
     * url: http://juejin.cn/post/6844903917524893709
     * url: https://colobu.com/2015/09/07/LRU-cache-implemented-by-Java-LinkedHashMap/
     * url: https://www.cnblogs.com/lzrabbit/p/3734850.html
     */

    public static class LRUCache0<K, V> {
        private final LinkedHashMap<K, V> mp;

        public LRUCache0(final int cacheSize) {
            final float DEFAULT_LOAD_FACTOR = 0.75f;
            //根据cacheSize和加载因子计算hashmap的capactiy，+1确保当达到cacheSize上限时不会触发hashmap的扩容，
            int capacity = (int) Math.ceil(cacheSize / DEFAULT_LOAD_FACTOR) + 1;

            mp = new LinkedHashMap<K, V>(capacity, DEFAULT_LOAD_FACTOR, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry eldest) {
                    return size() > cacheSize;
                }
            };
        }

        // 存储 k, v
        public synchronized void put(K k, V v) {
            mp.put(k, v);
        }

        // 获取 v
        public synchronized V get(K k) {
            return mp.get(k);
        }

        // 删除 k
        public synchronized void remove(K k) {
            mp.remove(k);
        }

        //
        public synchronized Set<Map.Entry<K, V>> getAllMapEntry() {
            return mp.entrySet();
        }

        //
        public synchronized int size() {
            return mp.size();
        }

        //
        public synchronized void clear() {
            mp.clear();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (Map.Entry<K, V> entry : mp.entrySet()) {
                sb.append(String.format(" %s -> %s, ", entry.getKey(), entry.getValue()));
            }
            return sb.deleteCharAt(sb.length() - 2).append("]").toString();
        }
    }

    /**
     * 力扣第 146 题「LRU缓存机制」就是让你设计数据结构：
     * 首先要接收一个 capacity 参数作为缓存的最大容量，然后实现两个 API，
     * 一个是 put(key, val) 方法存入键值对，
     * 另一个是 get(key) 方法获取 key 对应的 val，如果 key 不存在则返回 -1。
     *
     * 注意哦，get 和 put 方法必须都是 O(1) 的时间复杂度，我们举个具体例子来看看 LRU 算法怎么工作。
     *
     *
     * 二、LRU 算法设计
     * 分析上面的操作过程，要让 put 和 get 方法的时间复杂度为 O(1)，我们可以总结出 cache 这个数据结构必要的条件：
     * 1、显然 cache 中的元素必须有时序，以区分最近使用的和久未使用的数据，当容量满了之后要删除最久未使用的那个元素腾位置。
     * 2、我们要在 cache 中快速找某个 key 是否已存在并得到对应的 val；
     * 3、每次访问 cache 中的某个 key，需要将这个元素变为最近使用的，也就是说 cache 要支持在任意位置快速插入和删除元素。
     * 那么，什么数据结构同时符合上述条件呢？哈希表查找快，但是数据无固定顺序；链表有顺序之分，插入删除快，但是查找慢。
     * 所以结合一下，形成一种新的数据结构：哈希链表 LinkedHashMap。
     *
     * LRU 缓存算法的核心数据结构就是哈希链表，双向链表和哈希表的结合体。这个数据结构长这样：
     *
     * 哈希表 + 双向链表
     *
     * 借助这个结构，我们来逐一分析上面的 3 个条件：
     * 1、如果我们每次默认从链表尾部添加元素，那么显然越靠尾部的元素就是最近使用的，越靠头部的元素就是最久未使用的。
     * 2、对于某一个 key，我们可以通过哈希表快速定位到链表中的节点，从而取得对应 val。
     * 3、链表显然是支持在任意位置快速插入和删除的，改改指针就行。只不过传统的链表无法按照索引快速访问某一个位置的元素，而这里借助哈希表，
     * 可以通过 key 快速映射到任意一个链表节点，然后进行插入和删除。
     *
     * 也许读者会问，为什么要是双向链表，单链表行不行？另外，既然哈希表中已经存了 key，为什么链表中还要存 key 和 val 呢，只存 val 不就行了？
     * 想的时候都是问题，只有做的时候才有答案。这样设计的原因，必须等我们亲自实现 LRU 算法之后才能理解，所以我们开始看代码吧～
     *
     *
     *
     * 很多编程语言都有内置的哈希链表或者类似 LRU 功能的库函数，但是为了帮大家理解算法的细节，
     * 我们先自己造轮子实现一遍 LRU 算法，然后再使用 Java 内置的 LinkedHashMap 来实现一遍。
     * 首先，我们把双链表的节点类写出来，为了简化，key 和 val 都认为是 int 类型：
     *
     * 到这里就能回答刚才「为什么必须要用双向链表」的问题了，因为我们需要删除操作。删除一个节点不光要得到该节点本身的指针，
     * 也需要操作其前驱节点的指针，而双向链表才能支持直接查找前驱，保证操作的时间复杂度 O(1)。
     * 注意我们实现的双链表 API 只能从尾部插入，也就是说靠尾部的数据是最近使用的，靠头部的数据是最久为使用的。
     * 有了双向链表的实现，我们只需要在 LRU 算法中把它和哈希表结合起来即可，先搭出代码框架：
     *
     * 先不慌去实现 LRU 算法的 get 和 put 方法。由于我们要同时维护一个双链表 cache 和一个哈希表 map，很容易漏掉一些操作，
     * 比如说删除某个 key 时，在 cache 中删除了对应的 Node，但是却忘记在 map 中删除 key。
     * 解决这种问题的有效方法是：在这两种数据结构之上提供一层抽象 API。
     */
    public static class LRUCache1<K, V> {
        static class Node<K, V> { // 双向链表 Node
            K k;
            V v;
            Node<K, V> next, prev;

            Node() {}
            Node(K k, V v) {
                this.k = k;
                this.v = v;
            }
        }

        static class DoubleDirectList<K, V> {
            Node<K, V> head, tail;
            int size;

            DoubleDirectList() {
                // 初始化双向链表的数据
                head = new Node<>();
                tail = new Node<>();
                head.next = tail;
                tail.prev = head;
                size = 0;

            }

            // 在链表尾部添加节点 x，时间 O(1)
            void addLast(Node<K, V> node) {
                node.prev = tail.prev;
                node.next = tail;
                tail.prev.next = node;
                tail.prev = node;
                size++;
            }

            // 删除链表中的 x 节点（x 一定存在）
            // 由于是双链表且给的是目标 Node 节点，时间 O(1)
            void remove(Node<K, V> node) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
            }

            // 删除链表中第一个节点，并返回该节点，时间 O(1)
            Node<K, V> removeFirst() {
                if (tail == head.next) {
                    return null;
                }
                Node<K, V> first = head.next;
                remove(first);
                return first;
            }

            // 返回链表长度，时间 O(1)
            int size() {
                return size;
            }
        }

        // key -> Node(key, val)
        private final HashMap<K, Node<K, V>> mp;
        // Node(k1, v1) <-> Node(k2, v2)...
        private final DoubleDirectList<K, V> lt;
        // 最大容量
        private final int cap;

        public LRUCache1(final int cacheSize) {
            mp = new HashMap<>();
            lt = new DoubleDirectList<>();
            cap = cacheSize;
        }

        public synchronized int size() {
            return mp.size();
        }

        // 存储 k, v
        public synchronized void put(K k, V v) {
            Node<K, V> rs = mp.get(k);
            if (null != rs) {
                lt.remove(rs);
            }

            rs = new Node<>(k, v);
            lt.addLast(rs);
            mp.put(k, rs);

            if (mp.size() > cap) {
                Node<K, V> node = lt.removeFirst(); // remove first
                mp.remove(node.k);                  // remove first key
            }
        }

        // 获取 v
        public synchronized V get(K k) {
            Node<K, V> rs = mp.get(k);
            if (null == rs) {
                return null;
            }

            lt.remove(rs);
            lt.addLast(rs);

            return rs.v;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");

            for (Node<K, V> tmp = lt.head.next; tmp != lt.tail;) {
                sb.append(String.format(" %s -> %s, ", tmp.k, tmp.v));
                tmp = tmp.next;
            }
            return sb.deleteCharAt(sb.length() - 2).append("]").toString();
        }
    }

    static class LRUCache2<K, V> {
        private final LinkedHashMap<K, V> mp = new LinkedHashMap<>();
        private final int capacity;

        public LRUCache2(int capacity) {
            this.capacity = capacity;
        }

        public V get(K k) {
            return kRecent(k);
        }

        public void put(K k, V v) {
            mp.put(k, v);
            kRecent(k);

            if (mp.size() > capacity) {
                K oldestKey = mp.keySet().iterator().next(); // 链表头部就是最久未使用的 key
                mp.remove(oldestKey);
            }
        }

        private V kRecent(K k) {
            V v = mp.get(k);
            mp.remove(k); // 删除 key，重新插入到队尾
            mp.put(k, v);
            return v;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (Map.Entry<K, V> entry : mp.entrySet()) {
                sb.append(String.format(" %s -> %s, ", entry.getKey(), entry.getValue()));
            }
            return sb.deleteCharAt(sb.length() - 2).append("]").toString();
        }
    }

    private static void testLRUCache0() {
//        LRUCache0<Integer, Integer> cache = new LRUCache0<>(5);
//        LRUCache1<Integer, Integer> cache = new LRUCache1<>(5);
        LRUCache2<Integer, Integer> cache = new LRUCache2<>(5);
        for (int i = 0; i < 10; i++) {
            cache.put(i, i);
            cache.put(i, i);
        }
        System.out.println(cache.toString());

        System.out.println(cache.get(9));
        System.out.println(cache.get(8));
        System.out.println(cache.get(7));

        System.out.println(cache.toString());
    }

    public static void main(String[] args) {
        testLRUCache0();
    }
}
