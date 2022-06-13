import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    // 최대 500명
    // 자신의 키 순서 아는 경우 : 모든 다른 점에서 현재점으로 오고 갈 수 있는 경우가 있음(무한이 아님)
    
    // 모든 점에서 모든 점 최단 경로

    final static int max = 500 * 500 / 2 + 1;
    static int N, M;
    static int[][] dist;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        dist = new int[N + 1][N + 1];
        for (int i = 0; i < N + 1; i++) {
            Arrays.fill(dist[i], max);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int from, to;
            from = Integer.parseInt(st.nextToken());
            to = Integer.parseInt(st.nextToken());
          
            dist[from][to] = 1;
        }

        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        int answer = 0;

        for (int i = 1; i < N + 1; i++) {
            int cnt = 0;
            for (int j = 1; j < N + 1; j++) {
                if (i == j) continue;

                if (dist[i][j] == max && dist[j][i] == max) {
                    break;
                }
                else
                    cnt++;
            }
            if (cnt == (N - 1))
                answer++;
        }

        System.out.println(answer);
    }
}
