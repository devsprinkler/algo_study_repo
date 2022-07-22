import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ2638 {
    static int[][] cheese;
    static int[][] cheeseNext;
    static boolean[][] visited;
    static int n, m;

    // 가장자리 표시
    static void dfs(int x, int y) {
        if (visited[x][y]) return;
        visited[x][y] = true;

        if (x > 0 && cheese[x - 1][y] == 0 && !visited[x - 1][y]) {
            cheese[x - 1][y] = -1;
            dfs(x - 1, y);
        }

        if (x < n - 1 && cheese[x + 1][y] == 0 && !visited[x + 1][y]) {
            cheese[x + 1][y] = -1;
            dfs(x + 1, y);
        }

        if (y > 0 && cheese[x][y - 1] == 0 && !visited[x][y - 1]) {
            cheese[x][y - 1] = -1;
            dfs(x, y - 1);
        }

        if (y < m - 1 && cheese[x][y + 1] == 0 && !visited[x][y + 1]) {
            cheese[x][y + 1] = -1;
            dfs(x, y + 1);
        }
    }

    // 가장자리인지 확인
    static boolean isBorder(int x, int y) {
        // top
        for (int i = 1; x - i >= 0; i++) {
            if (cheese[x - i][y] == 1) break;
            if (x - i == 0 || cheese[x - i][y] == -1) return true;
        }

        //bottom
        for (int i = 1; x + i < n; i++) {
            if (cheese[x + i][y] == 1) break;
            if (x + i == n - 1 || cheese[x + i][y] == -1) return true;
        }

        // left
        for (int i = 1; y - i >= 0; i++) {
            if (cheese[x][y - i] == 1) break;
            if (y - i == 0 || cheese[x][y - 1] == -1) return true;
        }

        // right
        for (int i = 1; y + i < m; i++) {
            if (cheese[x][y + i] == 1) break;
            if (y + i == m - 1 || cheese[x][y + 1] == -1) return true;
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        ArrayList<int[]> cheesePos = new ArrayList<>();
        cheese = new int[n][m];
        cheeseNext = new int[n][m];
        visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                cheese[i][j] = Integer.parseInt(st.nextToken());
                if (cheese[i][j] == 1) {
                    cheesePos.add(new int[]{i, j});
                }
            }
        }

        int count = 0;
        while (!cheesePos.isEmpty()) {
            count++;

            // 가장자리 확인 & 표시
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (cheese[i][j] == 0 && isBorder(i, j)) {
                        cheese[i][j] = -1;
                        dfs(i, j);
                    }
                }
            }

            // 임시저장용 배열 복사
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    cheeseNext[i][j] = cheese[i][j];
                }
            }

            // 가장자리와 접한 부분이 2곳 이상인 경우 리스트에서 제외
            for (int i = 0; i < cheesePos.size(); i++) {
                int exposed = 0;
                int[] pos = cheesePos.get(i);
                int x = pos[0];
                int y = pos[1];

                if (x > 0) {
                    if (cheese[x - 1][y] == -1) exposed++;
                } else exposed++;

                if (x < n - 1) {
                    if (cheese[x + 1][y] == -1) exposed++;
                } else exposed++;

                if (y > 0) {
                    if (cheese[x][y - 1] == -1) exposed++;
                } else exposed++;

                if (y < m - 1) {
                    if (cheese[x][y + 1] == -1) exposed++;
                } else exposed++;

                if (exposed >= 2) {
                    cheeseNext[x][y] = 0;
                    cheesePos.remove(i--);
                }
            }

            // 임시저장 배열을 다시 복사
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    cheese[i][j] = cheeseNext[i][j];
                }
            }
        }

        System.out.println(count);
    }
}
