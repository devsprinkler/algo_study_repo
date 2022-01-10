import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1717 {
    static int N, M;
    static int[] parent;
    static int op, a, b;
    static BufferedReader br;

    static int find(int x) {
        if (parent[x] == x)
            return x;

        return parent[x] = find(parent[x]);
    }

    static void union(int x, int y) {
        x = find(x);
        y = find(y);

        if(x < y) {
            parent[y] = x;
        } else {
            parent[x] = y;
        }
    }

    static void initData() throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        parent = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
        }
    }

    static void input() throws Exception {
        StringTokenizer st = new StringTokenizer(br.readLine());

        op = Integer.parseInt(st.nextToken());
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
    }

    static String solve() throws Exception {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < M; i++) {
            input();

            if (op == 0) {
                union(a, b);
            } else {
                sb.append(find(a) == find(b) ? "YES" : "NO").append("\n");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        initData();

        System.out.println(solve());
    }
}
