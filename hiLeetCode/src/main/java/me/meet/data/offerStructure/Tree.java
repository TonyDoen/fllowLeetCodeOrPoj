package me.meet.data.offerStructure;

import java.util.*;

public final class Tree {
    private Tree() {
    }

    interface Visit<T> {
        void now(T it);
    }

    static class Node<T extends Comparable<T>> {
        T value;
        Node<T> left;
        Node<T> right;
        Node<T> parent;

        Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 004-重建二叉树
     *
     * 题目描述
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
     *
     * 先来分析一下前序遍历和中序遍历得到的结果，
     * 前序遍历第一位是根节点；
     * 中序遍历中，根节点左边的是根节点的左子树，右边是根节点的右子树。
     * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}。
     *
     * 首先，根节点 是{ 1 }；
     * 左子树是：前序{ 2,4,7 } ，中序{ 4,7,2 }；
     * 右子树是：前序{ 3,5,6,8 } ，中序{ 5,3,8,6 }；
     * 这时，如果我们把左子树和右子树分别作为新的二叉树，则可以求出其根节点，左子树和右子树。
     * 这样，一直用这个方式，就可以实现重建二叉树。
     */
    // 主功能函数
    public static Node reConstructBinaryTree(int[] pre, int[] in) {
        if (pre == null || in == null) {
            return null;
        }
        return reConstructBinaryTreeCore(pre, in, 0, pre.length - 1, 0, in.length - 1);
    }

    // 核心递归
    public static Node reConstructBinaryTreeCore(int[] pre, int[] in, int preStart, int preEnd, int inStart,
                                                 int inEnd) {
        Node tree = new Node(pre[preStart], null, null);
        if (preStart == preEnd && inStart == inEnd) {
            return tree;
        }
        int rootIdx = 0;
        for (rootIdx = inStart; rootIdx < inEnd; rootIdx++) {
            if (pre[preStart] == in[rootIdx]) {
                break;
            }
        }
        int leftLength = rootIdx - inStart;
        int rightLength = inEnd - rootIdx;
        if (leftLength > 0) {
            tree.left = reConstructBinaryTreeCore(pre, in, preStart + 1, preStart + leftLength, inStart, rootIdx - 1);
        }
        if (rightLength > 0) {
            tree.right = reConstructBinaryTreeCore(pre, in, preStart + 1 + leftLength, preEnd, rootIdx + 1, inEnd);
        }
        return tree;
    }

    private static void testReConstructBinaryTree() {
        int[] pre = new int[]{1,2,4,7,3,5,6,8};
        int[] in = new int[]{4,7,2,1,5,3,8,6};
        Node result = reConstructBinaryTree(pre, in);
        printFromTopToBottom(result, (Visit<Node<Integer>>) it -> {
            System.out.println("[root:"+it.value+";left:"+(null == it.left?null:it.left.value)+";right:"+(null == it.right?null:it.right.value)+"]");
        });
    }

