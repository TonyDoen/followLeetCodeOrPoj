package me.meet.leetcode.medium;

public final class ValidTicTacToeState {
    private ValidTicTacToeState() {}

    /**
     url:
     url:

     A Tic-Tac-Toe board is given as a string array board. Return True if and only if it is possible to reach this board position during the course of a valid tic-tac-toe game.
     The board is a 3 x 3 array, and consists of characters " ", "X", and "O".  The " " character represents an empty square.

     Here are the rules of Tic-Tac-Toe:
     Players take turns placing characters into empty squares (" ").
     The first player always places "X" characters, while the second player always places "O" characters.
     "X" and "O" characters are always placed into empty squares, never filled ones.
     The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
     The game also ends if all squares are non-empty.
     No more moves can be played if the game is over.

     Example 1:
     Input: board = ["O  ", "   ", "   "]
     Output: false
     Explanation: The first player always plays "X".

     Example 2:
     Input: board = ["XOX", " X ", "   "]
     Output: false
     Explanation: Players take turns making moves.

     Example 3:
     Input: board = ["XXX", "   ", "OOO"]
     Output: false

     Example 4:
     Input: board = ["XOX", "O O", "XOX"]
     Output: true

     Note:
     board is a length-3 array of strings, where each string board[i] has length 3.
     Each board[i][j] is a character in the set {" ", "X", "O"}.
     */

    /**
     题意：
     这道题又是关于井字棋游戏的，之前也有一道类似的题Design Tic-Tac-Toe，不过那道题是模拟游戏进行的，而这道题是让我们验证当前井字棋的游戏状态是否正确。这题的例子给的比较好，cover了很多种情况：

     情况一：                                情况二：                                  情况三：
     0 _ _    这是不正确的状态,               X O X    因为两个player交替下棋,           X X X    一旦第一个玩家的X连成了三个,
     _ _ _    因为先走的使用X,                _ X _    X最多只能比O多一个,               _ _ _    那么游戏马上结束了,
     _ _ _    所以只出现一个O，是不对的;       _ _ _    这里多了两个,是不对的;             O O O    不会有另外一个O出现,是不对的

     情况四：                                情况五：
     X O X                                  X X X
     O _ O                                  O O _
     X O X    对的;                         O _ _    是不对的,理由同情况三
     */

    static boolean validTicTacToe(char[][] board) {
        boolean xwin = false, owin = false;
        int[] row = new int[3], col = new int[3];
        int diag = 0, antidiag = 0, turns = 0;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 'X') {
                    ++row[i]; ++col[j]; ++turns;
                    if (i == j) ++diag;
                    if (i + j == 2) ++antidiag;
                } else if (board[i][j] == 'O') {
                    --row[i]; --col[j]; --turns;
                    if (i == j) --diag;
                    if (i + j == 2) --antidiag;
                }
            }
        }
        xwin = row[0] == 3 || row[1] == 3 || row[2] == 3 ||
                col[0] == 3 || col[1] == 3 || col[2] == 3 ||
                diag == 3 || antidiag == 3;
        owin = row[0] == -3 || row[1] == -3 || row[2] == -3 ||
                col[0] == -3 || col[1] == -3 || col[2] == -3 ||
                diag == -3 || antidiag == -3;
        if ((xwin && turns == 0) || (owin && turns == 1)) return false;
        return (turns == 0 || turns == 1) && (!xwin || !owin);
    }

    public static void main(String[] args) {
        char[][] board = {{'O',' ',' '},{' ',' ',' ',},{' ',' ',' ',}};
        boolean res = validTicTacToe(board);
        System.out.println(res);
    }
}
