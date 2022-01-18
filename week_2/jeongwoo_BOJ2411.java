import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static int A, B;

    static int[][] map;
    static int[][] count;

    static void input() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());
            B = Integer.parseInt(st.nextToken());

            map = new int[N + 1][M + 1];
            count = new int[N + 1][M + 1];

            for (int i = 0; i < A; i++) {
                st = new StringTokenizer(br.readLine());

                int y = Integer.parseInt(st.nextToken());
                int x = Integer.parseInt(st.nextToken());

                map[y][x] = 1;

                for (int j = y + 1; j <= N; j++) {
                    for (int k = 1; k < x; k++) {
                        if (map[j][k] != 1) {
                            map[j][k] = -1;
                        }
                    }
                }
                
                for (int j = y - 1; j >= 1; j--) {
                    for (int k = x + 1; k <= M; k++) {
                        if (map[j][k] != 1)
                            map[j][k] = -1;
                    }
                }
            }

            for (int i = 0; i < B; i++) {
                st = new StringTokenizer(br.readLine());

                int y = Integer.parseInt(st.nextToken());
                int x = Integer.parseInt(st.nextToken());

                map[y][x] = -1;
            }

            map[1][1] = 0;
            map[N][M] = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void solve() {
        count[1][1] = 1;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (i == 1 && j == 1)
                    continue;

                if (map[i][j] != -1) {
                    count[i][j] = count[i - 1][j] + count[i][j - 1];
                }
            }
        }

        System.out.println(count[N][M]);
    }

    public static void main(String[] args) {
        input();

        solve();
    }
}
