public class BackTraceAL {

    /**
     * 矩阵中的路经
     * 思路：就是从矩阵中每个点出发，递归上下左右，并添加递归终止条件
     * 注意要点：
     * 1.全局访问数组
     * 2.递归终止条件
     * @param matrix
     * @param rows
     * @param cols
     * @param str
     * @return
     */
    private boolean[] visited;
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str)
    {
        visited = new boolean[matrix.length];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(BackTracePath(matrix, rows, cols, str, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean BackTracePath(char[] matrix, int rows, int cols, char[] str, int row, int col, int strIndex) {
        // 算出在一维数组的位置
        int index = row * cols + col;
        //递归终结条件
        if (row < 0 || col < 0 || row >= rows || col >= cols) {
            return false;
        }
        if (matrix[index] != str[strIndex] || visited[index]) {
            return false;
        }

        if (strIndex == str.length - 1) {
            return true;
        }

        visited[index] = true;
        //向四个方向递归
        if (BackTracePath(matrix, rows, cols, str, row + 1, col, strIndex + 1)) {
            return true;
        }
        if (BackTracePath(matrix, rows, cols, str, row - 1, col, strIndex + 1)) {
            return true;
        }
        if (BackTracePath(matrix, rows, cols, str, row, col + 1, strIndex + 1)) {
            return true;
        }
        if (BackTracePath(matrix, rows, cols, str, row, col - 1, strIndex + 1)) {
            return true;
        }
        visited[index] = false;
        return false;
    }


    /**
     * 机器人的运动范围
     * @param threshold
     * @param rows
     * @param cols
     * @return
     */
    private boolean[][] visitedMove;
    public int movingCount(int threshold, int rows, int cols)
    {
        visitedMove = new boolean[rows][cols];
        return BackTraceMoving(0, 0, threshold, rows, cols);
    }

    private int BackTraceMoving(int row, int col, int num, int rows, int cols) {
        if (row < 0 || col < 0 || row >= rows || col >= cols) {
            return 0;
        }
        if (numSum(row) + numSum(col) > num || visitedMove[row][col]) {
            return 0;
        }
        visitedMove[row][col] = true;
        return BackTraceMoving(row - 1, col, num, rows, cols)
                + BackTraceMoving(row + 1, col, num, rows, cols)
                + BackTraceMoving(row, col -1, num, rows, cols)
                + BackTraceMoving(row, col + 1, num, rows, cols)
                + 1;
    }

    private int numSum(int i) {
        int sum = 0;
        do{
            sum += i%10;
        }while((i = i/10) > 0);
        return sum;
    }
}
