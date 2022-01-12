import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    // N개의 노드의 트리 ( 최대 1000)
    // M개의 두 노드 쌍
    // 두 노드 사이의 거리

    // N^2 -> 1000 * 1000 * log1000(3) 최대300만
    // BFS

    static int N, M;
    static ArrayList<Pair>[] adj;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        adj = new ArrayList[N + 1];
        for (int i = 0; i < N + 1; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());

            int a, b, d;

            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());

            adj[a].add(new Pair(b, d));
            adj[b].add(new Pair(a, d));
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a, b;

            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            System.out.println(bfs(a, b));
        }
    }

    static int bfs(int a, int b) {
        boolean[] visit = new boolean[N + 1];
        Queue<Pair> q = new LinkedList<>();

        q.add(new Pair(a, 0));
        while (!q.isEmpty()) {
            Pair p = q.poll();

            if (p.node == b)
                return p.dist;

            for (int i = 0; i < adj[p.node].size(); i++) {
                Pair t = adj[p.node].get(i);

                if (!visit[t.node]) {
                    q.add(new Pair(t.node, p.dist + t.dist));
                    visit[t.node] = true;
                }
            }
        }

        return 0;
    }

    static class Pair {
        int node;
        int dist;

        public Pair(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }
}
