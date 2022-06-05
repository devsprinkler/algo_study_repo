import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static class Road implements Comparable<Road> {
        int s, e, w;

        Road(int s, int e, int w) {
            this.s = s;
            this.e = e;
            this.w = w;
        }

        @Override
        public int compareTo(Road o) {
            return this.w - o.w;
        }
    }

    static int N, M;
    static PriorityQueue<Road> roads;
    static int sumOfWholeMoney;
    static int[] parent;

    static void input() {
        StringTokenizer st;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                st = new StringTokenizer(br.readLine());

                M = Integer.parseInt(st.nextToken());
                N = Integer.parseInt(st.nextToken());

                if (M == 0 && N == 0) {
                    break;
                }

                roads = new PriorityQueue<>();
                parent = new int[M];
                sumOfWholeMoney = 0;

                for (int i = 0; i < M; i++) {
                    parent[i] = i;
                }

                for (int i = 0; i < N; i++) {
                    st = new StringTokenizer(br.readLine());

                    int s = Integer.parseInt(st.nextToken());
                    int e = Integer.parseInt(st.nextToken());
                    int w = Integer.parseInt(st.nextToken());

                    roads.offer(new Road(s, e, w));
                    sumOfWholeMoney += w;
                }

                solve();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void union(int x, int y) {
        x = getParent(x);
        y = getParent(y);

        parent[y] = x;
    }

    static int getParent(int x) {
        if (parent[x] == x) {
            return x;
        }

        return parent[x] = getParent(parent[x]);
    }

    static void solve() {
        int savedMoney = 0;

        while (!roads.isEmpty()) {
            Road road = roads.poll();

            int s = road.s;
            int e = road.e;

            if (getParent(s) != getParent(e)) {
                union(s, e);
                savedMoney += road.w;
            }
        }

        System.out.println(sumOfWholeMoney - savedMoney);
    }

    public static void main(String[] args) {
        input();
    }
}
