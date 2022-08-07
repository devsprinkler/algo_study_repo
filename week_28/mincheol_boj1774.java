import java.util.*;
import java.io.*;

class Point {
    int num;
    double x;
    double y;

    Point(int num, double x, double y) {
        this.num = num;
        this.x = x;
        this.y = y;
    }
}

class Edge implements Comparable<Edge> {
    int start;
    int end;
    double weight;

    Edge(int start, int end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
        if (weight < o.weight) {
            return -1;
        }
        return 1;
    }

}

public class Main {
    static int[] parent;
    static ArrayList<Edge> edgeList;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        parent = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
        }

        Point[] points = new Point[N + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());

            points[i] = new Point(i, x, y);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            union(start, end);
        }

        edgeList = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            for (int j = i + 1; j <= N; j++) {
                double weight = distance(points[i], points[j]);

                edgeList.add(new Edge(points[i].num, points[j].num, weight));
            }
        }
        Collections.sort(edgeList);

        double ans = 0;

        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);

            if (find(edge.start) != find(edge.end)) {
                ans += edge.weight;
                union(edge.start, edge.end);
            }
        }

        System.out.println(String.format("%.2f", ans));
    }

    static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    static int find(int x) {
        if (x == parent[x]) {
            return x;
        }

        return find(parent[x]);
    }

    static void union(int x, int y) {
        x = find(x);
        y = find(y);

        if (x != y) {
            parent[y] = x;
        }
    }
}
