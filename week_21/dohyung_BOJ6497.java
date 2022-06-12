import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.System.in;

public class Main {

    // 모든 길마다 켜져 있던 가로등 중 일부를 소등
    // 가로등 켜두면 하루에 길의 미터 수만큼 돈이 들어감

    // 어떤 두집을 왕래할 때 불이 켜져있지않은 길을 반드시 지나야 한다면 위험
    // 모든 두 집 쌍에 대해, 불이 켜진 길만으로 서로를 왕래 가능해야 

    // 절약할 수 있는 최대 액수 => 안지나도 되는 길을 꺼도 됨

    // 집 최대 20만개, 길 최대 20만개, 최소 집-1개
    // x, y, z : x,y 사이 양방향 도로 z미터

    // 도시 모든 길의 거리 합은 2^31 보다 작다
    // 도로 중 사용 안된 것을 기록?
    // 모든 점과 모든 점사이 -> kij x

    // mst kruskal
    // 1. edge 오름 차순 정렬
    // 2. 순서대로 고르되 사이클 형성하지 않는 간선을 선택
    // -> 새로운 edge의 양끝 정점이 같은 집합이면 사이클 형성
    // 3. 집합에 추가

    static int m, n;
    static int[] p;
    static ArrayList<Pair> edges = new ArrayList<>();
    static int answer = 0;

    static class Pair {
        int depart, dest, cost;

        public Pair(int depart, int dest, int cost) {
            this.depart = depart;
            this.dest = dest;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st;


        while(true) {
            st = new StringTokenizer(br.readLine());
            m = Integer.parseInt(st.nextToken());
            n = Integer.parseInt(st.nextToken());

            if (m == 0 && n == 0)
                break;

            p = new int[m];
            for (int i = 0; i < m; i++) {
                p[i] = i;
            }
            answer = 0;
            edges.clear();

            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                int x, y, z;

                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());
                z = Integer.parseInt(st.nextToken());

                edges.add(new Pair(x, y, z));
                answer += z;
            }

            Collections.sort(edges, Comparator.comparingInt(o -> o.cost));

            kruskal();
            System.out.println(answer);
        }

    }

    static void kruskal() {
        int cnt = 0;
        for (Pair edge : edges) {
            if (cnt == m - 1)
                break;

            if (isCycle(edge))
                continue;

            merge(edge.dest, edge.depart);
            answer -= edge.cost;
            cnt++;
        }
    }

    static boolean isCycle(Pair p) {
        return find(p.depart) == find(p.dest);
    }

    static int find(int a) {
        if (p[a] == a)
            return a;
        else
            return p[a] = find(p[a]);
    }

    static void merge(int a, int b) {
        int pa = find(a);
        int pb = find(b);

        p[pa] = pb;
    }
}
