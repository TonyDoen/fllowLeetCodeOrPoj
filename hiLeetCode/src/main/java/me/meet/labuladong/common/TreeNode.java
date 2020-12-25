package me.meet.labuladong.common;

import java.util.LinkedList;

/**
 * 模板：
 *
 * void traverse(TreeNode root) {
 *     // 前序遍历
 *     traverse(root.left)
 *     // 中序遍历
 *     traverse(root.right)
 *     // 后序遍历
 * }
 */
public class TreeNode {
    private Integer val;
    private TreeNode left;

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    private TreeNode right;

    public Integer getVal() {
        return val;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public TreeNode(Integer val) {
        this.val = val;
    }

    public TreeNode(Integer val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode prepareTree1() {
        /**
         *         -1
         *       /   \
         *     2      -3
         *   /  \    /  \
         * 2     4  5   -8
         */
        TreeNode _2 = new TreeNode(2);
        TreeNode _4 = new TreeNode(4);
        TreeNode _5 = new TreeNode(5);
        TreeNode _8 = new TreeNode(-8);
        TreeNode _3 = new TreeNode(-3, _5, _8);
        TreeNode _2u = new TreeNode(2, _2, _4);
        TreeNode _1 = new TreeNode(-1, _2u, _3);
        return _1;
    }

    public int maxLevel() {
        return maxLevel(this);
    }

    public static int maxLevel(TreeNode node) {
        if (null == node) {
            return 0;
        }

        return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
    }

    public void println() {
        println(this, maxLevel(this));
    }

    public static void println(TreeNode node, int maxLevel) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        for (int depth = 0; depth < maxLevel; depth++) {
            int size = queue.size();
            System.out.printf("binary tree level %d : ", depth);
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();

                if (null == cur) {
                    System.out.print("nil ");
                    queue.offer(null);
                    queue.offer(null);
                } else {
                    System.out.print(cur.val + " ");
                    queue.offer(cur.left);
                    queue.offer(cur.right);
                }
            }
            System.out.println();
        }
    }
}