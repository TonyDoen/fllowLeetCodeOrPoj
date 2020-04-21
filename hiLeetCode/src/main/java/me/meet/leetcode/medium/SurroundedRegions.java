package me.meet.leetcode.medium;

public final class SurroundedRegions {
    private SurroundedRegions() {
    }
    /**
     * Surrounded Regions
     *
     * Given a 2D board containing'X'and'O', capture all regions surrounded by'X'.
     * A region is captured by flipping all'O's into'X's in that surrounded region .
     * For example,
     *     X X X X
     *     X O O X
     *     X X O X
     *     X O X X
     *
     * After running your function, the board should be:
     *     X X X X
     *     X X X X
     *     X X X X
     *     X O X X
     *
     *
     * 题意：所有与四条边相连的O都保留，其他O都变为X
     * 1、被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。
     * 2、任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。
     * 3、如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
     *
     * 思路：
     * 1、遍历四条边上的O，并深度遍历与其相连的O，将这些O都转为*
     * 2、将剩余的O变为X
     * 3、将剩余的*变为O
     */
    static void surroundedRegions(char[][] matrix) {
        if (null == matrix || matrix.length <= 1 || matrix[0].length <= 1) {
            return;
        }
        int row = matrix.length, col = matrix[0].length; // 行；列；

        // 1、四条边上开始深度遍历，边上O及与边上O相连的全变为*
        for (int i = 0; i < row; i++) {  // 行
            dfs(matrix, i, 0);       // 第一列元素开始深度遍历
            dfs(matrix, i, col - 1); // 最后一列元素开始深度遍历
        }
        for (int j = 0; j < col; j++) {  // 列
            dfs(matrix, 0, j);       // 第一行元素开始深度遍历
            dfs(matrix, row - 1, j); // 最后一行元素开始深度遍历
        }

        // 2、将剩余的O变为X
        // 3、将剩余的*变为O
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                char c = matrix[i][j];
                if ('O' == c) {        // 将剩余的O变为X
                    matrix[i][j] = 'X';
                } else if ('*' == c) { // 将剩余的*变为O
                    matrix[i][j] = 'O';
                }
            }
        }

    }

    // 深度遍历
    private static final int[][] dirs = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}}; // 上/左/下/右

    private static void dfs(char[][] matrix, int row, int col) {
        // 非法判断
        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length) {
            return;
        }
        if (matrix[row][col] == 'O') {
            // 替换为*
            matrix[row][col] = '*';
            // 深度遍历上下左右
            for (int[] dir : dirs) {
                dfs(matrix, row + dir[0], col + dir[1]);
            }

//            dfs(matrix, row-1, col);
//            dfs(matrix, row+1, col);
//            dfs(matrix, row, col-1);
//            dfs(matrix, row, col+1);
        }
    }

    private static void testSurroundedRegions() {
        char[][] matrix = {
                {'X', 'X', 'X', 'X'},
                {'X', 'O', 'O', 'X'},
                {'X', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'X'}};
        surroundedRegions(matrix);

        for (char[] chars : matrix) {
            for (char c : chars) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        testSurroundedRegions();
    }
}
