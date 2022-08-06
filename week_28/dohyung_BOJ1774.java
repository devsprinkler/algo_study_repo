import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.System.in;

public class Main {

    // 바로 연결될 필요가 없다
    // 다른 우주신들을 거쳐 교감 가능

    // 2차원 좌표계상의 거리 == 통로들의 길이
    // 새로 만들어야 할 통로의 길이들의 합이 최소가 되게 통로

    // 우주신최대 1000
    // 통로의 개수 1000
    // 황선자를 포함한 우주신들의 좌표 100만이하

    // 연결하는 순서에 따라 답이 다를수도?
    // 유니온-파인드?
    // 집단에 해당하는 것 중 최소 길이?
    // -> 연결안 된 것중 최소로 연결 가능한 것부터 정렬하여 연결? (이렇게 하면 매번 정렬해야 함)
    // -> 거리 + 좌표 조합을 정렬하여 저장. 새로운 점을 연결하면 그 점에 대해 다른 모든 점을 추가
    // -> 500 500 : 500 * 500 * log 500 OK

    // 연결된 집합인지 관리 필요
    // 통로 길이 실수, 소수점 둘째자리까지

    // 누가 황선자인지?
    // 우주신들끼리만 연결된 통로?

    // MST
    // 크루스칼
    // 유니온-파인드로 사이클 확인
    // -> 사이클 만드는 간선 있나?
    // -> 만든다 하더라도 최소만 되면 상관 X?
    // 가능한 간선들을 확인?
    // -> 어떤 집합이 연결되는 집합인지 확인 필요
    // 어쨌든 전체 노드가 연결되면 됨
    // -> 전체 노드가 연결될 것이므로 가장 가까운 노드와 연결 시켜주면 된다?
    // -> 이미 연결된 최소가 아닌 통로는?
    // -> 좌표로 주어지므로 순회하면서 모든 다른 점으로 이르는 통로들을 계산

    static int N, M;
    static int[] parent;
    static Pair[] coords;
    static double answer = 0;
    static Road[] list;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        coords = new Pair[N + 1];
        parent = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            parent[i] = i;
        }
        list = new Road[N * N - N];
        for (int i = 0; i < list.length; i++) {
            list[i] = new Road(Double.MAX_VALUE, -1, -1);
        }

        int x, y;
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());

            coords[i] = new Pair(y, x);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a, b;

            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            merge(a, b);
        }

        // 통로 pq
        int idx = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = i + 1; j <= N; j++) {
                if (find(i) == find(j)) continue;

//                pq.add(new Road(getDist(coords[i], coords[j]), i, j));
                list[idx++] = new Road(getDist(coords[i], coords[j]), i, j);
            }
        }

        Arrays.sort(list, new Comparator<Road>() {
            @Override
            public int compare(Road o1, Road o2) {
                return Double.compare(o1.dist, o2.dist);
            }
        });

        // MST N - 1 - M 번
        int j = 0;
        // while 모두 연결
        do {
            Road r = null;
            while (j < idx) {
//                r = pq.poll();
                r = list[j++];

                if (r.sameParent()) continue;
                else break;
            }

            merge(r.from, r.to);
            answer += r.dist;
        }
        while (!connected());
        System.out.printf("%.2f", answer);
    }

    static boolean connected() {
        int p = find(1);
        for (int i = 2; i <= N; i++) {
            if (p != find(i)) {
                return false;
            }
        }

        return true;
    }

    static class Pair {
        long y,x;

        public Pair(long y, long x) {
            this.y = y;
            this.x = x;
        }
    }

    static double getDist(Pair a, Pair b) {
        return Math.sqrt(Math.pow(a.y - b.y, 2) + Math.pow(a.x - b.x, 2));
    }

    static class Road implements Comparable<Road>{
        double dist;
        int from, to;

        public Road(double dist, int from, int to) {
            this.dist = dist;
            this.from = from;
            this.to = to;
        }

        @Override
        public int compareTo(Road o) {
            return Double.compare(this.dist, o.dist);
        }

        public boolean sameParent() {
            if (find(from) == find(to)) {
                return true;
            }
            else
                return false;

        }
    }

    static int find(int a) {
        if (parent[a] == a)
            return a;
        else
            return parent[a] = find(parent[a]);
    }

    static void merge(int a, int b) {
        int pa = find(a);
        int pb = find(b);

        parent[pa] = pb;
    }
}
