import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));
            String[] str = br.readLine().split(" ");
            int n = Integer.parseInt(str[0]);
            int m = Integer.parseInt(str[1]);
            int[][] dist = new int[n+1][n+1];
            final int MAX = 1000 * 10000;
            for (int i = 1; i <= n; i++) {
                Arrays.fill(dist[i], MAX);
                dist[i][i] = 0;
            }
            for (int i = 1; i < n; i++) {
                str = br.readLine().split(" ");
                int x = Integer.parseInt(str[0]);
                int y = Integer.parseInt(str[1]);
                int d = Integer.parseInt(str[2]);
                dist[x][y] = dist[y][x] = d;
            }

            for (int k = 1; k <= n; k++) {
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j <= n; j++) {
                        dist[i][j] =
                                Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }

            StringBuffer sb = new StringBuffer();
            while(m-- > 0) {
                str = br.readLine().split(" ");
                int x = Integer.parseInt(str[0]);
                int y = Integer.parseInt(str[1]);
                sb.append(dist[x][y]).append("\n");
            }
            System.out.println(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}