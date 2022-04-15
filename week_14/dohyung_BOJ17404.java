import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    final static int MAX = 1000 * 1000 + 1;
    // dp[i][j]
    // 0 ~ i번째를 칠하면서 i 번째를 j색으로 칠할 때 최소값
    static int dp[][];
    static int N;
    static int[][] cost;
    static int answer = MAX;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        cost = new int[N][3];
        dp = new int[N][3];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < 3; j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 첫번째 색 고름
        // 0: R, 1: G, 2: B
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 3; k++) {
                    dp[j][k] = MAX;
                }
            }
            dp[0][i] = cost[0][i];

            for (int j = 1; j < N; j++) {
                dp[j][0] = Math.min(dp[j - 1][1], dp[j - 1][2]) + cost[j][0];
                dp[j][1] = Math.min(dp[j - 1][0], dp[j - 1][2]) + cost[j][1];
                dp[j][2] = Math.min(dp[j - 1][0], dp[j - 1][1]) + cost[j][2];
            }

            for (int j = 0; j < 3; j++) {
                if (i != j) {
                    answer = Math.min(answer, dp[N - 1][j]);
                }
            }
        }

        System.out.println(answer);
    }
}
