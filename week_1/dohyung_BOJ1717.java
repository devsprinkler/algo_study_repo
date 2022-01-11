import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    // 0 ~ n 이 각각 N + 1개의 집합

    // 합집합 연산
    // 같은 집합 포함 확인 연산

    // n 최대 100만
    // m 최대 10만 -> 연산의 개수

    // 0 a b : 합집합
    // 1 a b : 확인 집합

    // 초기값 : 부모: -1
    // 합집합 연산 : 부모가 같으면 그대로
    // 부모가 다르면 부모를 바꿔줌

    static int[] p;
    static int N, M;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // init
        p = new int[N + 1];
        for (int i = 0; i < N + 1; i++) {
            p[i] = -1;
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int mode = Integer.parseInt(st.nextToken());
            int a, b;
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            if (mode == 0) {
                // merge
                merge(a, b);
            }
            else {
                // 부모 확인
                int pa, pb;

                pa = find(a);
                pb = find(b);
                if (pa == pb)
                    System.out.println("YES");
                else
                    System.out.println("NO");
            }
        }

    }

    static int find(int e) {
        if (p[e] < 0)
            return e;
        else {
            return p[e] = find(p[e]);
        }
    }

    static void merge(int a, int b) {
        int pa, pb;

        pa = find(a);
        pb = find(b);

        if (pa == pb)
            return;
        else {
            p[pa] = pb;
        }
    }
}
