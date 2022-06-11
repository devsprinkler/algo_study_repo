import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Road implements Comparable<Road> {
    int x, y;
    int cost;

    public Road(int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    @Override
    public int compareTo(Road o) {
        return this.cost - o.cost;
    }
}

public class Main {

    static int n, m;
    static int[] parent;

    static void union(int x, int y) {
        if (parent[x] == -1) {
            parent[y] = x;
        } else {
            parent[x] = y;
        }
    }

    static int find(int x) {
        if (parent[x] == -1) return x;
        return parent[x] = find(parent[x]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        while(true) {
            st = new StringTokenizer(br.readLine());
            m = Integer.parseInt(st.nextToken());
            if (m == 0) break;
            n = Integer.parseInt(st.nextToken());

            parent = new int[m];
            Arrays.fill(parent, -1);

            Road[] arr = new Road[n];

            int totalCost = 0;
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                totalCost += c;
                arr[i] = new Road(x, y, c);
            }
            Arrays.sort(arr);
            union(arr[0].x, arr[0].y);
            int connected = 2;
            int newCost = arr[0].cost;

            for (int i = 1; connected < n && i < n; i++) {
                int x = find(arr[i].x);
                int y = find(arr[i].y);
                if (x != -1 && x != y) {
                    union(x, y);
                    connected++;
                    newCost += arr[i].cost;
                }
            }

            sb.append(totalCost - newCost).append("\n");
        }
        System.out.println(sb);
    }
}
