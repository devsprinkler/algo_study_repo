import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {
    // 지도, 직사각
    // 각 칸에 그 지점의 높이
    // 이동: 상하좌우 인접

    // 왼쪽 위 시작 -> 오른쪽 아래 도착
    // 항상 높이가 더 낮은 지점으로만 이동하여 목표지점까지
    // 경로의 개수

    // 세로의 크기 M, 가로의 크기 N. 최대 500행, 500열
    // 각 지점 높이 최대 1만

    // 이동 가능한 경로의 수 H. 최대 10억
    
    // dp 있으면 가지치기
    
    // 상, 우, 하, 좌
    final static int dy[] = {-1, 0, 1, 0};
    final static int dx[] = {0, 1, 0, -1};
    static int[][] dp;
    static int[][] map;
    static int R, C;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        dp = new int[R][C];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }
        map = new int[R][C];

        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < C; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(findPath(0, 0));
    }

    static int findPath(int r, int c) {
        if (dp[r][c] != -1)
            return dp[r][c];
        if (r == R - 1 && c == C - 1)
            return 1;

        dp[r][c] = 0; // 초기화
        // lower path
        for (int i = 0; i < dy.length; i++) {
            int ny = r + dy[i];
            int nx = c + dx[i];

            if (ny >= 0 && ny < R && nx >= 0 && nx < C) {
                if (map[ny][nx] < map[r][c]) {
                    dp[r][c] += findPath(ny, nx);
                }
            }
        }

        return dp[r][c];
    }
}
