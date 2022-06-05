import java.util.*;
import java.io.*;

class Main {

    static int INF = 987654321;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
      
        while(t-- > 0) {
            int k = Integer.parseInt(br.readLine());
            int[][] dp = new int[501][501];
            int[] sum = new int[501];
            StringTokenizer st = new StringTokenizer(br.readLine());
            sum[0] = Integer.parseInt(st.nextToken());
          
            for (int i = 1; i < k; i++) {
                sum[i] = sum[i - 1] + Integer.parseInt(st.nextToken());
            }

            for (int i = 1; i < k; i++) {
                for (int j = 0; i + j < k; j++) {
                    int l = i + j;
                    int psum;
                    if (j == 0) {
                        psum = sum[l];
                    } else {
                        psum = sum[l] - sum[j - 1];
                    }

                    dp[j][l] = INF;
                    for (int m = j; m < l; m++) {
                        dp[j][l] = Math.min(dp[j][l], dp[j][m] + dp[m + 1][l] + psum);
                    }
                }
            }
          
            System.out.println(dp[0][k - 1]);
        }
    }
}
