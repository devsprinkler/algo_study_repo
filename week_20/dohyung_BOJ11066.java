import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    // 전체 그룹을 두 부분 그룹으로 나누어감
    // dp[i][j] i부터 j까지 최소 비용
    // dp[i][i] = files[i]
    // dp[i][i + 1] = files[i] + files[i + 1]
    // dp[i][j] = min(dp[i][k] + dp[k + 1][j]) (i <= k < j) + (sum[j] - sum[i - 1]) 큰 단위에서 더하는 비용

    static int T;
    static int K;
    static int[] files;
    static int[] sum;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            K = Integer.parseInt(st.nextToken());
            sum = new int[K];
            files = new int[K];
            dp = new int[K][K];
            for (int j = 0; j < K; j++) {
                Arrays.fill(dp[j], Integer.MAX_VALUE);
            }

            st = new StringTokenizer(br.readLine());
            int beforeSum = 0;
            for (int j = 0; j < K; j++) {
                files[j] = Integer.parseInt(st.nextToken());
                sum[j] = beforeSum + files[j];

                beforeSum = sum[j];
            }

            System.out.println(getDp(0, K - 1));
        }
    }

    static int getDp(int r, int c) {
        if (dp[r][c] != Integer.MAX_VALUE)
            return dp[r][c];
        else if (r == c)
            return 0;
        else if (r + 1 == c)
            return dp[r][c] = files[r] + files[c];
        else {
            int min = -1;
            for (int k = r; k < c; k++) {
                if (min == -1)
                    min = getDp(r, k) + getDp(k + 1, c);
                else
                    min = Math.min(min, getDp(r, k) + getDp(k + 1, c));
            }
            if (r == 0)
                dp[r][c] = min + sum[c];
            else
                dp[r][c] = min + sum[c] - sum[r - 1];
            return dp[r][c];
        }
    }
}
