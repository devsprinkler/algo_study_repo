import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    // R x C 체스판
    // 각 칸에 정수 하나씩
    // 적혀있는 정수는 모두 서로 다르다

    // 각 칸위에 공을 하나씩 놓는다.
    // 공은 자동으로 다음 규칙따라 움직임
    // 1. 인접한 8방향(가로, 세로, 대각선)에 적힌 모든 정수가 현재 칸에 적힌 수보다 크면 이동을 멈춘다.
    // 2. 그 외의 경우에는 가장 작은 정수가 있는 칸으로 공이 이동한다.
    // 공의 크기는 매우 작아서, 체스판 한 칸 위에 여러개 공 가능
    // 공이 더 이상 움직이지않을 때, 각 칸에 공이 몇개 있는지
    
    // 최대 500행, 500열
    // 정수 최대 30만
    
    // 최대 25만개를 12.5만번이동 X -> dp
    
    // 위, 위오른, 오른, 아래오른, 아래, 아래왼, 왼, 위왼
    final static int dy[] = {-1, -1, 0, 1, 1, 1, 0, -1};
    final static int dx[] = {0, 1, 1, 1, 0, -1, -1, -1};
    static int R, C;
    static int[][] map;
    static int[][] count;
    static Pair[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new int[R][C];
        count = new int[R][C];
        dp = new Pair[R][C];

        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < C; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                Pair p = findDest(i, j);
                count[p.y][p.x] += 1;
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                sb.append(count[i][j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);

    }

    static Pair findDest(int r, int c) {
        if (dp[r][c] != null)
            return dp[r][c];

        // find min
        int min_y = -1, min_x = -1;
        for (int i = 0; i < dy.length; i++) {
            for (int j = 0; j < dx.length; j++) {
                int ny = r + dy[i];
                int nx = c + dx[i];

                if (ny >= 0 && ny < R && nx >= 0 && nx < C) {
                    if (map[ny][nx] < map[r][c]) {
                        if (min_y == -1 && min_x == -1) {
                            min_y = ny;
                            min_x = nx;
                        }
                        else if (map[min_y][min_x] > map[ny][nx]) {
                            min_y = ny;
                            min_x = nx;
                        }
                    }
                }
            }
        }
        
        // min 없으면 그대로
        if (min_y == -1 && min_x == -1) {
            dp[r][c] = new Pair(r, c);
            return dp[r][c];
        }
        // 있으면 재귀
        else {
            return dp[r][c] = findDest(min_y, min_x);
        }
    }

    static class Pair {
        int y, x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
