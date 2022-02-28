package algorithm.BOJ.P2240;

/*
 * 불가능한 케이스 핸들링
 * 0번 움직일 때 2번 나무의 열매를 받는 경우 = 0
 * 1번 움직일 때 1번 나무의 열매를 받는 경우 = 0
 */

import java.io.IOException;
import java.util.Scanner;

public class P2240 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        int w = sc.nextInt();

        int[] tree = new int[t];
        for (int i = 0; i < t; i++) {
            tree[i] = sc.nextInt() - 1;
        }
        sc.close();
        
        int[][][] dp = new int[w+1][2][t];

        if (tree[0] == 0) dp[0][0][0] = 1;
        for (int i = 1; i < t; i++) {
            if (tree[i] == 0) dp[0][0][i] = dp[0][0][i - 1] + 1;
            else dp[0][0][i] = dp[0][0][i - 1];
        }
        if (tree[0] == 1) {
            dp[1][1][0] = 1;
        }
        for (int i = 1; i < t; i++) {
            dp[1][1][i] = dp[1][1][i - 1] > dp[0][0][i - 1] ?
                dp[1][1][i - 1] + tree[i] : dp[0][0][i - 1] + tree[i];
        }
        for (int i = 2; i <= w; i++) {
            for (int j = i - 1; j < t; j++) {
                int a = 0, b = 0;
                if (tree[j] == 0) a = 1;
                else b = 1;
                dp[i][0][j] = dp[i][0][j - 1] > dp[i - 1][1][j - 1] ?
                    dp[i][0][j - 1] + a : dp[i - 1][1][j - 1] + a;
                dp[i][1][j] = dp[i][1][j - 1] > dp[i - 1][0][j - 1] ?
                    dp[i][1][j - 1] + b : dp[i - 1][0][j - 1] + b;
            }
        }
        int max = 0;
        for (int i = 0 ; i <= w; i++) {
            max = Math.max(max, Math.max(dp[i][0][t - 1], dp[i][1][t - 1]));
        }
        System.out.println(max);
    }
}