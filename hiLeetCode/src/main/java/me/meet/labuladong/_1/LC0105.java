package me.meet.labuladong._1;

import me.meet.labuladong.common.TreeNode;

import java.util.HashMap;
import java.util.Map;

public final class LC0105 {
    private LC0105() {
    }

    /**
     * LeetCode 105 题,难度 Medium,让你根据前序遍历和中序遍历的结果还原一棵二叉树,
     */
    static TreeNode reBuildTree(int[] preOrder, int[] inOrder) {
        if (null == preOrder || null == inOrder || preOrder.length < 1 || inOrder.length < 1) {
            return null;
        }

        Map<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < inOrder.length; i++) {
            inMap.put(inOrder[i], i);
        }

        return reBuildTree(preOrder, 0, preOrder.length - 1, inOrder, 0, inOrder.length - 1, inMap);
    }

    private static TreeNode reBuildTree(int[] preOrder, int preStart, int preEnd, int[] inOrder, int inStart, int inEnd, Map<Integer, Integer> inMap) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        TreeNode root = new TreeNode(preOrder[preStart]);
        int inRoot = inMap.get(root.getVal());
        int numsLeft = inRoot - inStart;

        root.setLeft(reBuildTree(preOrder, preStart + 1, preStart + numsLeft, inOrder, inStart, inRoot - 1, inMap));
        root.setRight(reBuildTree(preOrder, preStart + numsLeft + 1, preEnd, inOrder, inRoot + 1, inEnd, inMap));
        return root;
    }

    public static void main(String[] args) {
        int[] pre = new int[]{1, 2, 4, 7, 3, 5, 6, 8};
        int[] in = new int[]{4, 7, 2, 1, 5, 3, 8, 6};
        TreeNode result = reBuildTree(pre, in);

        result.println();

        System.out.println(result.maxLevel());
//        result.printTreeNode();
//        result.print();
    }
}
