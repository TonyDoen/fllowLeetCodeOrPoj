package me.meet.labuladong._2;

import me.meet.labuladong.common.TreeNode;

import java.util.LinkedList;

public final class LC0297 {
    private LC0297() {
    }
    /*
     * JSON 的运用非常广泛，比如我们经常将变成语言中的结构体序列化成 JSON 字符串，存入缓存或者通过网络发送给远端服务，消费者接受 JSON 字符串然后进行反序列化，就可以得到原始数据了。
     * 这就是「序列化」和「反序列化」的目的，以某种固定格式组织字符串，使得数据可以独立于编程语言。
     *
     * 那么假设现在有一棵用 Java 实现的二叉树，我想把它序列化字符串，然后用 C++ 读取这棵并还原这棵二叉树的结构，怎么办？这就需要对二叉树进行「序列化」和「反序列化」了。
     */

    /**
     * 一、题目描述
     * 力扣第 297 题「二叉树的序列化与反序列化」就是给你输入一棵二叉树的根节点 root，要求你实现如下一个类：
     * public class Codec {
     *
     *     // 把一棵二叉树序列化成字符串
     *     public String serialize(TreeNode root) {}
     *
     *     // 把字符串反序列化成二叉树
     *     public TreeNode deserialize(String data) {}
     * }
     *
     * 我们可以用 serialize 方法将二叉树序列化成字符串，用 deserialize 方法将序列化的字符串反序列化成二叉树，至于以什么格式序列化和反序列化，这个完全由你决定。
     * 比如说输入如下这样一棵二叉树：
     *          1
     *        /   \
     *      2      3
     *       \
     *        4
     *
     * serialize 方法也许会把它序列化成字符串 2,1,#,6,3,#,#，其中 # 表示 null 指针，那么把这个字符串再输入 deserialize 方法，依然可以还原出这棵二叉树。
     * 也就是说，这两个方法会成对儿使用，你只要保证他俩能够自洽就行了。
     * 想象一下，二叉树结该是一个二维平面内的结构，而序列化出来的字符串是一个线性的一维结构。所谓的序列化不过就是把结构化的数据「打平」，其实就是在考察二叉树的遍历方式。
     * 二叉树的遍历方式有哪些？递归遍历方式有前序遍历，中序遍历，后序遍历；迭代方式一般是层级遍历。本文就把这些方式都尝试一遍，来实现 serialize 方法和 deserialize 方法。
     *
     *
     *
     * 二、前序遍历解法
     * 前文 学习数据结构和算法的框架思维 说过了二叉树的几种遍历方式，前序遍历框架如下：
     * void traverse(TreeNode root) {
     *     if (root == null) return;
     *
     *     // 前序遍历的代码
     *
     *     traverse(root.left);
     *     traverse(root.right);
     * }
     *
     * 真的很简单，在递归遍历两棵子树之前写的代码就是前序遍历代码，那么请你看一看如下伪码：
     *
     *
     *
     */
    // 代表分隔符的字符
    private static final String SEP = ",";
    // 代表 null 空指针的字符
    private static final String NULL = "#";

