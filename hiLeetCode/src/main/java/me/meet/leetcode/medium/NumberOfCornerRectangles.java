package me.meet.leetcode.medium;

public final class NumberOfCornerRectangles {
    private NumberOfCornerRectangles() {}

    /**
     * Given a grid where each entry is only 0 or 1, find the number of corner rectangles.
     * A corner rectangle is 4 distinct 1s on the grid that form an axis-aligned rectangle.
     * Note that only the corners need to have the value 1. Also, all four 1s used must be distinct.
     *
     * Example 1:
     * Input: grid =
     * [[1, 0, 0, 1, 0],
     *  [0, 0, 1, 0, 1],
     *  [0, 0, 0, 1, 0],
     *  [1, 0, 1, 0, 1]]
     * Output: 1
     * Explanation: There is only one corner rectangle, with corners grid[1][2], grid[1][4], grid[3][2], grid[3][4].
     *
     * Example 2:
     * Input: grid =
     * [[1, 1, 1],
     *  [1, 1, 1],
     *  [1, 1, 1]]
     * Output: 9
     * Explanation: There are four 2x2 rectangles, four 2x3 and 3x2 rectangles, and one 3x3 rectangle.
     *
     * Example 3:
     * Input: grid =
     * [[1, 1, 1, 1]]
     * Output: 0
     * Explanation: Rectangles must have four distinct corners.
     *
     * Note:
     * The number of rows and columns of grid will each be in the range [1, 200].
     * Each grid[i][j] will be either 0 or 1.
     * The number of 1s in the grid will be at most 6000.
     */

    static int countCornerRectangles(int[][] grid) {
        int m = grid.length, n = grid[0].length, res = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = i + 1; j < m; ++j) {
                int cnt = 0;
                for (int k = 0; k < n; ++k) {
                    if (grid[i][k] == 1 && grid[j][k] == 1) ++cnt;
                }
                res += cnt * (cnt - 1) / 2;
            }
        }
        return res;
    }

    static int countCornerRectangles2(int[][] grid) {
        int m = grid.length, n = grid[0].length, res = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 0) continue;
                for (int h = 1; h < m - i; ++h) {
                    if (grid[i + h][j] == 0) continue;
                    for (int w = 1; w < n - j; ++w) {
                        if (grid[i][j + w] == 1 && grid[i + h][j + w] == 1) ++res;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] grid = {{1, 1, 1},{1, 1, 1},{1, 1, 1}};
        int result = countCornerRectangles(grid);
        int result2 = countCornerRectangles2(grid);
        System.out.println(result);
        System.out.println(result2);
    }
}
