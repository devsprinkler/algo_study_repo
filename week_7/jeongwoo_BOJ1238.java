import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ1238 {
    static int N, M, X;
    static int[][] road;

    static void input() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            X = Integer.parseInt(st.nextToken());

            road = new int[N + 1][N + 1];

            for (int i = 1; i <= N; i++) {
                Arrays.fill(road[i], 9999999);
                road[i][i] = 0;
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());

                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());

                road[s][e] = w;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void solve() {
        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (road[i][j] > road[i][k] + road[k][j]) {
                        road[i][j] = road[i][k] + road[k][j];
                    }
                }
            }
        }

        int answer = -1;
        for (int i = 1; i <= N; i++) {
            answer = Math.max(answer, road[i][X] + road[X][i]);
        }

        System.out.println(answer);
    }

    public static void main(String[] args) {
        input();

        solve();
    }
}
