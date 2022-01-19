import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    // 중심은 행성 T

    // N개의 행성 간에 플로우를 설치
    // 플로우르 설치하면 시간0에 이동 가능
    // 치안을 유지하기 위해 플로우 내에 제국군을 주둔시켜야

    // 제국의 모든 행성을 연결하면서 관리 비용을 최소한
    // N개의 행성은 1..N
    // i j 사이의 관리비용 Cij
    // i==j 이면 0

    // N 1 ~ 1000
    // 관리비용 C 최대 1억 --> Long

    // MST
    // 최대 N - 1 번 갱신
    // 유니온 파인드
    // 가능한 플로우 중 정렬하여 최소인 것 부터 , 같은 부모가 아닌 경우만 합침(사이클 x)

    static int N;
    static Long answer = 0L;
    static PriorityQueue<Flow> pq = new PriorityQueue<>(new Comparator<Flow>() {
        @Override
        public int compare(Flow o1, Flow o2) {
            return o1.cost - o2.cost;
        }
    });

    static int[] p;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        p = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            p[i] = i;
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int cost = Integer.parseInt(st.nextToken());
                if (i == j) continue;
                pq.add(new Flow(i, j, cost));
            }
        }

        findMst();

        System.out.println(answer);
    }

    static void findMst() {
        int cnt = 0;

        while (!pq.isEmpty()) {
            if (cnt >= N - 1)
                break;

            Flow f = pq.poll();

            int pi = find(f.i);
            int pj = find(f.j);

            if (pi == pj) continue;

            merge(f.i, f.j);
            answer += f.cost;
            cnt++;
        }
    }

    static int find(int e) {
        if (p[e] == e)
            return e;
        else {
            return p[e] = find(p[e]);
        }
    }

    static void merge(int a, int b) {
        int pa, pb;

        pa = find(a);
        pb = find(b);
        if (pa == pb) {
            return;
        }
        else {
            p[pa] = pb; // 자주 실수하는 부분
        }
    }

    static class Flow {
        int i, j;
        int cost;

        public Flow(int i, int j, int cost) {
            this.i = i;
            this.j = j;
            this.cost = cost;
        }
    }
}
