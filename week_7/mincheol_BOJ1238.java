import java.io.*;
import java.util.*;

class node implements Comparable<node>{
    int route;
    int cost;

    public node(int route, int cost) {
        this.route = route;
        this.cost = cost;
    }

    @Override
    public int compareTo(node o) {
        if (this.cost < o.cost)
            return -1;
        else if (this.cost > o.cost)
            return 1;
        return 0;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        final int n = Integer.parseInt(st.nextToken());
        final int m = Integer.parseInt(st.nextToken());
        final int x = Integer.parseInt(st.nextToken());

        ArrayList<node>[] al = new ArrayList[n+1];
        ArrayList<node>[] al_rev = new ArrayList[n+1];
        for (int i = 1; i <= n; i++) {
            al[i] = new ArrayList<>();
            al_rev[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken());
            int route = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            al[idx].add(new node(route, cost));
            al_rev[route].add(new node(idx, cost));
        }

        int[] d = new int[n+1];
        boolean[] visited = new boolean[n+1];
        node cur;
        PriorityQueue<node> pq = new PriorityQueue<>();
        pq.add(new node(x, 0));
        while(!pq.isEmpty()) {
            cur = pq.poll();
            if (visited[cur.route]) continue;
            visited[cur.route] = true;
            d[cur.route] = cur.cost;
            for (int i = 0; i < al_rev[cur.route].size(); i++) {
                pq.add(new node(al_rev[cur.route].get(i).route, cur.cost + al_rev[cur.route].get(i).cost));
            }
        }

        Arrays.fill(visited, false);
        int max = 0;
        pq.add(new node(x, 0));
        while(!pq.isEmpty()) {
            cur = pq.poll();
            if (visited[cur.route]) continue;
            visited[cur.route] = true;
            max = Math.max(max, d[cur.route] + cur.cost);
            for (int i = 0; i < al[cur.route].size(); i++) {
                pq.add(new node(al[cur.route].get(i).route, cur.cost + al[cur.route].get(i).cost));
            }
        }
        System.out.println(max);
    }
}
