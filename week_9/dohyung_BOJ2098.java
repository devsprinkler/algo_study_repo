import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    // TSP
    // 출발도시는 아무곳이나 해도 된다. 어차피 경로가 사이클이기 때문

    // 완전탐색: 모든 경로를 탐색해보고 그 중 최소 -> (N-1)개의 순열 : (N - 1)!


    // dp, 비트마스크 사용
    // dp[i][j] : i는 지금까지 방문한 도시 비트마스크, j는 현재 위치한 도시. 나머지 도시들을 모두 방문하고 돌아가는데 드는 비용 저장

    // N 최대 16

    static int MAX = 17 * 1000000;
    static int N;
    static int start = 0;
    static int[][] dp = new int[1 << 16][16];
    static int[][] cost = new int[16][16];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(findMin(0, start));
    }

    static int findMin(int i, int j) {
        i |= (1 << j); // visit

        if (i == (1 << N) - 1 && j == start) { // 모두 방문시 (1 << N) - 1
            // 출발점 돌아옴
            return 0;
        }

        if (dp[i][j] != 0) // dp 값 있으면 사용
            return dp[i][j];

        dp[i][j] = MAX;

        for (int k = 0; k < cost[j].length; k++) {
            if (cost[j][k] != 0 && (k == start || (i & (1 << k)) == 0)) {
                dp[i][j] = Math.min(dp[i][j], findMin(i, k) + cost[j][k]);
            }
        }

        return dp[i][j];
    }

}
