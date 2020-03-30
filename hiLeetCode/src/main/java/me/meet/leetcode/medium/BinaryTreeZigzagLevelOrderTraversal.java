package me.meet.leetcode.medium;

import java.util.LinkedList;

public final class BinaryTreeZigzagLevelOrderTraversal {
    private BinaryTreeZigzagLevelOrderTraversal() {}

    static class TreeNode {
        Integer val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}
        TreeNode(Integer val) {this.val = val;}
        TreeNode(Integer val, TreeNode l, TreeNode r) {
            this.val = val;
            this.left = l;
            this.right = r;
        }
    }


    /**
     * 059-按之字形顺序打印二叉树
     *
     * 题意：按照z字形层次遍历二叉树（以根节点所在层为第1层，则第二层的变量从右边节点开始直到最左边节点，第三层遍历则是从最左边开始到最右边）
     * 思路：z字形层次遍历是对层次遍历加上了一个限制条件（即相邻层，从左到右的遍历顺序相反），因此还是可以采用队列来实现，只不过节点接入队列时需要考虑加入的顺序
     */
    static void zigzagLevelOrder(TreeNode root) {
        if (null == root) {
            return;
        }

        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        for (int depth = 0; !queue.isEmpty(); depth++) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode t = null;
                if (0 == depth % 2) {
                    t = queue.pop();
                    System.out.print(t.val + " ");
                    if (null != t.left) {
                        queue.add(t.left);
                    }
                    if (null != t.right) {
                        queue.add(t.right);
                    }
                } else {
                    t = queue.pollLast();
                    System.out.print(t.val + " ");
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
          2      6
           \    / \
            3  5   9
         */

        TreeNode _9 = new TreeNode(9);
        TreeNode _3 = new TreeNode(3);
        TreeNode _5 = new TreeNode(5);
        TreeNode _2 = new TreeNode(2, null, _3);
        TreeNode _6 = new TreeNode(6, _5, _9);
        TreeNode _4 = new TreeNode(4, _2, _6);
        TreeNode _8 = new TreeNode(8, null, null);
        TreeNode _7 = new TreeNode(7, _4, _8);

        zigzagLevelOrder(_7);
        System.out.println();
    }

    public static void main(String[] args) {
        testZigzagLevelOrder();
    }
}