    /* 主函数，将二叉树序列化为字符串 */
    static String preOrderSerialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        preOrderSerialize(root, sb);
        return sb.toString();
    }

    /* 辅助函数，将二叉树存入 StringBuilder */
    private static void preOrderSerialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(SEP);
            return;
        }

        // 前序遍历位置
        sb.append(root.getVal()).append(SEP);
        // ==========

        preOrderSerialize(root.getLeft(), sb);
        preOrderSerialize(root.getRight(), sb);
    }

    /**
     * 可以用于高效拼接字符串，所以也可以认为是一个列表，用 , 作为分隔符，用 # 表示空指针 null，调用完 traverse 函数后，StringBuilder 中的字符串应该是 1,2,#,4,#,#,3,#,#,。
     * 至此，我们已经可以写出序列化函数 serialize 的代码.
     *
     * 现在，思考一下如何写 deserialize 函数，将字符串反过来构造二叉树。
     * 首先我们可以把字符串转化成列表：
     * String data = "1,2,#,4,#,#,3,#,#,";
     * String[] nodes = data.split(",");
     *
     * 这样，nodes 列表就是二叉树的前序遍历结果，问题转化为：如何通过二叉树的前序遍历结果还原一棵二叉树？
     *
     * PS：
     * 一般语境下，单单前序遍历结果是不能还原二叉树结构的，因为缺少空指针的信息，至少要得到前、中、后序遍历中的两种才能还原二叉树。但是这里的 node 列表包含空指针的信息，所以只使用 node 列表就可以还原二叉树。
     * 根据我们刚才的分析，nodes 列表就是一棵打平的二叉树：
     *          1                        preOrder:
     *        /   \
     *      2      3                                 root.left         root.right
     *    /  \    /  \                   root ┌───────────────────┐    ┌─────────┐
     *   #    4  #    #                  1    2    #    4    #    #    3    #    #
     *       / \
     *      #   #
     *
     * 那么，反序列化过程也是一样，先确定根节点 root，然后遵循前序遍历的规则，递归生成左右子树即可：
     *
     *
     * 我们发现，根据树的递归性质，nodes 列表的第一个元素就是一棵树的根节点，所以只要将列表的第一个元素取出作为根节点，剩下的交给递归函数去解决即可。
     */
    /* 主函数，将字符串反序列化为二叉树结构 */
    static TreeNode preOrderDeserialize(String data) {
        // 将字符串转化成列表
        LinkedList<String> nodes = new LinkedList<>();
        for (String s : data.split(SEP)) {
            nodes.addLast(s);
        }
        return preOrderDeserialize(nodes);
    }

    /* 辅助函数，通过 nodes 列表构造二叉树 */
    private static TreeNode preOrderDeserialize(LinkedList<String> nodes) {
        if (nodes.isEmpty()) return null;

        // 前序遍历位置
        // 列表最左侧就是根节点
        String first = nodes.removeFirst();
        if (first.equals(NULL)) return null;
        TreeNode root = new TreeNode(Integer.parseInt(first));
        // =================

        root.setLeft(preOrderDeserialize(nodes));
        root.setRight(preOrderDeserialize(nodes));

        return root;
    }

    private static void testPreOrderSerializeAndDeserialize() {
        TreeNode root = TreeNode.prepareTree2();
        root.print();

        String rs = preOrderSerialize(root);
        System.out.println(rs);

        TreeNode tRs = preOrderDeserialize(rs);
        tRs.print();
    }

    /**
     * 三、后序遍历解法
     *
     * 二叉树的后续遍历框架：
     * void traverse(TreeNode root) {
     *     if (root == null) return;
     *     traverse(root.left);
     *     traverse(root.right);
     *
     *     // 后序遍历的代码
     * }
     *
     * 明白了前序遍历的解法，后序遍历就比较容易理解了，我们首先实现 serialize 序列化方法，只需要稍微修改辅助方法即可：
     *          1                        postOrder:
     *        /   \
     *      2      3                            root.left         root.right
     *    /  \    /  \                   ┌───────────────────┐    ┌─────────┐    root
     *   #    4  #    #                  #    #    #    4    2    #    #    3    1
     *       / \
     *      #   #
     *
     */
    /* 主函数，将二叉树序列化为字符串 */
    static String postOrderSerialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        postOrderSerialize(root, sb);
        return sb.toString();
    }

    /* 辅助函数，将二叉树存入 StringBuilder */
    private static void postOrderSerialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(SEP);
            return;
        }

        postOrderSerialize(root.getLeft(), sb);
        postOrderSerialize(root.getRight(), sb);

        // 后序遍历位置
        sb.append(root.getVal()).append(SEP);
        // ==========
    }

    /**
     * 关键的难点在于，如何实现后序遍历的 deserialize 方法呢？
     * 是不是也简单地将关键代码放到后序遍历的位置就行了呢：
     * TreeNode deserialize(LinkedList<String> nodes) {
     *     if (nodes.isEmpty()) return null;
     *
     *     root.left = deserialize(nodes);   // error about [root]
     *     root.right = deserialize(nodes);
     *
     *     // 后序遍历位置
     *     String first = nodes.removeFirst();
     *     if (first.equals(NULL)) return null;
     *     TreeNode root = new TreeNode(Integer.parseInt(first));
     *     // ==========
     *
     *     return root;
     * }
     *
     *
     *
     * 没这么简单，显然上述代码是错误的，变量都没声明呢，就开始用了？
     * 生搬硬套肯定是行不通的，回想刚才我们前序遍历方法中的 deserialize 方法，第一件事情在做什么？
     *
     * deserialize 方法首先寻找 root 节点的值，然后递归计算左右子节点。
     * 那么我们这里也应该顺着这个基本思路走，后续遍历中，root 节点的值能不能找到？
     *          1                        postOrder:
     *        /   \
     *      2      3                            root.left         root.right
     *    /  \    /  \                   ┌───────────────────┐    ┌─────────┐    root
     *   #    4  #    #                  #    #    #    4    2    #    #    3    1
     *       / \
     *      #   #
     *
     * 可见，root 的值是列表的最后一个元素。我们应该从后往前取出列表元素，先用最后一个元素构造 root，然后递归调用生成 root 的左右子树。
     * 注意，根据上图，从后往前在 nodes 列表中取元素，一定要先构造 root.right 子树，后构造 root.left 子树。
     *
     */
    /* 主函数，将字符串反序列化为二叉树结构 */
    static TreeNode postOrderDeserialize(String data) {
        LinkedList<String> nodes = new LinkedList<>();
        for (String s : data.split(SEP)) {
            nodes.addLast(s);
        }
        return postOrderDeserialize(nodes);
    }

    /* 辅助函数，通过 nodes 列表构造二叉树 */
    private static TreeNode postOrderDeserialize(LinkedList<String> nodes) {
        if (nodes.isEmpty()) return null;

        // 前序遍历位置? 类似？
        // 从后往前取出元素
        String last = nodes.removeLast();
        if (last.equals(NULL)) return null;
        TreeNode root = new TreeNode(Integer.parseInt(last));
        // =================

        // 限构造右子树，后构造左子树
        root.setRight(postOrderDeserialize(nodes)); // careful
        root.setLeft(postOrderDeserialize(nodes));  // careful

        return root;
    }

    private static void testPostOrderSerializeAndDeserialize() {
        TreeNode root = TreeNode.prepareTree2();
        root.print();

        String rs = postOrderSerialize(root);
        System.out.println(rs);

        TreeNode tRs = postOrderDeserialize(rs);
        tRs.print();
    }

    /**
     * 四、中序遍历解法
     * 先说结论，中序遍历的方式行不通，因为无法实现反序列化方法 deserialize。
     *
     * 序列化方法 serialize 依然容易，只要把字符串的拼接操作放到中序遍历的位置就行了：
     *
     *
     *
     * 但是，我们刚才说了，要想实现反序列方法，首先要构造 root 节点。
     * 前序遍历得到的 nodes 列表中，第一个元素是 root 节点的值；
     * 后序遍历得到的 nodes 列表中，最后一个元素是 root 节点的值。
     *
     * 你看上面这段中序遍历的代码，root 的值被夹在两棵子树的中间，
     * 也就是在 nodes 列表的中间，我们不知道确切的索引位置，
     * 所以无法找到 root 节点，也就无法进行反序列化。
     *
     */


    /**
     * 五、层级遍历解法
     * 首先，先写出层级遍历二叉树的代码框架：
     * void traverse(TreeNode root) {
     *     if (root == null) return;
     *     // 初始化队列，将 root 加入队列
     *     Queue<TreeNode> q = new LinkedList<>();
     *     q.offer(root);
     *
     *     while (!q.isEmpty()) {
     *         TreeNode cur = q.poll();
     *
     *         // 层级遍历代码位置
     *         System.out.println(root.val);
     *         // =============
     *
     *         if (cur.left != null) {
     *             q.offer(cur.left);
     *         }
     *         if (cur.right != null) {
     *             q.offer(cur.right);
     *         }
     *     }
     * }
     *
     * 上述代码是标准的二叉树层级遍历框架，从上到下，从左到右打印每一层二叉树节点的值，可以看到，队列 q 中不会存在 null 指针。
     * 不过我们在反序列化的过程中是需要记录空指针 null 的，所以可以把标准的层级遍历框架略作修改：
     * 这样也可以完成层级遍历，只不过我们把对空指针的检验从「将元素加入队列」的时候改成了「从队列取出元素」的时候。
     * 那么我们完全仿照这个框架即可写出序列化方法：
     *
     * 层级遍历序列化得出的结果如下图：
     *          1                        levelOrder:
     *        /   \
     *      2      3                     lv1   lv2            lv3            lv4
     *    /  \    /  \                   ┌┐   ┌────┐    ┌──────────────┐    ┌────┐
     *   #    4  #    #                  1    2    3    #    4    #    #    #    #
     *       / \
     *      #   #
     *
     *
     */
    /* 将二叉树序列化为字符串 */
    static String levelSerialize(TreeNode root) {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        // 初始化队列，将 root 加入队列
        LinkedList<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            TreeNode cur = q.poll();

            // 层级遍历代码位置
            if (cur == null) {
                sb.append(NULL).append(SEP);
                continue;
            }
            sb.append(cur.getVal()).append(SEP);
            // =============

            q.offer(cur.getLeft());
            q.offer(cur.getRight());
        }

        return sb.toString();
    }

    /* 将字符串反序列化为二叉树结构 */
    static TreeNode levelDeserialize(String data) {
        if (data.isEmpty()) return null;
        String[] nodes = data.split(SEP);
        // 第一个元素就是 root 的值
        TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));

        // 队列 q 记录父节点，将 root 加入队列
        LinkedList<TreeNode> q = new LinkedList<>();
        q.offer(root);

        for (int i = 1; i < nodes.length; ) {
            // 队列中存的都是父节点
            TreeNode parent = q.poll();
            // 父节点对应的左侧子节点的值
            String left = nodes[i++];
            if (!left.equals(NULL)) {
                parent.setLeft(new TreeNode(Integer.parseInt(left)));
                q.offer(parent.getLeft());
            } else {
                parent.setLeft(null);
            }
            // 父节点对应的右侧子节点的值
            String right = nodes[i++];
            if (!right.equals(NULL)) {
                parent.setRight(new TreeNode(Integer.parseInt(right)));
                q.offer(parent.getRight());
            } else {
                parent.setRight(null);
            }
        }
        return root;
    }

    private static void testLevelOrderSerializeAndDeserialize() {
        TreeNode root = TreeNode.prepareTree2();
        root.print();

        String rs = levelSerialize(root);
        System.out.println(rs);

        TreeNode tRs = levelDeserialize(rs);
        tRs.print();
    }

    public static void main(String[] args) {
        // 前序遍历 序列化反序列化 Tree
        testPreOrderSerializeAndDeserialize();
        // 后序遍历 序列化反序列化 Tree
        testPostOrderSerializeAndDeserialize();
        // 中序遍历 序列化反序列化 Tree cannot

        // 层级遍历 序列化反序列化 Tree
        testLevelOrderSerializeAndDeserialize();

    }
}
