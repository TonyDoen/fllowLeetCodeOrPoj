package me.meet.labuladong.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
    private TreeNode right;
    private TreeNode next;

    public TreeNode getNext() {
        return next;
    }

    public void setNext(TreeNode next) {
        this.next = next;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

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

    public static TreeNode prepareTree2() {
        /**
         *         3
         *       /   \
         *     9     20
         *           /  \
         *          15   7
         */
        TreeNode _15 = new TreeNode(15);
        TreeNode _7 = new TreeNode(7);
        TreeNode _20 = new TreeNode(20, _15, _7);
        TreeNode _9 = new TreeNode(9);
        TreeNode _3 = new TreeNode(3, _9, _20);
        return _3;
    }

    public static TreeNode prepareTree3() {
        /**         3
         *        /  \
         *      4      5
         *    /  \    /  \
         *   1   3  null  1
         */
        TreeNode _1 = new TreeNode(1);
        TreeNode _3 = new TreeNode(3);
        TreeNode _1a = new TreeNode(1);
        TreeNode _4 = new TreeNode(4, _1, _3);
        TreeNode _5 = new TreeNode(5, null, _1a);
        TreeNode _3a = new TreeNode(3, _4, _5);
        return _3a;
    }

    public static TreeNode prepareTree4() {
        /**
         *        4
         *      /   \
         *    2       7
         *  /  \    /  \
         * 1    3  6    9
         */
        TreeNode _1 = new TreeNode(1);
        TreeNode _3 = new TreeNode(3);
        TreeNode _6 = new TreeNode(6);
        TreeNode _9 = new TreeNode(9);
        TreeNode _2 = new TreeNode(2, _1, _3);
        TreeNode _7 = new TreeNode(7, _6, _9);
        TreeNode _4 = new TreeNode(4, _2, _7);
        return _4;
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

    public void print() {
        print(this);
    }

    public static void print(TreeNode root) {
        List<List<String>> lines = new ArrayList<>();
        List<TreeNode> level = new ArrayList<>();
        List<TreeNode> next = new ArrayList<>();

        level.add(root);
        int nn = 1;
        int widest = 0;
        while (nn != 0) {
            List<String> line = new ArrayList<>();
            nn = 0;
            for (TreeNode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = String.valueOf(n.getVal());
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null) nn++;
                    if (n.getRight() != null) nn++;
                }
            }
            if (widest % 2 == 1) widest++;
            lines.add(line);

            List<TreeNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perPiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perPiece / 2f) - 1;
            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perPiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (String f : line) {
                if (f == null) f = "";
                float mayV = perPiece / 2f - f.length() / 2f;
                int gap1 = (int) Math.ceil(mayV);
                int gap2 = (int) Math.floor(mayV);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            perPiece /= 2;
        }
    }
}