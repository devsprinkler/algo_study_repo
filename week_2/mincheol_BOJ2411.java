import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[][] map;
    static int count;

    static void findPath(int n, int m, int x, int y) {
        if (x == n && y == m) {
            count++;
            return;
        }

        if (x < n && map[x+1][y] == 0) {
            findPath(n, m, x+1, y);
        }
        if (y < m && map[x][y+1] == 0) {
            findPath(n, m, x, y+1);
        }
    }

    public static void main(String[] args) {
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));
            String[] str = br.readLine().split(" ");
            int n = Integer.parseInt(str[0]);
            int m = Integer.parseInt(str[1]);
            map = new int[n+1][m+1];

            int a = Integer.parseInt(str[2]);
            int b = Integer.parseInt(str[3]);

            int[] itemPoint = new int[a+2];
            itemPoint[0] = 1001;
            itemPoint[a+1] = n * 1000 + m;
            for (int i = 0 ; i < a; i++) {
                str = br.readLine().split(" ");
                int x = Integer.parseInt(str[0]);
                int y = Integer.parseInt(str[1]);
                itemPoint[i+1] = x * 1000 + y;
            }
            for (int i = 0; i < b; i++) {
                str = br.readLine().split(" ");
                int x = Integer.parseInt(str[0]);
                int y = Integer.parseInt(str[1]);
                map[x][y] = 1;
            }

            Arrays.sort(itemPoint);
            int ans = 1;
            for (int i = 0; i < a+1; i++) {
                int x = itemPoint[i] / 1000;
                int y = itemPoint[i] % 1000;
                int nx = itemPoint[i+1] / 1000;
                int my = itemPoint[i+1] % 1000;
                count = 0;
                findPath(nx, my, x, y);
                ans *= count;
            }
            System.out.println(ans);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