    /**
     * 017-树的子结构
     *
     * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
     *
     * 思路1：
     * 1、层次遍历root1, 找到与root2相同的节点(此步骤非递归)
     * 2、找到后判断以此节点为根节点，是否能在root1中找到与root2相同的树结构(此处判断用递归查找)
     */
    static boolean hasSubtree(Node<Integer> n1, Node<Integer> n2) {
        if (null == n1 || null == n2) {
            return false;
        }
        Queue<Node<Integer>> queue = new java.util.LinkedList<>();
        queue.add(n1);

        for (; !queue.isEmpty(); ) {
            for (int size = queue.size(), i = 0; i < size; i++) {
                Node<Integer> cur = queue.poll();
                if (null == cur) {
                    continue;
                }
                if (null != cur.value && cur.value.equals(n2.value) || cur.value == n2.value) {
                    if (isSameTree(cur, n2)) { // 找到了即返回
                        return true;
                    }
                }

                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
        }
        return true;
    }

    static boolean hasSubtree0(Node<Integer> n1, Node<Integer> n2) {
        if (null == n1 || null == n2) {
            return false;
        }
        return isSameTree(n1, n2) || hasSubtree0(n1.left, n2) || hasSubtree0(n2.right, n2);
    }

    private static boolean isSameTree(Node<Integer> n1, Node<Integer> n2) {
        if (null == n1 && null == n2) {
            return true;
        }
        if (null == n1 || null == n2) {
            return false;
        }
        if (null == n1.value && null == n2.value) {
            return true;
        }
        if (null == n1.value || null == n2.value) {
            return false;
        }
        if (!n1.value.equals(n2.value)) {
            return false;
        }

        boolean isLeft = isSameTree(n1.left, n2.left);
        boolean isRight = isSameTree(n1.right, n2.right);
        return isLeft && isRight;
    }

    private static void testHasSubtree() {
        /**
         *      1
         *    /  \
         *   2    3
         *  / \
         * 4   5
         */
        Node<Integer> _5 = new Node<>(5, null, null);
        Node<Integer> _4 = new Node<>(4, null, null);
        Node<Integer> _3 = new Node<>(3, null, null);
        Node<Integer> _2 = new Node<>(2, _4, _5);
        Node<Integer> _1 = new Node<>(1, _2, _3);

        /**
         *   2
         *  / \
         * 4   5
         */
        Node<Integer> _5u = new Node<>(5, null, null);
        Node<Integer> _4u = new Node<>(4, null, null);
        Node<Integer> _2u = new Node<>(2, _4u, _5u);
        boolean res = hasSubtree(_1, _2u);
        System.out.println(res);

        boolean res2 = hasSubtree0(_1, _2u);
        System.out.println(res2);
    }

    /**
     * 018-二叉树的镜像
     *
     * 操作给定的二叉树，将其变换为源二叉树的镜像。
     *
     * 输入描述:
     * 二叉树的镜像定义：
     * 源二叉树
     *     	     8
     *     	   /  \
     *     	  6   10
     *     	 / \  / \
     *     	5  7 9 11
     * 镜像二叉树
     *     	     8
     *     	   /  \
     *     	  10   6
     *     	 / \  / \
     *     	11 9 7  5
     * ————————————————
     *
     * 思路1：
     * 1、交换root节点的左右子树
     * 2、递归交换root.left和root.right的左右子树
     *
     */
    static void mirrorTree(Node node) {
        if (null == node || (null == node.left && null == node.right)) {
            return;
        }

        Node tmp = node.left;
        node.left = node.right;
        node.right = tmp;

        mirrorTree(node.left);
        mirrorTree(node.right);
    }

    static void mirrorTree2(Node node) {
        if (null == node || (null == node.left && null == node.right)) {
            return;
        }
        Queue<Node> queue = new java.util.LinkedList<>();
        queue.add(node);

        for (; !queue.isEmpty();) {
            Node cur = queue.poll();
            if (null == cur) {
                continue;
            }
            if (null != cur.left || null != cur.right) {
                Node tmp = cur.left;
                cur.left = cur.right;
                cur.right = tmp;
            }

            if (null != cur.left) {
                queue.add(cur.left);
            }
            if (null != cur.right) {
                queue.add(cur.right);
            }
        }

    }

    private static void testMirrorTree() {
        /**
         *      1
         *    /  \
         *   2    3
         *  / \
         * 4   5
         */
        Node<Integer> _5 = new Node<>(5, null, null);
        Node<Integer> _4 = new Node<>(4, null, null);
        Node<Integer> _3 = new Node<>(3, null, null);
        Node<Integer> _2 = new Node<>(2, _4, _5);
        Node<Integer> _1 = new Node<>(1, _2, _3);

        printFromTopToBottom(_1, (Visit<Node<Integer>>) it -> {
            System.out.print("[root:" + it.value + ";left:" + (null == it.left ? null : it.left.value) + ";right:" + (null == it.right ? null : it.right.value) + "]");
        });

        System.out.println();
        mirrorTree(_1);
        printFromTopToBottom(_1, (Visit<Node<Integer>>) it -> {
            System.out.print("[root:" + it.value + ";left:" + (null == it.left ? null : it.left.value) + ";right:" + (null == it.right ? null : it.right.value) + "]");
        });

        System.out.println();
        mirrorTree2(_1);
        printFromTopToBottom(_1, (Visit<Node<Integer>>) it -> {
            System.out.print("[root:" + it.value + ";left:" + (null == it.left ? null : it.left.value) + ";right:" + (null == it.right ? null : it.right.value) + "]");
        });
        System.out.println();
    }

    /**
     * 022-从上往下打印二叉树
     *
     * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
     *
     * 思路:
     * 1、利用队列进行层次遍历
     * 2、每次弹出队列中的一个元素，并把左右孩子加入队列即可
     */
    static void printFromTopToBottom(Node node, Visit visit) {
        if (null == node) {
            return;
        }
        Queue<Node> queue = new java.util.LinkedList<>();
        queue.add(node);

        for (; !queue.isEmpty();) {
            Node cur = queue.poll();

            visit.now(cur);

            if (null != cur.left) {
                queue.add(cur.left);
            }
            if (null != cur.right) {
                queue.add(cur.right);
            }
        }
    }

    private static void testPrintFromTopToBottom() {
        /**
         *   2
         *  / \
         * 4   5
         */
        Node<Integer> _5u = new Node<>(5, null, null);
        Node<Integer> _4u = new Node<>(4, null, null);
        Node<Integer> _2u = new Node<>(2, _4u, _5u);

        printFromTopToBottom(_2u, (Visit<Node<Integer>>) it -> System.out.print(it.value+" "));
        System.out.println();
    }

    /**
     * 二叉搜索树的后序遍历序列
     *
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
     * 如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
     *
     * 思路(递归)：
     * 1、后序遍历的特征为 根节点在序列的最后 值为rootVal
     * 2、序列上半部分的值都小于rootVal，下部分的值都大于rootVal
     * 3、递归判断上半部分、下半部分的序列，是否是树的后序遍历序列
     */
    static boolean verifySequenceOfBST(int[] sequence) {
        if (null == sequence || sequence.length < 1) {
            return false;
        }
        return verifySequenceOfBST(sequence, 0, sequence.length - 1);
    }

    private static boolean verifySequenceOfBST(int[] sequence, int begin, int end) {
        if (begin >= end) {             // 一个元素时，为后序遍历序列
            return true;
        }
        int rootVal = sequence[end];    // root节点的值
        int leftEnd = begin;            // 序列中的最后一个左子树节点
        int i = begin;

        while (sequence[i] < rootVal) { // 遍历找到左子树的序列 与 右子树序列, 获取分割索引
            leftEnd = i;
            i++;
        }
        while (i < end) {               // 判断leftEnd序列后的值,如果存在元素小于rootVal,则不是后序序列
            if (sequence[i] < rootVal) {
                return false;
            }
            i++;
        }
        return verifySequenceOfBST(sequence, begin, leftEnd) && verifySequenceOfBST(sequence, leftEnd + 1, end - 1);
    }

    private static void testVerifySequenceOfBST() {
        int[] sequence = {1, 2, 3, 4, 5};
        boolean res = verifySequenceOfBST(sequence);
        System.out.println(res);
    }

    /**
     * 024-二叉树中和为某一值的路径
     *
     * 题目描述：
     * 输入一颗二叉树的跟节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
     * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
     * (注意: 在返回值的list中，数组长度大的数组靠前)
     *
     * 思路：
     *  1、用深度优先搜索DFS
     *  2、每当DFS搜索到新节点时，都要保存该节点。而且每当找出一条路径之后，都将这个保存到list的路径保存到最终结果二维list中
     *  3、并且，每当DFS搜索到子节点，发现不是路径和时，返回上一个结点时，需要把该节点从list中移除
     */
    static List<List<Integer>> findPath(Node<Integer> root, int target) {
        if (null == root) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new java.util.LinkedList<>();
        findPath(result, new java.util.LinkedList<>(), target, root);
        return result;
    }

    private static void findPath(List<List<Integer>> result, java.util.LinkedList<Integer> one, int target, Node<Integer> node) {
        if (null == node) {
            return;
        }
        one.add(node.value);

        int remainVal = target - node.value;
        if (0 == remainVal && null == node.left && null == node.right) {
            result.add(new java.util.LinkedList<>(one));
        }

        findPath(result, one, remainVal, node.left);
        findPath(result, one, remainVal, node.right);
        one.removeLast();
    }

    private static void testFindPath() {
        /**
         *      1
         *    /  \
         *   2    3
         *  / \
         * 4   5
         */
        Node<Integer> _5 = new Node<>(5, null, null);
        Node<Integer> _4 = new Node<>(4, null, null);
        Node<Integer> _3 = new Node<>(3, null, null);
        Node<Integer> _2 = new Node<>(2, _4, _5);
        Node<Integer> _1 = new Node<>(1, _2, _3);

        List<List<Integer>> res = findPath(_1, 8);
        System.out.println(res);
    }

    /**
     * 026-二叉搜索树与双向链表
     *
     * 题目描述
     * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。
     *
     * 思路
     * 1、中序遍历BST的结果是排序的
     * 2、按照遍历顺序组建为双向链表即可
     *
     *
     * 中序遍历过程：
     * 1、利用栈与当前节点的指针
     * 2、处理根节点，如果有左孩子，就处理左孩子，记左孩子为链表的最后一个节点，后续指向当前节点
     * 3、如果左孩子为空，记录当前节点为链表的最后一个节点
     * 4、处理右孩子节点
     */
    static Node convertTree2LinkedList(Node node) {
        if (null == node) {
            return null;
        }

        Node head = null;
        Deque<Node> stack = new java.util.LinkedList<>();
        for (Node pre = null, cur = node; !stack.isEmpty() || null != cur;) {
            // 左
            for (;null != cur;) {
                stack.push(cur);
                cur = cur.left;
            }
            // 中（处理）
            cur = stack.pop();
            if (null == pre) {
                head = cur;
                pre = head;
            } else {
                pre.right = cur; // 构建双向链表
                cur.left = pre;

                pre = cur;       // 推进pre
            }
            // 右
            cur = cur.right;     // 指向右孩子
        }
        return head;
    }

    private static void testConvertTree2LinkedList() {
        /**
         *      4
         *    /  \
         *   2    5
         *  / \
         * 1   3
         */
        Node<Integer> _3 = new Node<>(3, null, null);
        Node<Integer> _1 = new Node<>(1, null, null);
        Node<Integer> _2 = new Node<>(2, _1, _3);
        Node<Integer> _5 = new Node<>(5, null, null);
        Node<Integer> _4 = new Node<>(4, _2, _5);

        Node res = convertTree2LinkedList(_4);

        for (; null != res;) {
            System.out.print(res.value + " ");
            res = res.right;
        }
        System.out.println();
    }

    /**
     * 038-二叉树的深度
     */
    private static int depth(Node node) {
        if (null == node) {
            return 0;
        } else {
            int ld = depth(node.left);
            int rd = depth(node.right);
            return 1 + (ld > rd ? ld : rd);
        }
    }

    /**
     * 039-平衡二叉树
     */
    static boolean isBalance(Node root) {
        if (null == root) {
            return true;
        }
        int dist = Math.abs(depth(root.left) - depth(root.right));
        if (dist > 1) {
            return false;
        } else {
            return isBalance(root.left) && isBalance(root.right);
        }
    }

    private static void testIsBalance() {
        /**
         *      0
         *     / |
         *    1   2
         *   /
         *  3
         */
        Node _3 = new Node(3, null, null);
        Node _1 = new Node(1, _3, null);
        Node _2 = new Node(2, null, null);
        Node root = new Node(0, _1, _2);
        boolean result = isBalance(root);
        System.out.println("平衡否？" + result);
    }

    /**
     * 057-二叉树的下一个结点
     *
     * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。
     * 注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
     *
     * 思路：
     * 分析二叉树的下一个节点，一共有以下情况：
     * 1.二叉树为空，则返回空；
     * 2.节点右孩子存在，则设置一个指针从该节点的右孩子出发，一直沿着指向左子结点的指针找到的叶子节点即为下一个节点；
     * 3.节点右孩子不存在，节点是根节点，返回null。
     *   节点不是根节点，如果该节点是其父节点的左孩子，则返回父节点；
     *   否则继续向上遍历其父节点的父节点，重复之前的判断，返回结果。
     *
     */
    static Node getInOrderNext(Node node) {
        if (null == node) {                 // 1.二叉树为空，则返回空；
            return null;
        }
        Node cur = node;
        if (null != cur.right) {            // 2.节点右孩子存在，则设置一个指针从该节点的右孩子出发，一直沿着指向左子结点的指针找到的叶子节点即为下一个节点；
            cur = cur.right;
            for (; null != cur.left;) {
                cur = cur.left;
            }
            return cur;
        }

        for (; null != cur.parent;) {       // 3.节点右孩子不存在，节点是根节点，返回null。
            if (cur == cur.parent.left) {   //   节点不是根节点，如果该节点是其父节点的左孩子，则返回父节点；
                return cur.parent;
            }
            cur = cur.parent;               //   否则继续向上遍历其父节点的父节点，重复之前的判断，返回结果。
        }
        return null;
    }

    private static void testGetInOrderNext() {
        /**
         *      4
         *    /  \
         *   2    5
         *  / \
         * 1   3
         */
        Node<Integer> _3 = new Node<>(3, null, null);
        Node<Integer> _1 = new Node<>(1, null, null);
        Node<Integer> _2 = new Node<>(2, _1, _3);
        Node<Integer> _5 = new Node<>(5, null, null);
        Node<Integer> _4 = new Node<>(4, _2, _5);
        _2.parent = _4;
        _5.parent = _4;
        _1.parent = _2;
        _3.parent = _2;

        Node<Integer> res1 = getInOrderNext(_2);
        System.out.println(res1.value);

        Node<Integer> res2 = getInOrderNext(_4);
        System.out.println(res2.value);

        Node<Integer> res3 = getInOrderNext(_5);
        System.out.println(res3);
    }


    /**
     * 058-对称的二叉树
     *
     * 请实现一个函数，用来判断一颗二叉树是不是对称的。
     * 注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
     *
     * 思路1：
     * 1、递归判断两侧的节点是否是对称的
     */
    static boolean isSymmetricalTree(Node node) {
        if (null == node) {
            return true;
        }
        return isSymmetrical(node.left, node.right);
    }

    private static boolean isSymmetrical(Node left, Node right) {
        if (null == left && null == right) {
            return true;
        }
        if (null == left || null == right) {
            return false;
        }
        if (left.value != right.value) {
            return false;
        }
        return isSymmetrical(left.left, right.right) && isSymmetrical(left.right, right.left);
    }

    private static void testIsSymmetricalTree() {
        /**
         *         1
         *       /  \
         *      2    2
         *     / \  / \
         *    6  7 7   6
         */
        Node _7u = new Node(7, null, null);
        Node _6u = new Node(6, null, null);
        Node _7 = new Node(7, null, null);
        Node _6 = new Node(6, null, null);
        Node _2u = new Node(2, _7u, _6u);
        Node _2 = new Node(2, _6, _7);
        Node _1 = new Node(1, _2, _2u);

        boolean res = isSymmetricalTree(_1);
        System.out.println(res);
    }
    
    /**
     * 059-按之字形顺序打印二叉树
     *
     * 题意：按照z字形层次遍历二叉树（以根节点所在层为第1层，则第二层的变量从右边节点开始直到最左边节点，第三层遍历则是从最左边开始到最右边）
     * 思路：z字形层次遍历是对层次遍历加上了一个限制条件（即相邻层，从左到右的遍历顺序相反），因此还是可以采用队列来实现，只不过节点接入队列时需要考虑加入的顺序
     */
    static void zigzagLevelOrder(Node<Integer> root) {
        if (null == root) {
            return;
        }

        Deque<Node<Integer>> queue = new java.util.LinkedList<>();
        queue.add(root);

        for (int depth = 0; !queue.isEmpty(); depth++) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node<Integer> t = null;
                if (0 == depth % 2) {
                    t = queue.pop();
                    System.out.print(t.value + " ");
                    if (null != t.left) {
                        queue.add(t.left);
                    }
                    if (null != t.right) {
                        queue.add(t.right);
                    }
                } else {
                    t = queue.pollLast();
                    System.out.print(t.value + " ");
                    if (null != t.right) {
                        queue.addFirst(t.right);
                    }
                    if (null != t.left) {
                        queue.addFirst(t.left);
                    }
                }
            }
        }
    }

    private static void testZigzagLevelOrder() {
        /**
           7
         /   \
        4     8
            /   \
           2    6
           \   / \
            3 5   9
         */

        Node<Integer> _9 = new Node<Integer>(9, null, null);
        Node<Integer> _3 = new Node<Integer>(3, null, null);
        Node<Integer> _5 = new Node<Integer>(5, null, null);
        Node<Integer> _2 = new Node<Integer>(2, null, _3);
        Node<Integer> _6 = new Node<Integer>(6, _5, _9);
        Node<Integer> _4 = new Node<Integer>(4, _2, _6);
        Node<Integer> _8 = new Node<Integer>(8, null, null);
        Node<Integer> _7 = new Node<Integer>(7, _4, _8);

        zigzagLevelOrder(_7);
        System.out.println();
    }

    /**
     * 060-把二叉树打印成多行
     * 从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。
     *
     * 思路1：
     * 1、初始化一个队列，初始元素为root
     * 2、遍历元素，每次首先获取当前队列的节点个数，即当前队列的size
     * 3、弹出size次元素，则本次遍历到的均为本层的元素
     * 4、每次弹出元素的同时，把元素的左右孩子加入队列，以便下次遍历
     *
     */
    static void levelPrint(Node node) {
        if (null == node) {
            return;
        }
        Queue<Node> queue = new java.util.LinkedList<>();
        queue.add(node);

        for (; !queue.isEmpty();) {
            for (int size = queue.size(), i = 0; i < size; i++) {
                Node cur = queue.poll();     // 弹出本层元素

                System.out.print(cur.value + " "); // print

                if (null != cur.left) {
                    queue.add(cur.left);
                }
                if (null != cur.right) {
                    queue.add(cur.right);
                }
            }
            System.out.println();
        }
    }

    private static void testLevelPrint() {
        /**
         *      4
         *    /  \
         *   2    5
         *  / \
         * 1   3
         */
        Node<Integer> _3 = new Node<>(3, null, null);
        Node<Integer> _1 = new Node<>(1, null, null);
        Node<Integer> _2 = new Node<>(2, _1, _3);
        Node<Integer> _5 = new Node<>(5, null, null);
        Node<Integer> _4 = new Node<>(4, _2, _5);

        levelPrint(_4);
    }

    /**
     * 062-二叉搜索树的第k个结点
     *
     * 给定一棵二叉搜索树，请找出其中的第k小的结点。
     * 例如， （5，3，7，2，4，6，8）    中，按结点数值大小顺序第三小结点的值为4。
     *
     * 思路
     * 1、二叉搜索树的中序遍历有序递增
     * 2、中序遍历二叉树(递归/非递归)，遍历到第k个元素即停止，则得到第k小元素
     *
     */
    static Node<Integer> kthInBST(Node<Integer> root, int k) {
        if (null == root || k <= 0) {
            return null;
        }
        List<Node<Integer>> cache = new java.util.LinkedList<>();
        helpKthInBST(cache, k, root);
        return k <= cache.size() ? cache.get(k - 1) : null;
    }

    private static void helpKthInBST(List<Node<Integer>> cache, int k, Node<Integer> node) {
        if (null == node) {
            return;
        }
        helpKthInBST(cache, k, node.left);
        cache.add(node);
        if (cache.size() == k) {
            return;
        }
        helpKthInBST(cache, k, node.right);
    }

    /**
     * 思路：
     * 1、非递归中序遍历，遍历到第k个元素停止遍历
     */
    static Node<Integer> kthInBST2(Node<Integer> root, int k) {
        if (null == root || k <= 0) {
            return null;
        }
        List<Node<Integer>> cache = new java.util.LinkedList<>();
        Deque<Node<Integer>> stack = new java.util.LinkedList<>();
        for (Node cur = root; !stack.isEmpty() || null != cur;) {
            for (; null != cur;) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            cache.add(cur);
            if (cache.size() == k) {
                break;
            }
            cur = cur.right;
        }
        return k <= cache.size() ? cache.get(k - 1) : null;
    }

    private static void testKthInBST() {
        /**
         *      4
         *    /  \
         *   2    5
         *  / \
         * 1   3
         */
        Node<Integer> _3 = new Node<>(3, null, null);
        Node<Integer> _1 = new Node<>(1, null, null);
        Node<Integer> _2 = new Node<>(2, _1, _3);
        Node<Integer> _5 = new Node<>(5, null, null);
        Node<Integer> _4 = new Node<>(4, _2, _5);

        Node<Integer> res = kthInBST(_4, 2);
        System.out.println(res.value);

        Node<Integer> res2 = kthInBST2(_4, 3);
        System.out.println(res2.value);
    }


    public static void main(String[] args) {
        // 004-重建二叉树
        testReConstructBinaryTree();
        // 017-树的子结构
        testHasSubtree();
        // 018-二叉树的镜像
        testMirrorTree();
        // 022-从上往下打印二叉树
        testPrintFromTopToBottom();
        // 023-二叉搜索树的后序遍历序列
        testVerifySequenceOfBST();
        // 024-二叉树中和为某一值的路径
        testFindPath();
        // 026-二叉搜索树与双向链表
        testConvertTree2LinkedList();
        // 038-二叉树的深度
        // 039-平衡二叉树
        testIsBalance();
        // 057-二叉树的下一个结点
        testGetInOrderNext();
        // 058-对称的二叉树
        testIsSymmetricalTree();
        // 059-按之字形顺序打印二叉树
        testZigzagLevelOrder();
        // 060-把二叉树打印成多行
        testLevelPrint();
        // 062-二叉搜索树的第k个结点
        testKthInBST();
    }
}
