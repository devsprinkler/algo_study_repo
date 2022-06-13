import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ16562 {
    static int N, M;
    static int money;
    static Pair[] friends;
    static int[] costs;

    static int[] parents;

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static void input() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            money = Integer.parseInt(st.nextToken());

            costs = new int[N + 1];
            parents = new int[N + 1];

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= N; i++) {
                costs[i] = Integer.parseInt(st.nextToken());
                parents[i] = i;
            }

            friends = new Pair[M];
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());

                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());

                friends[i] = new Pair(x, y);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int find(int x) {
        if (parents[x] == x) {
            return x;
        }

        return parents[x] = find(parents[x]);
    }

    static void union(int x, int y) {
        x = find(x);
        y = find(y);

        if (costs[x] >= costs[y]) {
            parents[x] = y;
        } else {
            parents[y] = x;
        }
    }

    static void solve() {
        for (Pair friend : friends) {
            int x = friend.x;
            int y = friend.y;

            union(x, y);
        }

        int sum = 0;
        for (int i = 1; i <= N; i++) {
            if (parents[i] == i) {
                sum += costs[i];
            }
        }

        if (money >= sum) {
            System.out.println(sum);
        } else {
            System.out.println("Oh no");
        }
    }

    public static void main(String[] args) {
        input();

        solve();
    }
}
