import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.System.in;

public class Main {

    // 친구의 친구는 친구다.
    // 가장 적은 비용으로 모든 사람과 친구가 되는 방법

    // 학생수 최대 1만
    // 친구관계수 최대 1만
    // 돈 최대 1천만


    static int N, M;
    static int k;
    static int[] fees;
    static int[] p;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        fees = new int[N + 1];
        p = new int[N + 1];
        for (int i = 0; i < p.length; i++) {
            p[i] = i;
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            fees[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int v, w;

            v = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            merge(v, w);
        }

        boolean[] found = new boolean[N + 1];
        int sum = 0;
        for (int i = 1; i < N + 1; i++) {
            // find parent
            int parent = find(i); // find 로 부모 찾아야
            if (found[parent]) continue;

            sum += fees[parent];
            found[parent] = true;
        }

        if (sum > k)
            System.out.println("Oh no");
        else
            System.out.println(sum);
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

        if (fees[pa] < fees[pb])
            p[pb] = p[pa];
        else if (fees[pa] == fees[pb]) {
            // idx 작은 것을 부모로
            if (pa > pb) {
                p[pa] = p[pb];
            }
            else {
                p[pb] = p[pa];
            }
        }
        else
            p[pa] = p[pb];
    }
}
