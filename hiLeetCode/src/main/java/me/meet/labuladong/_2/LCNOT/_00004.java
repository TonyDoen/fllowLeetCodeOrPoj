package me.meet.labuladong._2.LCNOT;

public final class _00004 {
    private _00004() {
    }
    /*
     * 今天讲讲 Union-Find 算法，也就是常说的并查集算法，主要是解决图论中「动态连通性」问题的。
     * 名词很高端，其实特别好理解，等会解释，另外这个算法的应用都非常有趣。
     *
     * 说起这个 Union-Find，应该算是我的「启蒙算法」了，因为《算法4》的开头就介绍了这款算法，可是把我秀翻了，感觉好精妙啊！
     * 后来刷了 LeetCode，并查集相关的算法题目都非常有意思，而且《算法4》给的解法竟然还可以进一步优化，只要加一个微小的修改就可以把时间复杂度降到 O(1)。
     *
     *
     * 一、问题介绍
     * 简单说，动态连通性其实可以抽象成给一幅图连线。比如下面这幅图，总共有 10 个节点，他们互不相连，分别用 0~9 标记：
     *              4         5
     *      0                           9
     *              3         6
     *      1                           8
     *              2         7
     *
     * 现在我们的 Union-Find 算法主要需要实现这两个 API：
     * class UF {
     *     public void union(int p, int q);         // 将 p 和 q 连接
     *     public boolean connected(int p, int q);  // 判断 p 和 q 是否连通
     *     public int count();                      // 返回图中有多少个连通分量
     * }
     *
     * 这里所说的「连通」是一种等价关系，也就是说具有如下三个性质：
     * 1、自反性：节点p和p是连通的。
     * 2、对称性：如果节点p和q连通，那么q和p也连通。
     * 3、传递性：如果节点p和q连通，q和r连通，那么p和r也连通。
     *
     * 比如说之前那幅图，0～9 任意两个不同的点都不连通，调用connected都会返回 false，连通分量为 10 个。
     * 如果现在调用union(0, 1)，那么 0 和 1 被连通，连通分量降为 9 个。
     * 再调用union(1, 2)，这时 0,1,2 都被连通，调用connected(0, 2)也会返回 true，连通分量变为 8 个。
     *
     *
     * 判断这种「等价关系」非常实用，比如说编译器判断同一个变量的不同引用，比如社交网络中的朋友圈计算等等。
     * 这样，你应该大概明白什么是动态连通性了，Union-Find 算法的关键就在于union和connected函数的效率。
     * 那么用什么模型来表示这幅图的连通状态呢？用什么数据结构来实现代码呢？
     *
     *
     *
     */
    /**
     * 二、基本思路
     * 注意我刚才把「模型」和具体的「数据结构」分开说，这么做是有原因的。
     * 因为我们使用森林（若干棵树）来表示图的动态连通性，用数组来具体实现这个森林。
     * 怎么用森林来表示连通性呢？我们设定树的每个节点有一个指针指向其父节点，如果是根节点的话，这个指针指向自己。
     *
     * 比如说刚才那幅 10 个节点的图，一开始的时候没有相互连通，就是这样：
     * class UF {
     *     // 记录连通分量
     *     private int count;
     *     // 节点 x 的节点是 parent[x]
     *     private int[] parent;
     *
     *     // 构造函数，n 为图的节点总数
     *     public UF(int n) {
     *         // 一开始互不连通
     *         this.count = n;
     *         // 父节点指针初始指向自己
     *         parent = new int[n];
     *         for (int i = 0; i < n; i++)
     *             parent[i] = i;
     *     }
     *
     *     // 其他函数
     * }
     *
     * ^自旋      ^自旋
     * 0         1      ......
     *
     * 如果某两个节点被连通，则让其中的（任意）一个节点的根节点接到另一个节点的根节点上：
     * public void union(int p, int q) {
     *     int rootP = find(p);
     *     int rootQ = find(q);
     *     if (rootP == rootQ)
     *         return;
     *     // 将两棵树合并为一棵
     *     parent[rootP] = rootQ;
     *     // parent[rootQ] = rootP 也一样
     *     count--; // 两个分量合二为一
     * }
     *
     * // 返回某个节点 x 的根节点
     * private int find(int x) {
     *     // 根节点的 parent[x] == x
     *     while (parent[x] != x)
     *         x = parent[x];
     *     return x;
     * }
     *
     * // 返回当前的连通分量个数
     * public int count() {
     *     return count;
     * }
     *
     *             rootP != rootQ                                  rootP = rootQ
     *
     *                                        union(1, 5)
     *       ^自旋              ^自旋          ============>             ^自旋
     *       0  rootP          2  rootQ                                 2  rootQ
     *     / | \                 \                                    /   \
     *   1   6   3                5                                 0       5
     *   p                        q                               / | \     q
     *                                                          1   6   3
     *                                                          p
     *
     * 这样，如果节点p和q连通的话，它们一定拥有相同的根节点：
     * public boolean connected(int p, int q) {
     *     int rootP = find(p);
     *     int rootQ = find(q);
     *     return rootP == rootQ;
     * }
     *
     * 至此，Union-Find 算法就基本完成了。是不是很神奇？竟然可以这样使用数组来模拟出一个森林，如此巧妙的解决这个比较复杂的问题！
     * 那么这个算法的复杂度是多少呢？我们发现，主要 APIconnected和union中的复杂度都是find函数造成的，所以说它们的复杂度和find一样。
     *
     * find主要功能就是从某个节点向上遍历到树根，其时间复杂度就是树的高度。我们可能习惯性地认为树的高度就是logN，但这并不一定。
     * logN的高度只存在于平衡二叉树，对于一般的树可能出现极端不平衡的情况，使得「树」几乎退化成「链表」，树的高度最坏情况下可能变成N。
     *
     * 所以说上面这种解法，find,union,connected的时间复杂度都是 O(N)。
     * 这个复杂度很不理想的，你想图论解决的都是诸如社交网络这样数据规模巨大的问题，对于union和connected的调用非常频繁，每次调用需要线性时间完全不可忍受。
     *
     * 问题的关键在于，如何想办法避免树的不平衡呢？只需要略施小计即可。
     *
     */
    /**
     * 三、平衡性优化
     *
     * 我们要知道哪种情况下可能出现不平衡现象，关键在于union过程：
     * public void union(int p, int q) {
     *     int rootP = find(p);
     *     int rootQ = find(q);
     *     if (rootP == rootQ)
     *         return;
     *     // 将两棵树合并为一棵
     *     parent[rootP] = rootQ;
     *     // parent[rootQ] = rootP 也可以
     *     count--;
     *
     *     ......
     *
     * 我们一开始就是简单粗暴的把p所在的树接到q所在的树的根节点下面，那么这里就可能出现「头重脚轻」的不平衡状况，比如下面这种局面:
     *
     *      ^自旋             ^自旋           union(0, 2)             ^自旋                     Good Union
     *      1  rootP         2              ============>            1  rootP
     *    /                  q                                     /   \
     *  0                                                        0       2
     *  p                                                        p       q
     *
     *                                                               ^自旋                     Bad Union
     *                                                               2
     *                                                             / q
     *                                                           1
     *                                                         /
     *                                                       0
     *                                                       p
     *
     * 长此以往，树可能生长得很不平衡。我们其实是希望，小一些的树接到大一些的树下面，这样就能避免头重脚轻，更平衡一些。
     * 解决方法是额外使用一个size数组，记录每棵树包含的节点数，我们不妨称为「重量」：
     * class UF {
     *     private int count;
     *     private int[] parent;
     *     // 新增一个数组记录树的“重量”
     *     private int[] size;
     *
     *     public UF(int n) {
     *         this.count = n;
     *         parent = new int[n];
     *         // 最初每棵树只有一个节点
     *         // 重量应该初始化 1
     *         size = new int[n];
     *         for (int i = 0; i < n; i++) {
     *             parent[i] = i;
     *             size[i] = 1;
     *         }
     *     }
     *     // 其他函数
     * }
     *
     * 比如说size[3] = 5表示，以节点3为根的那棵树，总共有5个节点。这样我们可以修改一下union方法：
     * public void union(int p, int q) {
     *     int rootP = find(p);
     *     int rootQ = find(q);
     *     if (rootP == rootQ)
     *         return;
     *
     *     // 小树接到大树下面，较平衡
     *     if (size[rootP] > size[rootQ]) {
     *         parent[rootQ] = rootP;
     *         size[rootP] += size[rootQ];
     *     } else {
     *         parent[rootP] = rootQ;
     *         size[rootQ] += size[rootP];
     *     }
     *     count--;
     * }
     *
     * 这样，通过比较树的重量，就可以保证树的生长相对平衡，树的高度大致在logN这个数量级，极大提升执行效率。
     * 此时，find,union,connected的时间复杂度都下降为 O(logN)，即便数据规模上亿，所需时间也非常少。
     *
     *
     *
     *
     * 四、路径压缩
     * 这步优化特别简单，所以非常巧妙。我们能不能进一步压缩每棵树的高度，使树高始终保持为常数？
     * 这样find就能以 O(1) 的时间找到某一节点的根节点，相应的，connected和union复杂度都下降为 O(1)。
     *
     * 要做到这一点，非常简单，只需要在find中加一行代码：
     * private int find(int x) {
     *     while (parent[x] != x) {
     *         // 进行路径压缩
     *         parent[x] = parent[parent[x]];
     *         x = parent[x];
     *     }
     *     return x;
     * }
     *
     * 这个操作有点匪夷所思，看个 GIF 就明白它的作用了（为清晰起见，这棵树比较极端）：
     * 0.0     ============>     1.0                 ============>            1.1                  ============>       2.0
     *   ^自旋                      ^自旋                                        ^自旋                                     ^自旋
     *   0                         0                                            0  parent[parent[x]]                     0  parent[x] = parent[parent[x]];
     *   |                         |                                            |                                      /   \
     *   1                         1                                            1  parent[x]                         2      1
     *   |                         |                                            |                                  /   \
     *   2  parent[parent[x]]      2  parent[x] = parent[parent[x]];            2  x                             4      3
     *   |                       /   \                                        /   \
     *   3  parent[x]          4      3                                     4      3
     *   |
     *   4  x
     *
     * 可见，调用find函数每次向树根遍历的同时，顺手将树高缩短了，最终所有树高都不会超过 3（union的时候树高可能达到 3）。
     *
     * PS：读者可能会问，这个 GIF 图的find过程完成之后，树高恰好等于 3 了，但是如果更高的树，压缩后高度依然会大于 3 呀？
     * 不能这么想。这个 GIF 的情景是我编出来方便大家理解路径压缩的，但是实际中，每次find都会进行路径压缩，
     * 所以树本来就不可能增长到这么高，你的这种担心应该是多余的。
     *
     *
     * Union-Find 算法的复杂度可以这样分析：
     * 构造函数初始化数据结构需要 O(N) 的时间和空间复杂度；
     * 连通两个节点union、判断两个节点的连通性connected、计算连通分量count所需的时间复杂度均为 O(1)。
     */
    public static class UnionFind {
        // 连通分量个数
        private int count;
        // 存储一棵树
        private int[] parent;
        // 记录树的“重量”
        private int[] size;

        public UnionFind(int n) {
            this.count = n;
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i; // ^自旋: 自己就是自己的父亲
                size[i] = 1;
            }
        }

        // 将 p 和 q 连接
        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ)
                return;

            // 小树接到大树下面，较平衡
            if (size[rootP] > size[rootQ]) {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            } else {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            }
            count--;
        }

        // 判断 p 和 q 是否连通
        public boolean connected(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            return rootP == rootQ;
        }

        // 返回图中有多少个连通分量
        public int count() {
            return this.count;
        }

        private int find(int x) {
            while (parent[x] != x) {
                // 进行路径压缩
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }
    }

    private static void testUnionFind0() {
        int n = 5;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n-3; i = i+1) {
            uf.union(i, i+3);
        }

        boolean tf = uf.connected(0, 3);
        System.out.println(tf);

        int rs = uf.count();
        System.out.println(rs);

    }

    public static void main(String[] args) {
        testUnionFind0();
    }
}
