import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    static int[][] pos = {
        {-1, -1}, 
        {-1, 0}, 
        {-1, 1}, 
        {0, -1}, 
        {0, 1}, 
        {1, -1},
        {1, 0},
        {1, 1}
    };

    static int[][] board;
    static int[][] parent;

    static int find(int row, int col) {
        if (parent[row][col] == 0) return row * 1000 + col;
        return parent[row][col] = find(parent[row][col] / 1000, parent[row][col] % 1000);
    }

    static void union(int row, int col) {
        int min = board[row][col];
        int minRow = row;
        int minCol = col;
        for (int i = 0; i < 8; i++) {
            if (board[row + pos[i][0]][col + pos[i][1]] < min) {
                min = board[row + pos[i][0]][col + pos[i][1]];
                minRow = row + pos[i][0];
                minCol = col + pos[i][1];
            }
        }

        if (min != board[row][col]) {
            parent[row][col] = minRow * 1000 + minCol;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        
        board = new int[r+2][c+2];
        int[][] ball = new int[r+2][c+2];
        for (int i = 0; i < r + 2; i++) {
            for (int j = 0; j < c + 2; j++) {
                board[i][j] = Integer.MAX_VALUE;
                ball[i][j] = 1;
            }
        }

        LinkedList<int[]> queue = new LinkedList<>();
        for (int i = 1; i <= r; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= c; j++) {
                queue.offer(new int[] {i, j});
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        parent = new int[r+2][c+2];
        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= c; j++) {
                int t = find(i, j);
                union(t / 1000, t % 1000);
            }
        }

        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= c; j++) {
                find(i, j);
            }
        }

        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= c; j++) {
                if (parent[i][j] == 0) continue;
                int row = parent[i][j] / 1000;
                int col = parent[i][j] % 1000;
                ball[row][col] += ball[i][j];
                ball[i][j] = 0;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= r; i++) {
            for (int j = 1; j <= c; j++) {
                sb.append(ball[i][j]).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
}
