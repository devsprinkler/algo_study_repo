import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.System.in;

public class Main {

    // n 개의 도시 최대 1000개
    // m 개의 버스 최대 10만개

    // A -> B 도시 버스 비용 최소화
    // 최소비용, 경로
    
    // 출발, 도착, 비용
    // 비용은 0이상 10만 미만 정수

    // 다익스트라
    // pq
    // 무한으로 초기화

    static int n, m;
    static ArrayList<Pair>[] adj;
    static int[] path;
    static long[] cost; // long

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        adj = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }
        path = new int[n + 1];
        cost = new long[n + 1];
        for (int i = 0; i <= n; i++) {
            cost[i] = 100000L * 100000 + 1;
            path[i] = -1;
        }

        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int from, to, cost;

            from = Integer.parseInt(st.nextToken());
            to = Integer.parseInt(st.nextToken());
            cost = Integer.parseInt(st.nextToken());

            adj[from].add(new Pair(to, cost));
        }

        st = new StringTokenizer(br.readLine());
        int depart, dest;
        depart = Integer.parseInt(st.nextToken());
        dest = Integer.parseInt(st.nextToken());

        dijkstra(depart, dest);
    }

    static void dijkstra(int depart, int dest) {
        PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Long.compare(o1.cost, o2.cost);
            }
        });

        boolean[] visit = new boolean[n + 1]; // 방문처리해야만 0인 경우 , 사이클 처리 가능?

        pq.add(new Pair(depart, 0));

        int cnt = 0;

        while (!pq.isEmpty() && cnt < n) {
            Pair p = pq.poll();

            if (visit[p.dest]) continue;
            visit[p.dest] = true;
            if (cost[p.dest] < p.cost) continue;

            for (int i = 0; i < adj[p.dest].size(); i++) {
                Pair t = adj[p.dest].get(i);

                if (t.cost + p.cost < cost[t.dest] && !visit[t.dest]) {
                    cost[t.dest] = t.cost + p.cost;
                    path[t.dest] = p.dest;
                    pq.add(new Pair(t.dest, t.cost + p.cost));
                }
            }
            cnt++;
        }

        StringBuilder sb = new StringBuilder();

        sb.append(cost[dest]).append("\n");

        Stack<Integer> s = new Stack<>();
        int node = dest;
        do {
            s.push(node);
            node = path[node];
        } while (node != -1);

        sb.append(s.size()).append("\n");
        while (!s.isEmpty()) {
            sb.append(s.pop()).append(" ");
        }

        System.out.print(sb);
    }

    static class Pair {
        int dest;
        long cost;

        public Pair(int dest, long cost) {
            this.dest = dest;
            this.cost = cost;
        }
    }
}
