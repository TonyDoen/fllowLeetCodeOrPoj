package me.meet.leetcode.medium;

public final class DesignTicTacToe {
    private DesignTicTacToe() {}

    /**
     * url: http://www.cnblogs.com/grandyang/p/5467118.html
     * url: https://discuss.leetcode.com/topic/44548/java-o-1-solution-easy-to-understand
     *
     * Design a Tic-tac-toe game that is played between two players on a n x n grid.
     * You may assume the following rules:
     *
     * A move is guaranteed to be valid and is placed on an empty block.
     * Once a winning condition is reached, no more moves is allowed.
     * A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
     * Example:
     * Given n = 3, assume that player 1 is "X" and player 2 is "O" in the board.
     *
     * TicTacToe toe = new TicTacToe(3);
     * toe.move(0, 0, 1); -> Returns 0 (no one wins)
     * |X| | |
     * | | | | // Player 1 makes a move at (0, 0).
     * | | | |
     *
     * toe.move(0, 2, 2); -> Returns 0 (no one wins)
     * |X| |O|
     * | | | | // Player 2 makes a move at (0, 2).
     * | | | |
     *
     * toe.move(2, 2, 1); -> Returns 0 (no one wins)
     * |X| |O|
     * | | | | // Player 1 makes a move at (2, 2).
     * | | |X|
     *
     * toe.move(1, 1, 2); -> Returns 0 (no one wins)
     * |X| |O|
     * | |O| | // Player 2 makes a move at (1, 1).
     * | | |X|
     *
     * toe.move(2, 0, 1); -> Returns 0 (no one wins)
     * |X| |O|
     * | |O| | // Player 1 makes a move at (2, 0).
     * |X| |X|
     *
     * toe.move(1, 0, 2); -> Returns 0 (no one wins)
     * |X| |O|
     * |O|O| | // Player 2 makes a move at (1, 0).
     * |X| |X|
     *
     * toe.move(2, 1, 1); -> Returns 1 (player 1 wins)
     * |X| |O|
     * |O|O| | // Player 1 makes a move at (2, 1).
     * |X|X|X|
     * Follow up:
     * Could you do better than O(n2) per move() operation?
     *
     * Hint:
     * Could you trade extra space such that move() operation can be done in O(1)?
     * You need two arrays: int rows[n], int cols[n], plus two variables: diagonal, anti_diagonal.
     *
     *
     * 思路：设计 TicTacToe 游戏
     * 根据提示中的，我们建立一个大小为n的一维数组rows和cols，还有变量对角线diag和逆对角线rev_diag，这种方法的思路是，如果玩家1在第一行某一列放了一个子，那么rows[0]自增1，如果玩家2在第一行某一列放了一个子，则rows[0]自减1，那么只有当rows[0]等于n或者-n的时候，表示第一行的子都是一个玩家放的，则游戏结束返回该玩家即可，其他各行各列，对角线和逆对角线都是这种思路
     */
    static class TicTacToe {
        private int[] rows;
        private int[] cols;
        private int N;
        private int diagonal;    // 对角线
        private int revDiagonal;

        enum Player {
            RED,
            BLACK
            ;
        }

        TicTacToe(int n) {
            rows = new int[n];
            cols = new int[n];
            N = n;
            diagonal = 0;
            revDiagonal = 0;
        }

        int move(int row, int col, Player player) {
            int add = Player.RED.equals(player) ? 1 : -1;
            rows[row] += add;
            cols[col] += add;
            diagonal += (row == col ? add : 0);
            revDiagonal += (row == N - col - 1 ? add : 0);
            return (Math.abs(rows[row]) == N || Math.abs(cols[col]) == N || Math.abs(diagonal) == N || Math.abs(revDiagonal) == N) ? 1 : 0; // move 返回1 即获胜
        }
    }

    public static void main(String[] args) {
        final int size = 5;
        TicTacToe ticTacToe = new TicTacToe(size);
        for(int i = 0; i < size; i++) {
            int blackRes = ticTacToe.move(0, i, TicTacToe.Player.BLACK);
            if (1 == blackRes) {
                System.out.println(TicTacToe.Player.BLACK+" win");
                return;
            }
            int redRes = ticTacToe.move(3, i, TicTacToe.Player.BLACK);
            if (1 == redRes) {
                System.out.println(TicTacToe.Player.RED+" win");
                return;
            }
        }
    }

}
