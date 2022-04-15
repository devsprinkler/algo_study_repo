import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[][] dp = new int[n+1][4];
        int[][] cost = new int[n+1][4];
        int ans = 1000001;
        
        for(int i=1; i<=n; i++) {
            String[] arr = br.readLine().split(" ");
            for(int j=1; j<=3; j++) {
                cost[i][j] = Integer.parseInt(arr[j-1]);
            }
        }
        
        for(int i=1; i<=3; i++) {
            for(int j=1; j<=3; j++) {
                dp[1][j] = i == j ? cost[1][j] : 1000001;
            }
            
            for(int j=2; j<=n; j++) {
                dp[j][1] = Math.min(dp[j-1][2], dp[j-1][3])+cost[j][1];
                dp[j][2] = Math.min(dp[j-1][1], dp[j-1][3])+cost[j][2];
                dp[j][3] = Math.min(dp[j-1][1], dp[j-1][2])+cost[j][3];
            }
            
            for(int j=1; j<=3; j++) {
                if(i != j) {
                    ans = Math.min(ans, dp[n][j]);
                }
            }
        }
        
        System.out.println(ans);
    }
}
