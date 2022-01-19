import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    static class Planet implements Comparable<Planet> {
        int x;
        int y;
        int cost;

        public Planet(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        @Override
        public int compareTo(Planet o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    static int[] union(int x, int y, int[] p) {
        p[y] = x;
        return p;
    }

    static int find(int x, int[] p) {
        if (p[x] == -1) return x;
        return p[x] = find(p[x], p);
    }

    public static void main(String[] args) {
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));
            int n = Integer.parseInt(br.readLine());
            String[] str;
            PriorityQueue<Planet> pq = new PriorityQueue<>();
            for (int i = 0; i < n; i++) {
                str = br.readLine().split(" ");
                for (int j  = i + 1 ; j < n; j++) {
                    pq.offer(new Planet(i, j, Integer.parseInt(str[j])));
                }
            }
            int[] parents = new int[n];
            Arrays.fill(parents, -1);
            long minCost = 0;
            while(!pq.isEmpty()) {
                Planet planet = pq.poll();
                int x = find(planet.x, parents);
                int y = find(planet.y, parents);
                if (x != y) {
                    parents = union(x, y, parents);
                    minCost += planet.cost;
                }
            }
            System.out.println(minCost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
