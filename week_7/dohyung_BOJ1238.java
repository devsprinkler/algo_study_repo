import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    // N개의 마을. 각 1명의 학생

    // N 명이 x 번 마을에 모여서 파티를 벌이기로 했다.
    // 마을 사이에는 총 M개의 단 방향 도로들이 있고
    // i 번째 길을 지나느데 Ti 시간을 소비. (최대 100. 1이상)

    // 파티에 참석하기 위해 가서 다시 그들의 마을로 돌아와야 한다.
    // 최단시간에 오고 가기를 원한다.

    // 단방향 -> 오고 가는 길이 다를 수도 있다.
    // N명의 학생들 중 가장 많은 시간이 걸리는 학생?

    // N 최대 1000명
    // M 최대 도로 1만개
    // (시작, 끝, 시간)
    // 순환 X, 중복 도로 X
    // x 로 오갈 수 있는 경우만 주어짐

    // BFS 하면 O(V + E) * O(V + E) * 1000 X
    // 전체 최단 거리를 구하는 알고리즘
    // 플로이드 와샬 -> 모든 정점에서 모든 다른 정점으로 가는 최단 경로
    // 벨만포드? 다익스트라? -> 하나의 정점에서 다른 모든 정점으로 가는 최단 경로
    // k i j

    static int N, M, X;
    static long[][] dist;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        dist = new long[N + 1][N + 1];
        for (int i = 0; i < dist.length; i++) {
            Arrays.fill(dist[i], 1000000 + 100);
            dist[i][i] = 0;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int src, dest;
            int cost;

            src = Integer.parseInt(st.nextToken());
            dest = Integer.parseInt(st.nextToken());
            cost = Integer.parseInt(st.nextToken());

            dist[src][dest] = cost;
        }

        floyd();
        System.out.println(findMax());
    }

    static long findMax() {
        long max = -1;

        for (int i = 1; i <= N; i++) {
            max = Math.max(max, dist[i][X] + dist[X][i]);
        }

        return max;
    }

    static void floyd() {
        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
    }

    static class Edge {
        int dest, cost;

        public Edge(int dest, int cost) {
            this.dest = dest;
            this.cost = cost;
        }
    }
}
